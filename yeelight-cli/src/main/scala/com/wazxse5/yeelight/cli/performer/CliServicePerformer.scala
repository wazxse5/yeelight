package com.wazxse5.yeelight.cli.performer

import com.wazxse5.yeelight.cli.CliCommands.{message, service}
import com.wazxse5.yeelight.cli.exception.InvalidCommandException
import com.wazxse5.yeelight.cli.{CliCommand, CliDevice}
import com.wazxse5.yeelight.command.GetProps
import com.wazxse5.yeelight.core.{InternalId, YeelightService}

import scala.collection.mutable.{Map => MutableMap}

object CliServicePerformer {

  def perform(command: CliCommand)(implicit yeelightService: YeelightService, cliDevices: MutableMap[InternalId, CliDevice]): Unit = {
    command.pop match {
      case service.deviceOf => createNewDevice(command)
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

  private def createNewDevice(command: CliCommand)(implicit yeelightService: YeelightService, cliDevices: MutableMap[InternalId, CliDevice]): Unit = {
    command.popOpt match {
      case Some(address) =>
        val newDevice = yeelightService.deviceOf(address)
        newDevice.performCommand(GetProps.all)
        val newCliDevice = CliDevice(newDevice, command.popOpt)
        insertOrUpdateCliDevice(newCliDevice)
      case None =>
    }
  }

  private def printCliDevices()(implicit yeelightService: YeelightService, cliDevices: MutableMap[InternalId, CliDevice]): Unit = {
    refreshDevices()
    if (cliDevices.nonEmpty) cliDevices.values.toList.sortBy(_.cliId).foreach(c => println(c.simpleInfo))
    else println(message.noDevices)
  }

  private def refreshDevices()(implicit yeelightService: YeelightService, cliDevices: MutableMap[InternalId, CliDevice]): Unit = {
    val newDevices = yeelightService.devices.filterNot(d => cliDevices.contains(d.internalId))
    val newCliDevices = newDevices.map(CliDevice(_))
    newCliDevices.foreach(insertOrUpdateCliDevice)
  }

  private def insertOrUpdateCliDevice(cliDeviceInfo: CliDevice)(implicit cliDevices: MutableMap[InternalId, CliDevice]): Unit = {
    cliDevices += cliDeviceInfo.yeelightDevice.internalId -> cliDeviceInfo
  }

}
