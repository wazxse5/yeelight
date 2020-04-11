package wazxse5.model

import wazxse5.UID
import wazxse5.command.YeelightCommand
import wazxse5.connection.NetworkLocation

trait IYeelightService {

  def devices: Set[IYeelightDevice]

  def deviceInfo(internalId: UID): Option[DeviceInfo]

  def deviceOf(deviceInfo: DeviceInfo): IYeelightDevice

  def deviceOf(location: NetworkLocation): IYeelightDevice

  def search(): Unit

  def startListening(): Unit

  def stopListening(): Unit

  def performCommand(internalId: UID, command: YeelightCommand): Unit
}
