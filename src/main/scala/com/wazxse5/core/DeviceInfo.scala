package com.wazxse5.core

import com.wazxse5.connection.NetworkLocation
import com.wazxse5.message.DeviceInfoMessage
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
  brightness: Option[Brightness] = None,
  colorMode: Option[ColorMode] = None,
  flowExpression: Option[FlowExpression] = None,
  flowPower: Option[FlowPower] = None,
  hue: Option[Hue] = None,
  musicPower: Option[MusicPower] = None,
  name: Option[Name] = None,
  power: Option[Power] = None,
  rgb: Option[Rgb] = None,
  saturation: Option[Saturation] = None,
  temperature: Option[Temperature] = None,
  timerValue: Option[TimerValue] = None,
  //
  bgBrightness: Option[Brightness] = None,
  bgColorMode: Option[ColorMode] = None,
  bgFlowExpression: Option[FlowExpression] = None,
  bgFlowPower: Option[FlowPower] = None,
  bgHue: Option[Hue] = None,
  bgPower: Option[Power] = None,
  bgRgb: Option[Rgb] = None,
  bgSaturation: Option[Saturation] = None,
  bgTemperature: Option[Temperature] = None,
) {

  def withStateUpdate(stateUpdate: StateUpdate): DeviceInfo = copy(
    brightness = stateUpdate.brightness.orElse(brightness),
    colorMode = stateUpdate.colorMode.orElse(colorMode),
    flowExpression = stateUpdate.flowExpression.orElse(flowExpression),
    flowPower = stateUpdate.flowPower.orElse(flowPower),
    hue = stateUpdate.hue.orElse(hue),
    musicPower = stateUpdate.musicPower.orElse(musicPower),
    name = stateUpdate.name.orElse(name),
    power = stateUpdate.power.orElse(power),
    rgb = stateUpdate.rgb.orElse(rgb),
    saturation = stateUpdate.saturation.orElse(saturation),
    temperature = stateUpdate.temperature.orElse(temperature),
    timerValue = stateUpdate.timerValue.orElse(timerValue),
    //
    bgBrightness = stateUpdate.bgBrightness.orElse(bgBrightness),
    bgColorMode = stateUpdate.bgColorMode.orElse(bgColorMode),
    bgFlowExpression = stateUpdate.bgFlowExpression.orElse(bgFlowExpression),
    bgFlowPower = stateUpdate.bgFlowPower.orElse(bgFlowPower),
    bgHue = stateUpdate.bgHue.orElse(bgHue),
    bgPower = stateUpdate.bgPower.orElse(bgPower),
    bgRgb = stateUpdate.bgRgb.orElse(bgRgb),
    bgSaturation = stateUpdate.bgSaturation.orElse(bgSaturation),
    bgTemperature = stateUpdate.bgTemperature.orElse(bgTemperature)
  )
  
  def update(deviceInfo: DeviceInfo): DeviceInfo = copy(
    location = deviceInfo.location.orElse(location),
    //
    id = deviceInfo.id.orElse(id),
    model = deviceInfo.model.orElse(model),
    firmwareVersion = deviceInfo.firmwareVersion.orElse(firmwareVersion),
    supportedCommands = deviceInfo.supportedCommands.orElse(supportedCommands),
    //
    brightness = deviceInfo.brightness.orElse(brightness),
    colorMode = deviceInfo.colorMode.orElse(colorMode),
    flowExpression = deviceInfo.flowExpression.orElse(flowExpression),
    flowPower = deviceInfo.flowPower.orElse(flowPower),
    hue = deviceInfo.hue.orElse(hue),
    musicPower = deviceInfo.musicPower.orElse(musicPower),
    name = deviceInfo.name.orElse(name),
    power = deviceInfo.power.orElse(power),
    rgb = deviceInfo.rgb.orElse(rgb),
    saturation = deviceInfo.saturation.orElse(saturation),
    temperature = deviceInfo.temperature.orElse(temperature),
    timerValue = deviceInfo.timerValue.orElse(timerValue),
    //
    bgBrightness = deviceInfo.bgBrightness.orElse(bgBrightness),
    bgColorMode = deviceInfo.bgColorMode.orElse(bgColorMode),
    bgFlowExpression = deviceInfo.bgFlowExpression.orElse(bgFlowExpression),
    bgFlowPower = deviceInfo.bgFlowPower.orElse(bgFlowPower),
    bgHue = deviceInfo.bgHue.orElse(bgHue),
    bgPower = deviceInfo.bgPower.orElse(bgPower),
    bgRgb = deviceInfo.bgRgb.orElse(bgRgb),
    bgSaturation = deviceInfo.bgSaturation.orElse(bgSaturation),
    bgTemperature = deviceInfo.bgTemperature.orElse(bgTemperature)
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
    //
    brightness = Some(Brightness(message.brightness)),
    colorMode = Some(ColorMode(message.colorMode)),
    hue = Some(Hue(message.hue)),
    name = Some(Name(message.name)),
    power = Some(Power(message.power)),
    rgb = Some(Rgb(message.rgb)),
    saturation = Some(Saturation(message.saturation)),
    temperature = Some(Temperature(message.temperature))
  )

  def empty: DeviceInfo = new DeviceInfo(
    InternalId.generate,
    None,
    isConnected = false,
    None, None, None, None
  )

  def apply(location: NetworkLocation): DeviceInfo = empty.copy(location = Some(location))

}