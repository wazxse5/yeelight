package wazxse5.model

import java.time.LocalDateTime

import wazxse5.valuetype._

trait IYeelightState {
  def power: Option[Power]

  def brightness: Option[Brightness]

  def temperature: Option[Temperature]

  def rgb: Option[Rgb]

  def hue: Option[Hue]

  def saturation: Option[Saturation]

  def colorMode: Option[ColorMode]

  def lastUpdate: Option[LocalDateTime]
}