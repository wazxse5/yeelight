package com.wazxse5.cli

import com.wazxse5.InternalId
import com.wazxse5.api.IYeelightService
import com.wazxse5.cli.CLI._
import com.wazxse5.command.{SetBrightness, SetPower, SetRgb, YeelightCommand}
import com.wazxse5.valuetype.Power

import scala.collection.mutable.{Map => MutableMap}

class CLI(service: IYeelightService) {

  private var cliDevices: MutableMap[InternalId, CliDeviceInfo] = MutableMap.empty

  def perform(string: String): Unit = {
    val words = string.split(' ').toList
    if (words.headOption.exists(keywords.contains))
      performService(words)
    else if (words.length > 1) {
      findDeviceInternalId(words.head) match {
        case Some(deviceInternalId) => performDevice(deviceInternalId, words.tail)
        case None => println("Cannot find device")
      }
    }
  }

  private def findDeviceInternalId(identifier: String): Option[InternalId] = {
    lazy val asAlias = cliDevices.collectFirst {
      case (uid, cliDevice) if cliDevice.alias.contains(identifier) => uid
    }
    lazy val asCliId = cliDevices.collectFirst {
      case (uid, cliDevice) if cliDevice.cliId == identifier => uid
    }
    lazy val asId = cliDevices.collectFirst {
      case (uid, cliDevice) if cliDevice.yeelightDevice.id.contains(identifier) => uid
    }
    lazy val asInternalId = cliDevices.collectFirst {
      case (uid, _) if uid.id == identifier => uid
    }

    asCliId orElse asAlias orElse asId orElse asInternalId
  }

  private def performService(words: List[String]): Unit = if (words.nonEmpty) {
    words.head match {
      case CLI.discover => service.search()
      case CLI.deviceOf => createNewDevice(words.tail)
      case CLI.devices => cliDevices.foreach(c => println(c._2.simpleInfo))
      case CLI.exit => System.exit(0)
    }
  }

  private def performDevice(deviceInternalId: InternalId, words: List[String]): Unit = if (words.nonEmpty) {
    def performCommand(command: YeelightCommand): Unit = service.performCommand(deviceInternalId, command)

    words.head match {
      case CLI.on => performCommand(SetPower(Power.on))
      case CLI.off => performCommand(SetPower(Power.off))
      case CLI.set if words.length > 1 => words(1) match {
        case CLI.alias => setAlias(deviceInternalId, words(2))
        case CLI.brightness => performCommand(SetBrightness(words(2).toInt))
        case CLI.temperature => performCommand(SetBrightness(words(2).toInt))
        case CLI.color => performCommand(SetRgb(words(2).toInt))
      }
    }
  }

  private def createNewDevice(words: List[String]): Unit = {
    val address = words.headOption
    val newDevice = address.map(service.deviceOf(_))
    val newCliDevice = newDevice.map(CliDeviceInfo(_, words.lift(1)))
    newCliDevice.foreach(insertOrUpdateCliDevice)
  }

  private def setAlias(deviceInternalId: InternalId, alias: String): Unit = {
    if (!CLI.keywords.contains(alias)) cliDevices.get(deviceInternalId).foreach(_.setAlias(alias))
    else println("Alias is reserved keyword")
  }

  private def insertOrUpdateCliDevice(cliDeviceInfo: CliDeviceInfo): Unit = {
    cliDevices += cliDeviceInfo.yeelightDevice.internalId -> cliDeviceInfo
  }

}

object CLI {
  val discover = "discover"
  val deviceOf = "deviceof"
  val devices = "devices"
  val exit = "exit"

  val on = "on"
  val off = "off"
  val set = "set"
  val alias = "alias"
  val brightness = "brightness"
  val temperature = "temperature"
  val color = "color"


  val keywords: Set[String] = Set(discover, deviceOf, devices, exit, on, off, set, alias)
}
