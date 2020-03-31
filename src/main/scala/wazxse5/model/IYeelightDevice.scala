package wazxse5.model

import wazxse5.command.YeelightCommand
import wazxse5.connection.NetworkLocation
import wazxse5.property.{Brightness, ColorMode, Hue, PowerMode, Rgb, Saturation, Temperature}

trait IYeelightDevice { // TODO: Pomyśleć nad lepszą nazwą

  val id: String

  val model: DeviceModel

  def firmwareVersion: Option[String]

  def supportedCommands: Set[String]

  def location: Option[NetworkLocation]

  def isConnected: Boolean

  def state: IYeelightState

  def service: IYeelightService

  def performCommand(command: YeelightCommand): Unit // TODO: zwracany typ

}
