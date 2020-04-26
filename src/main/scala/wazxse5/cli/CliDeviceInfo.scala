package wazxse5.cli

import wazxse5.cli.CliDeviceInfo._
import wazxse5.model.IYeelightDevice

class CliDeviceInfo private (_yeelightDevice: IYeelightDevice, _alias: Option[String]) {
  val cliId: String = nextCliId
  var alias: Option[String] = _alias
  val yeelightDevice: IYeelightDevice = _yeelightDevice

  def setAlias(newAlias: Option[String]): Unit = alias = newAlias

  def setAlias(newAlias: String): Unit = setAlias(Some(newAlias))

  def simpleInfo: String = {
    s"cliId=$cliId\talias=$alias\tip=${yeelightDevice.location}\tid=${yeelightDevice.id}"
  }

}

object CliDeviceInfo {
  def apply(yeelightDevice: IYeelightDevice, alias: Option[String] = None): CliDeviceInfo =
    new CliDeviceInfo(yeelightDevice, alias)

  private var cliIdCounter = 0

  private def nextCliId: String = {
    cliIdCounter += 1
    cliIdCounter.toString
  }
}
