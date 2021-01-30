package com.wazxse5.yeelight.cli

import com.wazxse5.yeelight.cli.CliCommand.CommandOperations
import com.wazxse5.yeelight.cli.CliCommands._
import com.wazxse5.yeelight.cli.exception.{CliException, InvalidCommandException}
import com.wazxse5.yeelight.cli.performer.{CliAppPerformer, CliDevicePerformer, CliServicePerformer}
import com.wazxse5.yeelight.core.YeelightService

import scala.collection.mutable.{Map => MutableMap}

class CliApp(yeelightService: YeelightService) {

  private implicit val ylService: YeelightService = yeelightService
  private implicit val cliDevices: MutableMap[String, CliDevice] = MutableMap.empty

  def perform(string: String): Unit = {
    try {
      val command = CliCommand(string)
      if (command.nonEmpty) {
        val firstWord = command.top
        if (firstWord isIncludedIn app._commands) CliAppPerformer.perform(command)
        else if (firstWord isIncludedIn service._commands) CliServicePerformer.perform(command)
        else if (firstWord == allDevices_) performForAllDevices(command.tail)
        else findCliDevice(firstWord) match {
          case Some(cliDevice) => performForSingleDevice(cliDevice, command.tail)
          case None => throw InvalidCommandException(command.mkString)
        }
      } else {
        throw InvalidCommandException(None)
      }
    } catch {
      case e: CliException => println(e.getMessage)
    }
  }

  private def performForAllDevices(command: CliCommand): Unit = {
    cliDevices.values.foreach(performForSingleDevice(_, command.clone))
  }

  private def performForSingleDevice(cliDevice: CliDevice, command: CliCommand): Unit = {
    implicit val impCliDevice: CliDevice = cliDevice
    command.pop match {
      case device.decrease_ => CliDevicePerformer.decrease(command)
      case device.increase_ => CliDevicePerformer.increase(command)
      case device.get_ => CliDevicePerformer.get(command)
      case device.set_ => CliDevicePerformer.set(command, isAliasTaken)
      case device.set.on => CliDevicePerformer.on(command)
      case device.set.off => CliDevicePerformer.off(command)
      case device.refresh => CliDevicePerformer.refresh(command)
      case device.state => CliDevicePerformer.state(command)
      case device.toggle => CliDevicePerformer.toggle(command)
      case other => throw InvalidCommandException(other)
    }
  }

  private def findCliDevice(identifier: String): Option[CliDevice] = cliDevices.collectFirst {
    case (_, cliDevice) if cliDevice.cliId == identifier || cliDevice.alias.contains(identifier) => cliDevice
  }

  private def isAliasTaken(alias: String): Boolean = {
    cliDevices.flatMap(_._2.alias).toSet.contains(alias)
  }
}
