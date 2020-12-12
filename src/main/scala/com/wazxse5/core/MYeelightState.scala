package com.wazxse5.core

import com.wazxse5.valuetype._
import org.joda.time.DateTime

class MYeelightState(
  internalId: InternalId,
  yeelightService: YeelightService
) extends YeelightState {
  override def brightness: Brightness = yeelightService.deviceInfo(internalId).brightness
  override def colorMode: ColorMode = yeelightService.deviceInfo(internalId).colorMode
  override def flowExpression: FlowExpression = yeelightService.deviceInfo(internalId).flowExpression
  override def flowPower: FlowPower = yeelightService.deviceInfo(internalId).flowPower
  override def hue: Hue = yeelightService.deviceInfo(internalId).hue
  override def musicPower: MusicPower = yeelightService.deviceInfo(internalId).musicPower
  override def name: Name = yeelightService.deviceInfo(internalId).name
  override def power: Power = yeelightService.deviceInfo(internalId).power
  override def rgb: Rgb = yeelightService.deviceInfo(internalId).rgb
  override def saturation: Saturation = yeelightService.deviceInfo(internalId).saturation
  override def temperature: Temperature = yeelightService.deviceInfo(internalId).temperature
  override def timerValue: TimerValue = yeelightService.deviceInfo(internalId).timerValue

  override def bgBrightness: Brightness = yeelightService.deviceInfo(internalId).bgBrightness
  override def bgColorMode: ColorMode = yeelightService.deviceInfo(internalId).bgColorMode
  override def bgFlowExpression: FlowExpression = yeelightService.deviceInfo(internalId).bgFlowExpression
  override def bgFlowPower: FlowPower = yeelightService.deviceInfo(internalId).bgFlowPower
  override def bgHue: Hue = yeelightService.deviceInfo(internalId).bgHue
  override def bgPower: Power = yeelightService.deviceInfo(internalId).bgPower
  override def bgRgb: Rgb = yeelightService.deviceInfo(internalId).bgRgb
  override def bgSaturation: Saturation = yeelightService.deviceInfo(internalId).bgSaturation
  override def bgTemperature: Temperature = yeelightService.deviceInfo(internalId).bgTemperature

  override def lastUpdate: DateTime = yeelightService.deviceInfo(internalId).lastUpdate
}

object MYeelightState {
  def apply(device: YeelightDevice): MYeelightState = new MYeelightState(device.internalId, device.service)
}