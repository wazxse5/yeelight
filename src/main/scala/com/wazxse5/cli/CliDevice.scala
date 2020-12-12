package com.wazxse5.cli

import com.wazxse5.cli.CliDevice._
import com.wazxse5.core.YeelightDevice
import com.wazxse5.valuetype.ValueType


class CliDevice private(_yeelightDevice: YeelightDevice, _alias: Option[String]) {
  val cliId: String = nextCliId
  var alias: Option[String] = _alias
  val yeelightDevice: YeelightDevice = _yeelightDevice

  def setAlias(newAlias: Option[String]): Unit = alias = newAlias

  def setAlias(newAlias: String): Unit = setAlias(Some(newAlias))

  def simpleInfo: String = {
    s"n=$cliId\tmodel=${getPropOrUnknown(yeelightDevice.model)}\talias=${getOrNone(alias)}\tid=${getOrUnknown(yeelightDevice.id)}"
  }

  def state: String = {
    s"""|POWER = ${yeelightDevice.state.power.strValue} (last update = ${yeelightDevice.state.lastUpdate})
        |brightness  = ${yeelightDevice.state.brightness.strValue}
        |colorMode   = ${yeelightDevice.state.colorMode.strValue}
        |flow        = ${yeelightDevice.state.flowPower.strValue}
        |hue         = ${yeelightDevice.state.hue.strValue}
        |music       = ${yeelightDevice.state.musicPower.strValue}
        |name        = ${yeelightDevice.state.name.strValue}
        |rgb         = ${yeelightDevice.state.rgb.strValue}
        |saturation  = ${yeelightDevice.state.saturation.strValue}
        |temperature = ${yeelightDevice.state.temperature.strValue}
        |timer       = ${yeelightDevice.state.timerValue.strValue}""".stripMargin
  }

  private def getPropOrUnknown(property: Option[ValueType[_]]): String = property.map(_.strValue).getOrElse("unknown")
  private def getOrUnknown(optStr: Option[String]): String = optStr.getOrElse("unknown")
  private def getOrNone(optStr: Option[String]): String = optStr.getOrElse("none")
}

object CliDevice {
  def apply(yeelightDevice: YeelightDevice, alias: Option[String] = None): CliDevice =
    new CliDevice(yeelightDevice, alias)

  private var cliIdCounter = 0

  private def nextCliId: String = {
    cliIdCounter += 1
    cliIdCounter.toString
  }
}
