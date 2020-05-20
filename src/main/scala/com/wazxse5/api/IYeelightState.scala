package com.wazxse5.api

import java.time.LocalDateTime

import com.wazxse5.exception.UnsupportedProperty
import com.wazxse5.valuetype._

trait IYeelightState {
  def power: Option[Power] = throw new UnsupportedProperty

  def brightness: Option[Brightness] = throw new UnsupportedProperty

  def temperature: Option[Temperature] = throw new UnsupportedProperty

  def rgb: Option[Rgb] = throw new UnsupportedProperty

  def hue: Option[Hue] = throw new UnsupportedProperty

  def saturation: Option[Saturation] = throw new UnsupportedProperty

  def colorMode: Option[ColorMode] = throw new UnsupportedProperty

  // TODO: flowing, delayoff, flow_params, music_on, name

  def bgPower: Option[Power] = throw new UnsupportedProperty

  def bgBrightness: Option[Brightness] = throw new UnsupportedProperty

  def bgTemperature: Option[Temperature] = throw new UnsupportedProperty

  def bgRgb: Option[Rgb] = throw new UnsupportedProperty

  def bgHue: Option[Hue] = throw new UnsupportedProperty

  def bgSaturation: Option[Saturation] = throw new UnsupportedProperty

  def bgColorMode: Option[ColorMode] = throw new UnsupportedProperty

  // TODO: bg_flowing, bg_flow_params, nl_br, active_mode

  def lastUpdate: Option[LocalDateTime] = throw new UnsupportedProperty
}