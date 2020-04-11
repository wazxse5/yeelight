package wazxse5.model

import wazxse5.UID
import wazxse5.connection.NetworkLocation
import wazxse5.message.DeviceInfoMessage
import wazxse5.property._

case class DeviceInfo(
  internalId: UID,
  id: Option[String],
  model: Option[DeviceModel],
  firmwareVersion: Option[String],
  supportedCommands: Option[Set[String]],
  power: Option[PowerMode],
  brightness: Option[Brightness],
  temperature: Option[Temperature],
  rgb: Option[Rgb],
  hue: Option[Hue],
  saturation: Option[Saturation],
  colorMode: Option[ColorMode],
  location: Option[NetworkLocation],
  isConnected: Boolean
) {

  def withValue(
    id: Option[String] = id,
    model: Option[DeviceModel] = model,
    firmwareVersion: Option[String] = firmwareVersion,
    supportedCommands: Option[Set[String]] = supportedCommands,
    power: Option[PowerMode] = power,
    brightness: Option[Brightness] = brightness,
    temperature: Option[Temperature] = temperature,
    rgb: Option[Rgb] = rgb,
    hue: Option[Hue] = hue,
    saturation: Option[Saturation] = saturation,
    colorMode: Option[ColorMode] = colorMode,
    location: Option[NetworkLocation] = location,
    isConnected: Boolean = isConnected
  ): DeviceInfo = DeviceInfo(
    internalId, id, model, firmwareVersion, supportedCommands, power, brightness, temperature, rgb, hue, saturation, colorMode, location, isConnected
  )
}

object DeviceInfo {

  def apply(message: DeviceInfoMessage, isConnected: Boolean): DeviceInfo = new DeviceInfo(
    UID.generate(),
    Some(message.deviceId),
    Some(DeviceModel(message.model)),
    Some(message.firmwareVersion),
    Some(message.supportedCommands),
    Some(PowerMode(message.power)),
    Some(Brightness(message.brightness)),
    Some(Temperature(message.temperature)),
    Some(Rgb(message.rgb)),
    Some(Hue(message.hue)),
    Some(Saturation(message.saturation)),
    Some(ColorMode(message.colorMode)),
    Some(NetworkLocation(message.locationAddress, message.locationPort)),
    isConnected = isConnected
  )

  def empty: DeviceInfo = new DeviceInfo(
    UID.generate(),
    None, None, None, None, None, None, None, None, None, None, None, None,
    isConnected = false
  )

}