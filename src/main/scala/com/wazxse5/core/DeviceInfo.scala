package com.wazxse5.core

import com.wazxse5.api.InternalId
import com.wazxse5.api.message.{DeviceInfoMessage, NotificationMessage}
import com.wazxse5.api.valuetype._
import com.wazxse5.core.connection.NetworkLocation

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
    power = message.power.orElse(power),
    brightness = message.brightness.orElse(brightness),
    temperature = message.temperature.orElse(temperature),
    rgb = message.rgb.orElse(rgb),
    hue = message.hue.orElse(hue),
    saturation = message.saturation.orElse(saturation),
    colorMode = message.colorMode.orElse(colorMode)
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