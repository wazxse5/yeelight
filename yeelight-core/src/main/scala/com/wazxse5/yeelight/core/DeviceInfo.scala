package com.wazxse5.yeelight.core

import com.wazxse5.yeelight.core.Change.OptionChange
import com.wazxse5.yeelight.message.{AdvertisementMessage, DiscoveryResponseMessage}
import com.wazxse5.yeelight.valuetype._

case class DeviceInfo(
  deviceId: String,
  model: Option[DeviceModel],
  firmwareVersion: Option[String],
  supportedCommands: Option[Set[String]],
  //
  ipAddress: Option[IpAddress],
  port: Option[Port],
  isConnected: Boolean,
  //
  brightness: Option[Brightness],
  colorMode: Option[ColorMode],
  flowExpression: Option[FlowExpression],
  flowPower: Option[FlowPower],
  hue: Option[Hue],
  musicPower: Option[MusicPower],
  name: Option[Name],
  power: Option[Power],
  rgb: Option[Rgb],
  saturation: Option[Saturation],
  temperature: Option[Temperature],
  timerValue: Option[TimerValue],
  //
  bgBrightness: Option[Brightness],
  bgColorMode: Option[ColorMode],
  bgFlowExpression: Option[FlowExpression],
  bgFlowPower: Option[FlowPower],
  bgHue: Option[Hue],
  bgPower: Option[Power],
  bgRgb: Option[Rgb],
  bgSaturation: Option[Saturation],
  bgTemperature: Option[Temperature],
  //
  nlBrightness: Option[Brightness],
  activeMode: Option[ActiveMode]
) {

  def update(update: DeviceInfoChange): DeviceInfo = copy(
    model = model.withChange(update.model),
    firmwareVersion = firmwareVersion.withChange(update.firmwareVersion),
    supportedCommands = supportedCommands.withChange(update.supportedCommands),
    //
    brightness = brightness.withChange(update.brightness),
    colorMode = colorMode.withChange(update.colorMode),
    flowExpression = flowExpression.withChange(update.flowExpression),
    flowPower = flowPower.withChange(update.flowPower),
    hue = hue.withChange(update.hue),
    musicPower = musicPower.withChange(update.musicPower),
    name = name.withChange(update.name),
    power = power.withChange(update.power),
    rgb = rgb.withChange(update.rgb),
    saturation = saturation.withChange(update.saturation),
    temperature = temperature.withChange(update.temperature),
    timerValue = timerValue.withChange(update.timerValue),
    //
    bgBrightness = bgBrightness.withChange(update.bgBrightness),
    bgColorMode = bgColorMode.withChange(update.bgColorMode),
    bgFlowExpression = bgFlowExpression.withChange(update.bgFlowExpression),
    bgFlowPower = bgFlowPower.withChange(update.bgFlowPower),
    bgHue = bgHue.withChange(update.bgHue),
    bgPower = bgPower.withChange(update.bgPower),
    bgRgb = bgRgb.withChange(update.bgRgb),
    bgSaturation = bgSaturation.withChange(update.bgSaturation),
    bgTemperature = bgTemperature.withChange(update.bgTemperature),
    //
    nlBrightness = nlBrightness.withChange(update.nlBrightness),
    activeMode = activeMode.withChange(update.activeMode)
  )
}

object DeviceInfo {

  def fromDiscoveryResponse(message: DiscoveryResponseMessage): DeviceInfo = new DeviceInfo(
    message.deviceId,
    DeviceModel.fromString(message.model),
    Some(message.firmwareVersion),
    Some(message.supportedCommands),
    //
    IpAddress.fromString(message.location),
    Port.fromString(message.location),
    isConnected = false,
    //
    brightness = Brightness.fromString(message.brightness),
    colorMode = ColorMode.fromString(message.colorMode),
    flowExpression = None, flowPower = None,
    hue = Hue.fromString(message.hue),
    musicPower = None,
    name = Name.fromString(message.name),
    power = Power.fromString(message.power),
    rgb = Rgb.fromString(message.rgb),
    saturation = Saturation.fromString(message.saturation),
    temperature = Temperature.fromString(message.temperature),
    timerValue = None, bgBrightness = None, bgColorMode = None, bgFlowExpression = None, bgFlowPower = None, bgHue = None, bgPower = None, bgRgb = None, bgSaturation = None, bgTemperature = None, nlBrightness = None, activeMode = None
  )

  def fromAdvertisement(message: AdvertisementMessage): DeviceInfo = new DeviceInfo(
    message.deviceId,
    DeviceModel.fromString(message.model),
    Some(message.firmwareVersion),
    Some(message.supportedCommands),
    //
    IpAddress.fromString(message.location),
    Port.fromString(message.location),
    isConnected = false,
    //
    brightness = Brightness.fromString(message.brightness),
    colorMode = ColorMode.fromString(message.colorMode),
    flowExpression = None, flowPower = None,
    hue = Hue.fromString(message.hue),
    musicPower = None,
    name = Name.fromString(message.name),
    power = Power.fromString(message.power),
    rgb = Rgb.fromString(message.rgb),
    saturation = Saturation.fromString(message.saturation),
    temperature = Temperature.fromString(message.temperature),
    timerValue = None, bgBrightness = None, bgColorMode = None, bgFlowExpression = None, bgFlowPower = None, bgHue = None, bgPower = None, bgRgb = None, bgSaturation = None, bgTemperature = None, nlBrightness = None, activeMode = None
  )

}