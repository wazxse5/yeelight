package com.wazxse5.api.model

import java.time.LocalDateTime

import com.wazxse5.api.InternalId
import com.wazxse5.api.valuetype._

class YeelightState(
  internalId: InternalId,
  deviceService: IYeelightService
) extends IYeelightState {
  override def brightness: Option[Brightness] = deviceService.deviceInfo(internalId).flatMap(_.brightness)
  override def colorMode: Option[ColorMode] = deviceService.deviceInfo(internalId).flatMap(_.colorMode)
  override def flowExpression: Option[FlowExpression] = deviceService.deviceInfo(internalId).flatMap(_.flowExpression)
  override def flowPower: Option[FlowPower] = deviceService.deviceInfo(internalId).flatMap(_.flowPower)
  override def hue: Option[Hue] = deviceService.deviceInfo(internalId).flatMap(_.hue)
  override def musicPower: Option[MusicPower] = deviceService.deviceInfo(internalId).flatMap(_.musicPower)
  override def name: Option[Name] = deviceService.deviceInfo(internalId).flatMap(_.name)
  override def power: Option[Power] = deviceService.deviceInfo(internalId).flatMap(_.power)
  override def rgb: Option[Rgb] = deviceService.deviceInfo(internalId).flatMap(_.rgb)
  override def saturation: Option[Saturation] = deviceService.deviceInfo(internalId).flatMap(_.saturation)
  override def temperature: Option[Temperature] = deviceService.deviceInfo(internalId).flatMap(_.temperature)
  override def timerValue: Option[TimerValue] = deviceService.deviceInfo(internalId).flatMap(_.timerValue)

  override def bgBrightness: Option[Brightness] = deviceService.deviceInfo(internalId).flatMap(_.bgBrightness)
  override def bgColorMode: Option[ColorMode] = deviceService.deviceInfo(internalId).flatMap(_.bgColorMode)
  override def bgFlowExpression: Option[FlowExpression] = deviceService.deviceInfo(internalId).flatMap(_.bgFlowExpression)
  override def bgFlowPower: Option[FlowPower] = deviceService.deviceInfo(internalId).flatMap(_.bgFlowPower)
  override def bgHue: Option[Hue] = deviceService.deviceInfo(internalId).flatMap(_.bgHue)
  override def bgPower: Option[Power] = deviceService.deviceInfo(internalId).flatMap(_.bgPower)
  override def bgRgb: Option[Rgb] = deviceService.deviceInfo(internalId).flatMap(_.bgRgb)
  override def bgSaturation: Option[Saturation] = deviceService.deviceInfo(internalId).flatMap(_.bgSaturation)
  override def bgTemperature: Option[Temperature] = deviceService.deviceInfo(internalId).flatMap(_.bgTemperature)

  override def lastUpdate: Option[LocalDateTime] = None // TODO:
}

object YeelightState {
  def apply(device: IYeelightDevice): YeelightState = new YeelightState(device.internalId, device.service)
}