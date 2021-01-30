package com.wazxse5.yeelight.cli.performer

import com.wazxse5.yeelight.cli.CliCommands.{message, service}
import com.wazxse5.yeelight.cli.exception.InvalidCommandException
import com.wazxse5.yeelight.cli.{CliCommand, CliDevice}
import com.wazxse5.yeelight.core.YeelightService

import scala.collection.mutable.{Map => MutableMap}

object CliServicePerformer {

  def perform(command: CliCommand)(implicit yeelightService: YeelightService, cliDevices: MutableMap[String, CliDevice]): Unit = {
    command.pop match {
      case service.devices => printCliDevices()
      case service.discover => yeelightService.search()
      case service.listen => command.popOpt match {
        case Some(service.listenOff) => yeelightService.stopListening()
        case None | Some(service.listenOn) => yeelightService.startListening()
        case other => throw InvalidCommandException(other, Seq(service.listenOn, service.listenOff))
      }
      case _ => throw InvalidCommandException(command.mkString)
    }
  }

  private def printCliDevices()(implicit yeelightService: YeelightService, cliDevices: MutableMap[String, CliDevice]): Unit = {
    refreshDevices()
    if (cliDevices.nonEmpty) cliDevices.values.toList.sortBy(_.cliId).foreach(c => println(c.simpleInfo))
    else println(message.noDevices)
  }

  private def refreshDevices()(implicit yeelightService: YeelightService, cliDevices: MutableMap[String, CliDevice]): Unit = {
    val newDevices = yeelightService.devices.filterNot(d => cliDevices.contains(d.deviceId))
    val newCliDevices = newDevices.map(CliDevice(_))
    newCliDevices.foreach(insertOrUpdateCliDevice)
  }

  private def insertOrUpdateCliDevice(cliDeviceInfo: CliDevice)(implicit cliDevices: MutableMap[String, CliDevice]): Unit = {
    cliDevices += cliDeviceInfo.yeelightDevice.deviceId -> cliDeviceInfo
  }

}
