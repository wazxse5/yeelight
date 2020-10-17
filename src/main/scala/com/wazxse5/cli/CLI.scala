package com.wazxse5.cli

import com.wazxse5.cli.CliCommands._
import com.wazxse5.cli.exception.{AliasReservedKeyWordException, AliasTaken, CliException, UnknownCommandException}
import com.wazxse5.command._
import com.wazxse5.core.{InternalId, YeelightService}

import scala.collection.mutable.{Map => MutableMap}

class CLI(yeelightService: YeelightService) {

  private var cliDevices: MutableMap[InternalId, CliDevice] = MutableMap.empty

  def perform(string: String): Unit = {
    try {
      val words = string.split(' ').toList
      if (words.isEmpty) throw UnknownCommandException.emptyCommand
      else {
        val (head, tail) = (words.head, words.tail)
        if (app._commands.contains(head)) performForApp(head)
        else if (service._commands.contains(head)) performForService(head, tail)
        else if (head == allDevices_) performForAllDevices(tail)
        else findCliDevice(head) match {
          case Some(cliDevice) => performForSingleDevice(tail)(cliDevice)
          case None => throw UnknownCommandException(head)
        }
      }
    } catch {
      case e: CliException => println(e.cliMessage)
    }
  }

  private def performForApp(command: String): Unit = command match {
    case app.exit =>
      yeelightService.exit
      System.exit(0)
    case _ => throw UnknownCommandException(command)
  }

  private def performForService(command: String, tail: List[String]): Unit = command match {
    case service.deviceOf => createNewDevice(tail)
    case service.devices =>
      refreshDevices()
      if (cliDevices.nonEmpty) cliDevices.foreach(c => println(c._2.simpleInfo))
      else println(message.noDevices)
    case service.discover => yeelightService.search()
    case service.listen => tail.headOption match {
      case Some(service.listenOff) => yeelightService.stopListening()
      case None | Some(service.listenOn) => yeelightService.startListening()
      case other => throw UnknownCommandException(other, Seq(service.listenOn, service.listenOff))
    }
    case _ => throw UnknownCommandException(command)
  }

  private def performForAllDevices(commands: List[String]): Unit = {
    cliDevices.values.foreach(performForSingleDevice(commands)(_))
  }

  private def performForSingleDevice(commands: List[String])(implicit cliDevice: CliDevice): Unit = {
    commands.head match {
      case device.decrease_ => CliDevicePerformer.decrease(commands.tail)
      case device.increase_ => CliDevicePerformer.increase(commands.tail)
      case device.get_ => CliDevicePerformer.get(commands.tail)
      case device.set_ => CliDevicePerformer.set(commands.tail, this)
      case device.set.on => CliDevicePerformer.on(commands.tail)
      case device.set.off => CliDevicePerformer.off(commands.tail)
      case device.refresh => CliDevicePerformer.refresh(commands.tail)
      case device.state => CliDevicePerformer.state(commands.tail)
      case device.toggle => CliDevicePerformer.toggle(commands.tail)
      case other => throw UnknownCommandException(Some(other), device._commands)
    }
  }

  private def findCliDevice(identifier: String): Option[CliDevice] = cliDevices.collectFirst {
    case (_, cliDevice) if cliDevice.cliId == identifier || cliDevice.alias.contains(identifier) => cliDevice
  }

  private def createNewDevice(words: List[String]): Unit = words.headOption match {
    case Some(address) =>
      val newDevice = yeelightService.deviceOf(address)
      newDevice.performCommand(GetProps.all)
      val newCliDevice = CliDevice(newDevice, words.lift(1))
      insertOrUpdateCliDevice(newCliDevice)
    case None =>
  }

  private [cli] def isAliasAvailable(alias: String): Boolean = {
    if (CliCommands._keywords.contains(alias)) throw AliasReservedKeyWordException(alias)
    else if (cliDevices.flatMap(_._2.alias).toSet.contains(alias)) throw AliasTaken(alias)
    else true
  }

  private def refreshDevices(): Unit = {
    val newDevices = yeelightService.devices.filterNot(d => cliDevices.contains(d.internalId))
    val newCliDevices = newDevices.map(CliDevice(_))
    newCliDevices.foreach(insertOrUpdateCliDevice)
  }

  private def insertOrUpdateCliDevice(cliDeviceInfo: CliDevice): Unit = {
    cliDevices += cliDeviceInfo.yeelightDevice.internalId -> cliDeviceInfo
  }

}
