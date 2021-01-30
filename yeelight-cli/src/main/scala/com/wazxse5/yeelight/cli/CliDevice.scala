package com.wazxse5.yeelight.cli

import com.wazxse5.yeelight.cli.CliDevice._
import com.wazxse5.yeelight.core.YeelightDevice
import com.wazxse5.yeelight.valuetype.ValueType


class CliDevice private(_yeelightDevice: YeelightDevice, _alias: Option[String]) {
  val cliId: String = nextCliId
  var alias: Option[String] = _alias
  val yeelightDevice: YeelightDevice = _yeelightDevice

  def setAlias(newAlias: Option[String]): Unit = alias = newAlias

  def setAlias(newAlias: String): Unit = setAlias(Some(newAlias))

  def simpleInfo: String = {
    s"n=$cliId\tmodel=${getPropOrUnknown(yeelightDevice.model)}\talias=${getOrNone(alias)}\tid=${yeelightDevice.deviceId}"
  }

  def state: String = {
    s"""|POWER = ${yeelightDevice.state.power.strValueOrUnknown} (last update = ${yeelightDevice.state.lastUpdate})
        |brightness  = ${yeelightDevice.state.brightness.strValueOrUnknown}
        |colorMode   = ${yeelightDevice.state.colorMode.strValueOrUnknown}
        |flow        = ${yeelightDevice.state.flowPower.strValueOrUnknown}
        |hue         = ${yeelightDevice.state.hue.strValueOrUnknown}
        |music       = ${yeelightDevice.state.musicPower.strValueOrUnknown}
        |name        = ${yeelightDevice.state.name.strValueOrUnknown}
        |rgb         = ${yeelightDevice.state.rgb.strValueOrUnknown}
        |saturation  = ${yeelightDevice.state.saturation.strValueOrUnknown}
        |temperature = ${yeelightDevice.state.temperature.strValueOrUnknown}
        |timer       = ${yeelightDevice.state.timerValue.strValueOrUnknown}""".stripMargin
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
