package com.wazxse5.cli.exception

import com.wazxse5.cli.CliDevice

trait CliException extends Exception {
  def cliMessage: String
}

case class AliasReservedKeyWordException(alias: String) extends CliException{
  override def cliMessage: String = s"""alias "$alias" is reserved keywords"""
}

case class AliasTaken(alias: String) extends CliException {
  override def cliMessage: String = s"""alias "$alias" is taken"""
}

case class DeviceNotFoundException(deviceIdentifier: String) extends CliException {
  override def cliMessage: String = s"""not found device identified by "$deviceIdentifier""""
}

case class InvalidParamValueCommandException(paramName: String, invalidValue: Option[String] = None) extends CliException {
  override def cliMessage: String = {
    if (invalidValue.isEmpty) s"value of param $paramName is emptyCommand"
    else s"""invalid value "${invalidValue.get}" of param $paramName"""
  }
}

object InvalidParamValueCommandException {
  def apply(paramName: String, invalidValue: List[String]): InvalidParamValueCommandException = new InvalidParamValueCommandException(paramName, invalidValue.headOption)
}

case class NotImplementedCommandException(commandName: String) extends CliException {
  override def cliMessage: String = s"$commandName is not implemented yet"
}

case class UnknownCommandException(unknownCommand: Option[String], expectedValues: Seq[String], after: Option[String]) extends CliException {
  override def cliMessage: String = {
    val commandS = if (unknownCommand.isDefined) s"""unknown command "$unknownCommand"""" else "emptyCommand command "
    val afterS = if (after.isDefined) s""" after "${after.get}"""" else ""
    val expectedS = if (expectedValues.nonEmpty) s" - expected {${expectedValues.mkString(",")}}" else ""
    commandS + afterS + expectedS
  }
}

object UnknownCommandException {
  def apply(unknown: String): UnknownCommandException = new UnknownCommandException(Some(unknown), Seq.empty, None)
  def apply(unknown: String, expectedValues: Seq[String]): UnknownCommandException = new UnknownCommandException(Some(unknown), expectedValues, None)
  def apply(unknown: Option[String], expectedValues: Seq[String]): UnknownCommandException = new UnknownCommandException(unknown, expectedValues, None)
  def expectedNothing(unknown: String): UnknownCommandException = new UnknownCommandException(Some(unknown), Seq("'nothing'"), None)
  def emptyCommand: UnknownCommandException = new UnknownCommandException(None, Seq.empty, None)
}

case class UnsupportedCommandException(commandName: String, cliDevice: CliDevice) extends CliException {
  override def cliMessage: String = s"$commandName is not supported by device ${cliDevice.alias getOrElse cliDevice.cliId}"
}