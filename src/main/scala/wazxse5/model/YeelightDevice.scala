package wazxse5.model

import wazxse5.command.YeelightCommand
import wazxse5.connection.NetworkLocation
import wazxse5.property.{Brightness, ColorMode, Hue, PowerMode, Rgb, Saturation, Temperature}

class YeelightDevice private(
  val id: String,
  val model: DeviceModel,
  val state: IYeelightState,
  val service: IYeelightService,
) extends IYeelightDevice {

  override def location: Option[NetworkLocation] = service.deviceInfo(id).flatMap(_.location)

  override def firmwareVersion: Option[String] = service.deviceInfo(id).map(_.firmwareVersion)

  override def supportedCommands: Set[String] = service.deviceInfo(id).map(_.supportedCommands).getOrElse(Set.empty)

  override def isConnected: Boolean = service.deviceInfo(id).exists(_.isConnected)

  override def performCommand(command: YeelightCommand): Unit = service.performCommand(id, command)

}

object YeelightDevice {
  def apply(
    deviceInfo: DeviceInfo,
    service: IYeelightService
  ): YeelightDevice = {
    val state = new YeelightState(deviceInfo.id, service)
    new YeelightDevice(deviceInfo.id, deviceInfo.model, state, service)
  }

}
