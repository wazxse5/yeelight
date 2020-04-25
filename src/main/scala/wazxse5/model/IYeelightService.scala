package wazxse5.model

import wazxse5.UID
import wazxse5.command.YeelightCommand

trait IYeelightService {

  def devices: Set[IYeelightDevice]

  def deviceInfo(internalId: UID): Option[DeviceInfo]

  def deviceOf(deviceInfo: DeviceInfo): IYeelightDevice

  def deviceOf(address: String, port: Int = 55443): IYeelightDevice

  def search(): Unit

  def startListening(): Unit

  def stopListening(): Unit

  def performCommand(internalId: UID, command: YeelightCommand): Unit
}
