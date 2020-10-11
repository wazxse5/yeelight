package com.wazxse5.cli

import com.wazxse5.cli.CliDevice._
import com.wazxse5.core.YeelightDevice
import com.wazxse5.valuetype.Property


class CliDevice private(_yeelightDevice: YeelightDevice, _alias: Option[String]) {
  val cliId: String = nextCliId
  var alias: Option[String] = _alias
  val yeelightDevice: YeelightDevice = _yeelightDevice

  def setAlias(newAlias: Option[String]): Unit = alias = newAlias

  def setAlias(newAlias: String): Unit = setAlias(Some(newAlias))

  def simpleInfo: String = {
    s"cliId=$cliId\talias=$alias\tip=${yeelightDevice.location}\tid=${yeelightDevice.id}"
  }

  def state: String = { // TODO dodać pozostałe po dostosowaniu deviceInfo
    s"""|brightness  = ${optPropToString(yeelightDevice.state.brightness)}
        |colorMode   = ${optPropToString(yeelightDevice.state.colorMode)}
        |hue         = ${optPropToString(yeelightDevice.state.hue)}
        |power       = ${optPropToString(yeelightDevice.state.power)}
        |rgb         = ${optPropToString(yeelightDevice.state.rgb)}
        |saturation  = ${optPropToString(yeelightDevice.state.saturation)}
        |temperature = ${optPropToString(yeelightDevice.state.temperature)}""".stripMargin
  }

  private def optPropToString(property: Option[Property[_]]): String = property.map(_.rawValue).getOrElse("unknown")
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
