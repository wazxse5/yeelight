package wazxse5.model

import java.time.LocalDateTime

import wazxse5.property.{Brightness, ColorMode, Hue, PowerMode, Rgb, Saturation, Temperature}

trait IYeelightState {
  def power: PowerMode

  def brightness: Brightness

  def temperature: Temperature

  def rgb: Rgb

  def hue: Hue

  def saturation: Saturation

  def colorMode: ColorMode

  def lastUpdate: Option[LocalDateTime]
}