package com.wazxse5.yeelight.core

import com.wazxse5.yeelight.connection.NetworkLocation
import com.wazxse5.yeelight.message.DeviceInfoMessage
import com.wazxse5.yeelight.valuetype._
import org.joda.time.DateTime

// TODO: być może trzeba przerobić na zwykłą klasę ze względu na lastUpdate
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
  brightness: Brightness = Brightness.unknown,
  colorMode: ColorMode = ColorMode.unknown,
  flowExpression: FlowExpression = FlowExpression.unknown,
  flowPower: FlowPower = FlowPower.unknown,
  hue: Hue = Hue.unknown,
  musicPower: MusicPower = MusicPower.unknown,
  name: Name = Name.unknown,
  power: Power = Power.unknown,
  rgb: Rgb = Rgb.unknown,
  saturation: Saturation = Saturation.unknown,
  temperature: Temperature = Temperature.unknown,
  timerValue: TimerValue = TimerValue.unknown,
  //
  bgBrightness: Brightness = Brightness.unknown(isBackground = true),
  bgColorMode: ColorMode = ColorMode.unknown(isBackground = true),
  bgFlowExpression: FlowExpression = FlowExpression.unknown(isBackground = true),
  bgFlowPower: FlowPower = FlowPower.unknown(isBackground = true),
  bgHue: Hue = Hue.unknown(isBackground = true),
  bgPower: Power = Power.unknown(isBackground = true),
  bgRgb: Rgb = Rgb.unknown(isBackground = true),
  bgSaturation: Saturation = Saturation.unknown(isBackground = true),
  bgTemperature: Temperature = Temperature.unknown(isBackground = true),
  //
  lastUpdate: DateTime = DateTime.now()
) {

  def withStateUpdate(stateUpdate: PropsUpdate): DeviceInfo = copy(
    brightness = stateUpdate.brightness.getOrElse(brightness),
    colorMode = stateUpdate.colorMode.getOrElse(colorMode),
    flowExpression = stateUpdate.flowExpression.getOrElse(flowExpression),
    flowPower = stateUpdate.flowPower.getOrElse(flowPower),
    hue = stateUpdate.hue.getOrElse(hue),
    musicPower = stateUpdate.musicPower.getOrElse(musicPower),
    name = stateUpdate.name.getOrElse(name),
    power = stateUpdate.power.getOrElse(power),
    rgb = stateUpdate.rgb.getOrElse(rgb),
    saturation = stateUpdate.saturation.getOrElse(saturation),
    temperature = stateUpdate.temperature.getOrElse(temperature),
    timerValue = stateUpdate.timerValue.getOrElse(timerValue),
    //
    bgBrightness = stateUpdate.bgBrightness.getOrElse(bgBrightness),
    bgColorMode = stateUpdate.bgColorMode.getOrElse(bgColorMode),
    bgFlowExpression = stateUpdate.bgFlowExpression.getOrElse(bgFlowExpression),
    bgFlowPower = stateUpdate.bgFlowPower.getOrElse(bgFlowPower),
    bgHue = stateUpdate.bgHue.getOrElse(bgHue),
    bgPower = stateUpdate.bgPower.getOrElse(bgPower),
    bgRgb = stateUpdate.bgRgb.getOrElse(bgRgb),
    bgSaturation = stateUpdate.bgSaturation.getOrElse(bgSaturation),
    bgTemperature = stateUpdate.bgTemperature.getOrElse(bgTemperature),
    //
    lastUpdate = DateTime.now()
  )
  
  def update(deviceInfo: DeviceInfo)(implicit allowUpdateToUnknownIfKnown: Boolean = false): DeviceInfo = copy(
    location = deviceInfo.location.orElse(location),
    //
    id = deviceInfo.id.orElse(id),
    model = deviceInfo.model.orElse(model),
    firmwareVersion = deviceInfo.firmwareVersion.orElse(firmwareVersion),
    supportedCommands = deviceInfo.supportedCommands.orElse(supportedCommands),
    //
    brightness = updateProperty(brightness, deviceInfo.brightness),
    colorMode = updateProperty(colorMode, deviceInfo.colorMode),
    flowExpression = updateProperty(flowExpression, deviceInfo.flowExpression),
    flowPower = updateProperty(flowPower, deviceInfo.flowPower),
    hue = updateProperty(hue, deviceInfo.hue),
    musicPower = updateProperty(musicPower, deviceInfo.musicPower),
    name = updateProperty(name, deviceInfo.name),
    power = updateProperty(power, deviceInfo.power),
    rgb = updateProperty(rgb, deviceInfo.rgb),
    saturation = updateProperty(saturation, deviceInfo.saturation),
    temperature = updateProperty(temperature, deviceInfo.temperature),
    timerValue = updateProperty(timerValue, deviceInfo.timerValue),
    //
    bgBrightness = updateProperty(bgBrightness, deviceInfo.bgBrightness),
    bgColorMode = updateProperty(bgColorMode, deviceInfo.bgColorMode),
    bgFlowExpression = updateProperty(bgFlowExpression, deviceInfo.bgFlowExpression),
    bgFlowPower = updateProperty(bgFlowPower, deviceInfo.bgFlowPower),
    bgHue = updateProperty(bgHue, deviceInfo.bgHue),
    bgPower = updateProperty(bgPower, deviceInfo.bgPower),
    bgRgb = updateProperty(bgRgb, deviceInfo.bgRgb),
    bgSaturation = updateProperty(bgSaturation, deviceInfo.bgSaturation),
    bgTemperature = updateProperty(bgTemperature, deviceInfo.bgTemperature),
    //
    lastUpdate = DateTime.now()
  )

  private def updateProperty[A <: ValueType[_]](oldValue: A, newValue: A)(implicit allowUpdateToUnknownIfKnown: Boolean): A = {
    if (oldValue.isKnown && newValue.isUnknown && !allowUpdateToUnknownIfKnown) oldValue
    else newValue
  }
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
    brightness = Brightness(message.brightness),
    colorMode = ColorMode(message.colorMode),
    hue = Hue(message.hue),
    name = Name(message.name),
    power = Power(message.power),
    rgb = Rgb(message.rgb),
    saturation = Saturation(message.saturation),
    temperature = Temperature(message.temperature)
  )

  def empty: DeviceInfo = new DeviceInfo(
    InternalId.generate,
    None,
    isConnected = false,
    None, None, None, None
  )

  def apply(location: NetworkLocation): DeviceInfo = empty.copy(location = Some(location))

}