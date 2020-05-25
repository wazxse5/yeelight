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

  override def hue: Option[Hue] = deviceService.deviceInfo(internalId).flatMap(_.hue)

  override def power: Option[Power] = deviceService.deviceInfo(internalId).flatMap(_.power)

  override def rgb: Option[Rgb] = deviceService.deviceInfo(internalId).flatMap(_.rgb)

  override def saturation: Option[Saturation] = deviceService.deviceInfo(internalId).flatMap(_.saturation)

  override def temperature: Option[Temperature] = deviceService.deviceInfo(internalId).flatMap(_.temperature)

  override def lastUpdate: Option[LocalDateTime] = None // TODO:
}

object YeelightState {
  def apply(device: IYeelightDevice): YeelightState = new YeelightState(device.internalId, device.service)
}