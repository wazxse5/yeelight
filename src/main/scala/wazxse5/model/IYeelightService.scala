package wazxse5.model

import wazxse5.command.YeelightCommand

trait IYeelightService {

  def devices: Set[IYeelightDevice]

  def deviceInfo(deviceId: String): Option[DeviceInfo]

  def search(): Unit

  def startListening(): Unit

  def stopListening(): Unit

  def performCommand(deviceId: String, command: YeelightCommand): Unit
}
