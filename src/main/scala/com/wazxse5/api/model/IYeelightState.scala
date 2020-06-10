package com.wazxse5.api.model

import java.time.LocalDateTime

import com.wazxse5.api.exception.UnsupportedPropertyException
import com.wazxse5.api.valuetype._

trait IYeelightState {
  def brightness: Option[Brightness] = throw new UnsupportedPropertyException
  def colorMode: Option[ColorMode] = throw new UnsupportedPropertyException
  def flowExpression: Option[FlowExpression] = throw new UnsupportedPropertyException
  def flowPower: Option[FlowPower] = throw new UnsupportedPropertyException
  def hue: Option[Hue] = throw new UnsupportedPropertyException
  def musicPower: Option[MusicPower] = throw new UnsupportedPropertyException
  def name: Option[Name] = throw new UnsupportedPropertyException
  def power: Option[Power] = throw new UnsupportedPropertyException
  def rgb: Option[Rgb] = throw new UnsupportedPropertyException
  def saturation: Option[Saturation] = throw new UnsupportedPropertyException
  def temperature: Option[Temperature] = throw new UnsupportedPropertyException
  def timerValue: Option[TimerValue] = throw new UnsupportedPropertyException

  def bgBrightness: Option[Brightness] = throw new UnsupportedPropertyException
  def bgColorMode: Option[ColorMode] = throw new UnsupportedPropertyException
  def bgFlowExpression: Option[FlowExpression] = throw new UnsupportedPropertyException
  def bgFlowPower: Option[FlowPower] = throw new UnsupportedPropertyException
  def bgHue: Option[Hue] = throw new UnsupportedPropertyException
  def bgPower: Option[Power] = throw new UnsupportedPropertyException
  def bgRgb: Option[Rgb] = throw new UnsupportedPropertyException
  def bgSaturation: Option[Saturation] = throw new UnsupportedPropertyException
  def bgTemperature: Option[Temperature] = throw new UnsupportedPropertyException
  // TODO: nl_br, active_mode

  def lastUpdate: Option[LocalDateTime] = throw new UnsupportedPropertyException
}