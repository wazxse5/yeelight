package wazxse5.model

import java.time.LocalDateTime

import wazxse5.property._

class YeelightState(
  deviceId: String,
  deviceService: IYeelightService
) extends IYeelightState {

  override def power: PowerMode = deviceService.deviceInfo(deviceId).map(_.power).getOrElse(PowerMode.Unknown)

  override def brightness: Brightness = deviceService.deviceInfo(deviceId).map(_.brightness).getOrElse(Brightness.Unknown)

  override def temperature: Temperature = deviceService.deviceInfo(deviceId).map(_.temperature).getOrElse(Temperature.Unknown)

  override def rgb: Rgb = deviceService.deviceInfo(deviceId).map(_.rgb).getOrElse(Rgb.Unknown)

  override def hue: Hue = deviceService.deviceInfo(deviceId).map(_.hue).getOrElse(Hue.Unknown)

  override def saturation: Saturation = deviceService.deviceInfo(deviceId).map(_.saturation).getOrElse(Saturation.Unknown)

  override def colorMode: ColorMode = deviceService.deviceInfo(deviceId).map(_.colorMode).getOrElse(ColorMode.Unknown)

  override def lastUpdate: Option[LocalDateTime] = None // TODO:
}

object YeelightState {
  def apply(device: IYeelightDevice): YeelightState = new YeelightState(device.id, device.service)
}