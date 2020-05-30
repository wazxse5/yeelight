package com.wazxse5.api.valuetype

sealed trait ColorMode extends Property[Int] {

  override val propFgName: String = ColorMode.propFgName

  override val propBgName: Option[String] = Some(ColorMode.propBgName)

  override def rawValue: String = value.toString

}

object ColorMode {
  val propFgName = "color_mode"
  val propBgName = "bg_lmode"

  def apply(value: Int, isBackground: Boolean = false): ColorMode = value match {
    case 1 => RgbColorMode(isBackground)
    case 2 => TemperatureColorMode(isBackground)
    case 3 => HsvColorMode(isBackground)
  }

  def rgb: ColorMode = RgbColorMode(false)
  def rgb(isBackground: Boolean): ColorMode = RgbColorMode(isBackground)

  def temperature: ColorMode = TemperatureColorMode(false)
  def temperature(isBackground: Boolean): ColorMode = TemperatureColorMode(isBackground)

  def hsv: ColorMode = HsvColorMode(false)
  def hsv(isBackground: Boolean): ColorMode = HsvColorMode(isBackground)
}

final case class RgbColorMode(isBackground: Boolean) extends ColorMode {
  override val value: Int = 1
}

final case class TemperatureColorMode(isBackground: Boolean) extends ColorMode {
  override val value: Int = 2
}

final case class HsvColorMode(isBackground: Boolean) extends ColorMode {
  override val value: Int = 3
}

