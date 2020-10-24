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

  def state: String = { // TODO dodać pozostałe po dostosowaniu deviceInfo
    s"""|brightness  = ${getPropOrUnknown(yeelightDevice.state.brightness)}
        |colorMode   = ${getPropOrUnknown(yeelightDevice.state.colorMode)}
        |hue         = ${getPropOrUnknown(yeelightDevice.state.hue)}
        |power       = ${getPropOrUnknown(yeelightDevice.state.power)}
        |rgb         = ${getPropOrUnknown(yeelightDevice.state.rgb)}
        |saturation  = ${getPropOrUnknown(yeelightDevice.state.saturation)}
        |temperature = ${getPropOrUnknown(yeelightDevice.state.temperature)}""".stripMargin
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
