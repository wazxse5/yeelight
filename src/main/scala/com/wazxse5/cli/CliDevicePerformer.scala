package com.wazxse5.cli

import com.wazxse5.cli.CliCommands._
import com.wazxse5.cli.exception.{InvalidParamValueCommandException, NotImplementedCommandException, UnknownCommandException}
import com.wazxse5.command.{AdjustBrightness, AdjustTemperature, GetProps, SetBrightness, SetName, SetPower, SetRgb, SetTemperature, Toggle, YeelightCommand}
import com.wazxse5.valuetype._

object CliDevicePerformer {

  def decrease(commands: List[String])(implicit cliDevice: CliDevice): Unit = {
    def decreaseBrightness(commands: List[String]): AdjustBrightness = commands match {
      case percent :: duration :: Nil if areNumbers(percent, duration) => AdjustBrightness(-percent.toInt, duration.toInt)
      case percent :: Nil if isNumber(percent) => AdjustBrightness(-percent.toInt)
      case Nil => AdjustBrightness(-20)
      case other => throw InvalidParamValueCommandException(Brightness.paramName, other)
    }

    def decreaseTemperature(commands: List[String]): AdjustTemperature = commands match {
      case percent :: duration :: Nil if areNumbers(percent, duration) => AdjustTemperature(-percent.toInt, duration.toInt)
      case percent :: Nil if isNumber(percent) => AdjustTemperature(-percent.toInt)
      case Nil => AdjustTemperature(-20)
      case other => throw InvalidParamValueCommandException(Temperature.paramName, other)
    }

    commands match {
      case device.decrease.brightness :: tail => performCommand(decreaseBrightness(tail))
      case device.decrease.temperature :: tail => performCommand(decreaseTemperature(tail))
      case other => UnknownCommandException(other.headOption, device.decrease._commands)
    }
  }

  def increase(commands: List[String])(implicit cliDevice: CliDevice): Unit = {
    def increaseBrightness(commands: List[String]): AdjustBrightness = commands match {
      case percent :: duration :: Nil if areNumbers(percent, duration) => AdjustBrightness(percent.toInt, duration.toInt)
      case percent :: Nil if isNumber(percent) => AdjustBrightness(percent.toInt)
      case Nil => AdjustBrightness.apply
      case other => throw InvalidParamValueCommandException(Brightness.paramName, other)
    }

    def increaseTemperature(commands: List[String]): AdjustTemperature = commands match {
      case percent :: duration :: Nil if areNumbers(percent, duration) => AdjustTemperature(percent.toInt, duration.toInt)
      case percent :: Nil if isNumber(percent) => AdjustTemperature(percent.toInt)
      case Nil => AdjustTemperature.apply
      case other => throw InvalidParamValueCommandException(Temperature.paramName, other)
    }

    commands match {
      case device.increase.brightness :: tail => performCommand(increaseBrightness(tail))
      case device.increase.temperature :: tail => performCommand(increaseTemperature(tail))
      case other => UnknownCommandException(other.headOption, device.decrease._commands)
    }
  }

  def get(commands: List[String])(implicit cliDevice: CliDevice): Unit = {
    throw NotImplementedCommandException(device.get_)
  }

  def set(commands: List[String], cli: CLI)(implicit cliDevice: CliDevice): Unit = {
    def setAlias(commands: List[String]): Unit = commands match {
      case alias :: Nil if cli.isAliasAvailable(alias) => cliDevice.setAlias(alias)
      case other => throw InvalidParamValueCommandException("alias", other)
    }

    def setBrightnessCommand(commands: List[String]): SetBrightness = commands match {
      case brightness :: effect :: duration :: Nil if areNumbers(brightness, duration) && Effect.values.contains(effect) =>
        SetBrightness(Brightness(brightness.toInt), Effect(effect), Duration(duration.toInt))
      case brightness :: Nil if isNumber(brightness) => SetBrightness(brightness.toInt)
      case other => throw InvalidParamValueCommandException(Brightness.paramName, other)
    }

    def setColorCommand(commands: List[String]): SetRgb = commands match {
      case r :: g :: b :: Nil if areNumbers(r, g, b) => SetRgb(Rgb(r.toInt, g.toInt, b.toInt))
      case colorNumber :: Nil if isNumber(colorNumber) => SetRgb(colorNumber.toInt)
      case colorName :: Nil if Rgb.predefinedColorsNames.contains(colorName) => SetRgb(Rgb(colorName))
      case other => throw InvalidParamValueCommandException(Rgb.paramName, other)
    }

    def setColorModeCommand(commands: List[String]): YeelightCommand = throw NotImplementedCommandException(device.set.colorMode)

    def setHsvCommand(commands: List[String]): YeelightCommand = throw NotImplementedCommandException(device.set.hsv)

    def setIpCommand(commands: List[String]): YeelightCommand = throw NotImplementedCommandException(device.set.ip)

    def setMusicCommand(commands: List[String]): YeelightCommand = throw NotImplementedCommandException(device.set.music)

    def setNameCommand(commands: List[String]): SetName = commands match {
      case name :: Nil => SetName(name)
      case other => throw InvalidParamValueCommandException(Name.paramName, other)
    }

    def setPortCommand(commands: List[String]): YeelightCommand = throw NotImplementedCommandException(device.set.port)

    def setPower(commands: List[String]): Unit = commands match {
      case device.set.on :: tail => on(tail)
      case device.set.off :: tail  => off(tail)
      case other => throw InvalidParamValueCommandException(Power.paramName, other)
    }

    def setTemperatureCommand(commands: List[String]): SetTemperature = commands match {
      case temperature :: effect :: duration :: Nil if areNumbers(temperature, duration) && Effect.values.contains(effect) =>
        SetTemperature(Temperature(temperature.toInt), Effect(effect), Duration(duration.toInt))
      case temperature :: Nil if isNumber(temperature) => SetTemperature(temperature.toInt)
      case other => throw InvalidParamValueCommandException(Temperature.paramName, other)
    }

    def setTimerCommand(commands: List[String]): YeelightCommand = throw NotImplementedCommandException(device.set.timer)

    commands match {
      case device.set.alias :: tail => setAlias(tail)
      case device.set.brightness :: tail => performCommand(setBrightnessCommand(tail))
      case device.set.color :: tail => performCommand(setColorCommand(tail))
      case device.set.colorMode :: tail => performCommand(setColorModeCommand(tail))
      case device.set.hsv :: tail => performCommand(setHsvCommand(tail))
      case device.set.ip :: tail => performCommand(setIpCommand(tail))
      case device.set.music :: tail => performCommand(setMusicCommand(tail))
      case device.set.name :: tail => performCommand(setNameCommand(tail))
      case device.set.on :: tail => on(tail)
      case device.set.off :: tail => off(tail)
      case device.set.port :: tail => performCommand(setPortCommand(tail))
      case device.set.power :: tail => setPower(tail)
      case device.set.temperature :: tail => performCommand(setTemperatureCommand(tail))
      case device.set.timer :: tail => performCommand(setTimerCommand(tail))
      case other => throw UnknownCommandException(other.headOption, device.set._commands, Some(device.set_))
    }
  }

  def off(commands: List[String])(implicit cliDevice: CliDevice): Unit = {
    val command = commands match {
      case effect :: duration :: Nil if Effect.values.contains(effect) && isNumber(duration) =>
        SetPower(Power.off, Effect(effect), Duration(duration.toInt), None)
      case Nil => SetPower(Power.off)
      case other => throw InvalidParamValueCommandException(Power.paramName, other)
    }
    performCommand(command)
  }

  def on(commands: List[String])(implicit cliDevice: CliDevice): Unit = {
    val command = commands match {
      case effect :: duration :: Nil if Effect.values.contains(effect) && isNumber(duration) =>
        SetPower(Power.on, Effect(effect), Duration(duration.toInt), None)
      case Nil => SetPower(Power.on)
      case other => throw InvalidParamValueCommandException(Power.paramName, other)
    }
    performCommand(command)
  }

  def refresh(commands: List[String])(implicit cliDevice: CliDevice): Unit = {
    if (commands.isEmpty) performCommand(GetProps.all)
    else throw UnknownCommandException.expectedNothing(commands.head)
  }

  def state(commands: List[String])(implicit cliDevice: CliDevice): Unit = {
    println(cliDevice.state)
  }

  def toggle(commands: List[String])(implicit cliDevice: CliDevice): Unit = {
    if (commands.isEmpty) performCommand(Toggle)
    else throw UnknownCommandException.expectedNothing(commands.head)
  }

  private def performCommand(command: YeelightCommand)(implicit cliDevice: CliDevice): Unit = {
    cliDevice.yeelightDevice.performCommand(command)
  }

  private def isNumber(string: String): Boolean = string.forall(_.isDigit)

  private def areNumbers(string: String*) = string.forall(isNumber)

}
