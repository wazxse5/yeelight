package com.wazxse5.cli

import com.wazxse5.api.model.IYeelightDevice
import com.wazxse5.cli.CliDevice._

class CliDevice private(_yeelightDevice: IYeelightDevice, _alias: Option[String]) {
  val cliId: String = nextCliId
  var alias: Option[String] = _alias
  val yeelightDevice: IYeelightDevice = _yeelightDevice

  def setAlias(newAlias: Option[String]): Unit = alias = newAlias

  def setAlias(newAlias: String): Unit = setAlias(Some(newAlias))

  def simpleInfo: String = {
    s"cliId=$cliId\talias=$alias\tip=${yeelightDevice.location}\tid=${yeelightDevice.id}"
  }

}

object CliDevice {
  def apply(yeelightDevice: IYeelightDevice, alias: Option[String] = None): CliDevice =
    new CliDevice(yeelightDevice, alias)

  private var cliIdCounter = 0

  private def nextCliId: String = {
    cliIdCounter += 1
    cliIdCounter.toString
  }
}
