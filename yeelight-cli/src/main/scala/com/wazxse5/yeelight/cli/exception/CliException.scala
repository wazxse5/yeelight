package com.wazxse5.yeelight.cli.exception

import com.wazxse5.yeelight.cli.CliDevice

trait CliException extends Exception

case class AliasUnavailableException(alias: String, reason: String) extends CliException {
  override def getMessage: String = s"""alias "$alias" is $reason"""
}

case class DeviceNotFoundException(deviceIdentifier: String) extends CliException {
  override def getMessage: String = s"""not found device identified by "$deviceIdentifier""""
}

case class InvalidParamValueCommandException(paramName: String, invalidValue: Option[String]) extends CliException {
  override def getMessage: String = {
    if (invalidValue.isEmpty) s"empty value of param $paramName"
    else s"""invalid value "${invalidValue.get}" of param $paramName"""
  }
}

case class NotImplementedCommandException(commandName: String) extends CliException {
  override def getMessage: String = s"$commandName command is not implemented yet"
}

case class InvalidCommandException(unknownCommand: Option[String], expectedValues: Seq[String] = Seq.empty) extends CliException {
  override def getMessage: String = {
    val expectedS = if (expectedValues.nonEmpty) s" - expected {${expectedValues.mkString(",")}}" else ""
    val commandS = if (unknownCommand.isEmpty) "empty command" else s"""unknown command "${unknownCommand.get}""""
    commandS + expectedS
  }
}

case class UnsupportedCommandException(commandName: String, cliDevice: CliDevice) extends CliException {
  override def getMessage: String = s"$commandName is not supported by device ${cliDevice.alias getOrElse cliDevice.cliId}"
}

object AliasUnavailableException {
  def taken(alias: String): AliasUnavailableException = AliasUnavailableException(alias, "taken")
  def reservedKeyword(alias: String): AliasUnavailableException = AliasUnavailableException(alias, "reserved keyword")
}

object InvalidParamValueCommandException {
  def apply(paramName: String, invalidValue: String): InvalidParamValueCommandException = {
    new InvalidParamValueCommandException(paramName, Some(invalidValue))
  }
}

object InvalidCommandException {
  def apply(command: String): InvalidCommandException = new InvalidCommandException(Some(command))
  def apply(command: List[String]): InvalidCommandException = {
    new InvalidCommandException(if (command.nonEmpty) Some(command.mkString(" ")) else None)
  }
  def expectedNothing(command: String): InvalidCommandException = {
    new InvalidCommandException(Some(command), Seq("nothing"))
  }
}
