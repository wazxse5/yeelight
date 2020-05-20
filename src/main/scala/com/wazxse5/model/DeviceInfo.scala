package com.wazxse5.model

import com.wazxse5.InternalId
import com.wazxse5.connection.NetworkLocation
import com.wazxse5.message.{DeviceInfoMessage, NotificationMessage}
import com.wazxse5.valuetype._

case class DeviceInfo(
  internalId: InternalId,
  location: Option[NetworkLocation],
  isConnected: Boolean,
  //
  id: Option[String],
  model: Option[DeviceModel],
  firmwareVersion: Option[String],
  supportedCommands: Option[Set[String]],
  //
  power: Option[Power],
  brightness: Option[Brightness],
  temperature: Option[Temperature],
  rgb: Option[Rgb],
  hue: Option[Hue],
  saturation: Option[Saturation],
  colorMode: Option[ColorMode],
  bgPower: Option[Power] = None,
  bgBrightness: Option[Brightness] = None,
  bgTemperature: Option[Temperature] = None,
  bgRgb: Option[Rgb] = None,
  bgHue: Option[Hue] = None,
  bgSaturation: Option[Saturation] = None,
  bgColorMode: Option[ColorMode] = None
) {

  def withNotificationMessageChange(message: NotificationMessage): DeviceInfo = copy(
    power = message.power,
    brightness = message.brightness,
    temperature = message.temperature,
    rgb = message.rgb,
    hue = message.hue,
    saturation = message.saturation,
    colorMode = message.colorMode
  )
}

object DeviceInfo {

  def apply(message: DeviceInfoMessage, isConnected: Boolean): DeviceInfo = new DeviceInfo(
    InternalId.generate,
    Some(NetworkLocation(message.locationAddress, message.locationPort)),
    isConnected,
    Some(message.deviceId),
    Some(DeviceModel(message.model)),
    Some(message.firmwareVersion),
    Some(message.supportedCommands),
    Some(Power(message.power)),
    Some(Brightness(message.brightness)),
    Some(Temperature(message.temperature)),
    Some(Rgb(message.rgb)),
    Some(Hue(message.hue)),
    Some(Saturation(message.saturation)),
    Some(ColorMode(message.colorMode))
  )

  def empty: DeviceInfo = new DeviceInfo(
    InternalId.generate,
    None,
    isConnected = false,
    None, None, None, None, None, None, None, None, None, None, None
  )

  def apply(location: NetworkLocation): DeviceInfo = empty.copy(location = Some(location))

}