package com.wazxse5.valuetype

import play.api.libs.json.JsValue


case class Rgb(value: Option[Int], isBackground: Boolean) extends PropAndParam[Int] {
  override def companion: PropAndParamCompanion = Rgb

  override def strValue: String = ValueType.strValueOrUnknown(value)

  override def paramValue: JsValue = ValueType.jsValueOrUnknown(value)

  override def isValid: Boolean = value.exists(v => v >= 0 && v <= 16777215)
}

object Rgb extends PropAndParamCompanion {
  val snapshotName: String = "rgb"
  val paramName: String = "rgb_value"
  val propFgName: String = "rgb"
  override val propBgName: String = "bg_rgb"

  def apply(value: Int, isBackground: Boolean = false): Rgb = new Rgb(Some(value), isBackground)

  def apply(r: Int, g: Int, b: Int): Rgb = {
    val value = r * 255 * 255 + g * 255 + b
    new Rgb(Some(value), false)
  }

  def unknown(isBackground: Boolean): Rgb = new Rgb(None, isBackground)
  def unknown: Rgb = new Rgb(None, isBackground = false)

  def apply(name: String): Rgb = predefinedColors(name)

  def red: Rgb = apply(255, 0, 0)
  def green: Rgb = apply(0, 255, 0)
  def blue: Rgb = apply(0, 0, 255)
  def yellow: Rgb = apply(255, 255, 0)

  val predefinedColors: Map[String, Rgb] = Map(
    "red" -> red,
    "green" -> green,
    "blue" -> blue,
    "yellow" -> yellow
  )

  val predefinedColorsNames: Set[String] = predefinedColors.keySet
}
