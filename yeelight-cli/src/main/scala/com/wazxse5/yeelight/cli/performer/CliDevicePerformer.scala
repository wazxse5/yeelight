package com.wazxse5.yeelight.cli.performer

import com.wazxse5.yeelight.cli.CliCommands._
import com.wazxse5.yeelight.cli.exception._
import com.wazxse5.yeelight.cli.{CliCommand, CliCommands, CliDevice}
import com.wazxse5.yeelight.command._
import com.wazxse5.yeelight.valuetype._

object CliDevicePerformer {

  def decrease(cliCommand: CliCommand)(implicit cliDevice: CliDevice): Unit = adjust(cliCommand, -1)

  def increase(cliCommand: CliCommand)(implicit cliDevice: CliDevice): Unit = adjust(cliCommand)

  private def adjust(cliCommand: CliCommand, multiplyPercent: Int = 1)(implicit cliDevice: CliDevice): Unit = {
    val (valueTypeS, percentS, durationS) = cliCommand.pop3 match {
      case v :: p :: d :: Nil => (v, Some(p), Some(d))
      case v :: p :: Nil => (v, Some(p), None)
      case v :: Nil => (v, None, None)
      case _ => throw InvalidCommandException(None)
    }

    if (percentS.exists(isNotNumber)) throw InvalidParamValueCommandException(Percent.paramName, percentS)
    if (durationS.exists(isNotNumber)) throw InvalidParamValueCommandException(Duration.paramName, durationS)
    val percent = percentS.map(_.toInt).getOrElse(20) * multiplyPercent
    val duration = durationS.map(_.toInt).getOrElse(500)

    val command = valueTypeS match {
      case device.decrease.brightness => AdjustBrightness(percent, duration)
      case device.decrease.temperature => AdjustTemperature(percent, duration)
      case other => throw InvalidCommandException(other)
    }
    performCommand(command)
  }

  def get(cliCommand: CliCommand)(implicit cliDevice: CliDevice): Unit = {
    throw NotImplementedCommandException(device.get_)
  }

  def set(cliCommand: CliCommand, isAliasTaken: String => Boolean)(implicit cliDevice: CliDevice): Unit = {

    def setAlias: Unit = {
      cliCommand.popOpt match {
        case Some(alias) =>
          if (CliCommands._keywords.contains(alias)) throw AliasUnavailableException.reservedKeyword(alias)
          else if (isAliasTaken(alias)) throw AliasUnavailableException.taken(alias)
          else cliDevice.setAlias(alias)
        case None => throw InvalidParamValueCommandException("alias", None)
      }
    }

    def setBrightnessCommand: SetBrightness = {
      val (brightness, effect, duration) = cliCommand.pop3 match {
        case b :: d :: Nil =>
          if (isNotNumber(b)) throw InvalidParamValueCommandException(Brightness.paramName, b)
          else if (isNotNumber(d)) throw InvalidParamValueCommandException(Duration.paramName, d)
          else (Brightness(b.toInt), Effect.smooth, Duration(d.toInt))
        case b :: Nil =>
          if (isNotNumber(b)) throw InvalidParamValueCommandException(Brightness.paramName, b)
          else (Brightness(b.toInt), Effect.sudden, Duration(0))
        case other => throw InvalidCommandException(other)
      }
      SetBrightness(brightness, effect, duration)
    }

    def setColorCommand: SetRgb = throw NotImplementedCommandException(device.set.colorMode)

    def setColorModeCommand: YeelightCommand = throw NotImplementedCommandException(device.set.colorMode)

    def setHsvCommand: YeelightCommand = throw NotImplementedCommandException(device.set.hsv)

    def setIpCommand: YeelightCommand = throw NotImplementedCommandException(device.set.ip)

    def setMusicCommand: YeelightCommand = throw NotImplementedCommandException(device.set.music)

    def setNameCommand: SetName = cliCommand.popOpt match {
      case Some(name) => SetName(name)
      case None => throw InvalidParamValueCommandException(Name.paramName, None)
    }

    def setPortCommand: YeelightCommand = throw NotImplementedCommandException(device.set.port)

    def setTemperatureCommand: SetTemperature = {
      val (temperature, effect, duration) = cliCommand.pop3 match {
        case t :: d :: Nil =>
          if (isNotNumber(t)) throw InvalidParamValueCommandException(Temperature.paramName, t)
          else if (isNotNumber(d)) throw InvalidParamValueCommandException(Duration.paramName, d)
          else (Temperature(t.toInt), Effect.smooth, Duration(d.toInt))
        case t :: Nil =>
          if (isNotNumber(t)) throw InvalidParamValueCommandException(Temperature.paramName, t)
          else (Temperature(t.toInt), Effect.sudden, Duration(0))
        case other => throw InvalidCommandException(other)
      }
      SetTemperature(temperature, effect, duration)
    }

    def setTimerCommand: YeelightCommand = throw NotImplementedCommandException(device.set.timer)

    if (cliCommand.isEmpty) throw InvalidCommandException(None)
    cliCommand.pop match {
      case device.set.alias => setAlias
      case device.set.brightness => performCommand(setBrightnessCommand)
      case device.set.color => performCommand(setColorCommand)
      case device.set.colorMode => performCommand(setColorModeCommand)
      case device.set.hsv => performCommand(setHsvCommand)
      case device.set.ip => performCommand(setIpCommand)
      case device.set.music => performCommand(setMusicCommand)
      case device.set.name => performCommand(setNameCommand)
      case device.set.on => on(cliCommand)
      case device.set.off => off(cliCommand)
      case device.set.port => performCommand(setPortCommand)
      case device.set.power => setPower(cliCommand)
      case device.set.temperature => performCommand(setTemperatureCommand)
      case device.set.timer => performCommand(setTimerCommand)
      case other => throw InvalidCommandException(Some(other))
    }
  }

  def off(cliCommand: CliCommand)(implicit cliDevice: CliDevice): Unit = {
    cliCommand.push(device.set.off)
    setPower(cliCommand)
  }

  def on(cliCommand: CliCommand)(implicit cliDevice: CliDevice): Unit = {
    cliCommand.push(device.set.on)
    setPower(cliCommand)
  }

  private def setPower(cliCommand: CliCommand)(implicit cliDevice: CliDevice): Unit = {
    val power = cliCommand.popOpt match {
      case Some(p) => Power.fromString(p)
      case o => throw InvalidParamValueCommandException(Power.paramName, o)
    }

    val (effect, duration) = cliCommand.pop2 match {
      case d :: Nil  => (Effect.smooth, Duration.fromString(d))
      case Nil => (Effect.sudden, Some(Duration(0)))
      case other => throw InvalidCommandException(other.mkString(" "))
    }

    if (power.nonEmpty && duration.nonEmpty) {
      performCommand(SetPower(power.get, effect, duration.get))
    }
  }

  def refresh(cliCommand: CliCommand)(implicit cliDevice: CliDevice): Unit = {
    cliCommand.popOpt match {
      case None => performCommand(GetProps.all)
      case Some(value) => throw InvalidCommandException.expectedNothing(value)
    }
  }

  def state(cliCommand: CliCommand)(implicit cliDevice: CliDevice): Unit = {
    cliCommand.popOpt match {
      case None => println(cliDevice.state)
      case Some(value) => throw InvalidCommandException.expectedNothing(value)
    }
  }

  def toggle(cliCommand: CliCommand)(implicit cliDevice: CliDevice): Unit = {
    cliCommand.popOpt match {
      case None => performCommand(Toggle)
      case Some(value) => throw InvalidCommandException.expectedNothing(value)
    }
  }

  private def performCommand(command: YeelightCommand)(implicit cliDevice: CliDevice): Unit = {
    cliDevice.yeelightDevice.performCommand(command)
  }

  private def isNumber(string: String): Boolean = string.forall(_.isDigit)

  private def isNotNumber(string: String): Boolean = !isNumber(string)
}
