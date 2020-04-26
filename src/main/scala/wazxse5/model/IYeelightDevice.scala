package wazxse5.model

import wazxse5.UID
import wazxse5.command.YeelightCommand
import wazxse5.connection.NetworkLocation
import wazxse5.valuetype.DeviceModel

trait IYeelightDevice { // TODO: Pomyśleć nad lepszą nazwą

  val internalId: UID

  def id: Option[String]

  def model: Option[DeviceModel]

  def firmwareVersion: Option[String]

  def supportedCommands: Option[Set[String]]

  def location: Option[NetworkLocation]

  def isConnected: Boolean

  def state: IYeelightState

  def service: IYeelightService

  def performCommand(command: YeelightCommand): Unit

}
