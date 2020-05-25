package com.wazxse5.api.model

import java.time.LocalDateTime

import com.wazxse5.api.valuetype._
import com.wazxse5.core.exception.UnsupportedProperty

trait IYeelightState {
  def brightness: Option[Brightness] = throw new UnsupportedProperty

  def colorMode: Option[ColorMode] = throw new UnsupportedProperty

  def flowExpression: Option[FlowExpression] = throw new UnsupportedProperty

  def flowPower: Option[FlowPower] = throw new UnsupportedProperty

  def hue: Option[Hue] = throw new UnsupportedProperty

  def musicPower: Option[MusicPower] = throw new UnsupportedProperty

  def name: Option[Name] = throw new UnsupportedProperty

  def power: Option[Power] = throw new UnsupportedProperty

  def rgb: Option[Rgb] = throw new UnsupportedProperty

  def saturation: Option[Saturation] = throw new UnsupportedProperty

  def temperature: Option[Temperature] = throw new UnsupportedProperty

  def timerValue: Option[TimerValue] = throw new UnsupportedProperty


  def bgBrightness: Option[Brightness] = throw new UnsupportedProperty

  def bgColorMode: Option[ColorMode] = throw new UnsupportedProperty

  def bgFlowExpression: Option[FlowExpression] = throw new UnsupportedProperty

  def bgFlowPower: Option[FlowPower] = throw new UnsupportedProperty

  def bgHue: Option[Hue] = throw new UnsupportedProperty

  def bgPower: Option[Power] = throw new UnsupportedProperty

  def bgRgb: Option[Rgb] = throw new UnsupportedProperty

  def bgSaturation: Option[Saturation] = throw new UnsupportedProperty

  def bgTemperature: Option[Temperature] = throw new UnsupportedProperty

  // TODO: nl_br, active_mode

  def lastUpdate: Option[LocalDateTime] = throw new UnsupportedProperty
}