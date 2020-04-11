package wazxse5.model

import java.time.LocalDateTime

import wazxse5.property.{Brightness, ColorMode, Hue, PowerMode, Rgb, Saturation, Temperature}

trait IYeelightState {
  def power: Option[PowerMode]

  def brightness: Option[Brightness]

  def temperature: Option[Temperature]

  def rgb: Option[Rgb]

  def hue: Option[Hue]

  def saturation: Option[Saturation]

  def colorMode: Option[ColorMode]

  def lastUpdate: Option[LocalDateTime]
}