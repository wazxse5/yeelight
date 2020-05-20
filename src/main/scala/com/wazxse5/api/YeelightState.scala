package com.wazxse5.api

import java.time.LocalDateTime

import com.wazxse5.InternalId
import com.wazxse5.valuetype._

class YeelightState(
  internalId: InternalId,
  deviceService: IYeelightService
) extends IYeelightState {

  override def power: Option[Power] = deviceService.deviceInfo(internalId).flatMap(_.power)

  override def brightness: Option[Brightness] = deviceService.deviceInfo(internalId).flatMap(_.brightness)

  override def temperature: Option[Temperature] = deviceService.deviceInfo(internalId).flatMap(_.temperature)

  override def rgb: Option[Rgb] = deviceService.deviceInfo(internalId).flatMap(_.rgb)

  override def hue: Option[Hue] = deviceService.deviceInfo(internalId).flatMap(_.hue)

  override def saturation: Option[Saturation] = deviceService.deviceInfo(internalId).flatMap(_.saturation)

  override def colorMode: Option[ColorMode] = deviceService.deviceInfo(internalId).flatMap(_.colorMode)

  override def lastUpdate: Option[LocalDateTime] = None // TODO:
}

object YeelightState {
  def apply(device: IYeelightDevice): YeelightState = new YeelightState(device.internalId, device.service)
}