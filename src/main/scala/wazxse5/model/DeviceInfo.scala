package wazxse5.model

import wazxse5.connection.NetworkLocation
import wazxse5.property.{Brightness, ColorMode, Hue, PowerMode, Rgb, Saturation, Temperature}

case class DeviceInfo(
  id: String,
  model: DeviceModel,
  firmwareVersion: String,
  supportedCommands: Set[String],
  location: Option[NetworkLocation],
  isConnected: Boolean,
  power: PowerMode,
  brightness: Brightness,
  temperature: Temperature,
  rgb: Rgb,
  hue: Hue,
  saturation: Saturation,
  colorMode: ColorMode,
) {

  def withValue (
    id: String = id,
    model: DeviceModel = model,
    firmwareVersion: String = firmwareVersion,
    supportedCommands: Set[String] = supportedCommands,
    location: Option[NetworkLocation] = location,
    isConnected: Boolean = isConnected,
    power: PowerMode = power,
    brightness: Brightness = brightness,
    temperature: Temperature = temperature,
    rgb: Rgb = rgb,
    hue: Hue = hue,
    saturation: Saturation = saturation,
    colorMode: ColorMode = colorMode,
  ): DeviceInfo = DeviceInfo(
    id, model, firmwareVersion, supportedCommands, location, isConnected, power, brightness, temperature, rgb, hue, saturation, colorMode
  )
}
