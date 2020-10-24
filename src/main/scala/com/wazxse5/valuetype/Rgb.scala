package com.wazxse5.valuetype

import play.api.libs.json.{JsNumber, JsValue}


case class Rgb(value: Int, isBackground: Boolean = false) extends PropAndParam[Int] {
  override def companion: PropAndParamCompanion = Rgb

  override def strValue: String = value.toString

  override def paramValue: JsValue = JsNumber(value)

  override def isValid: Boolean = value >= 0 && value <= 16777215
}

object Rgb extends PropAndParamCompanion {
  val snapshotName: String = "rgb"
  val paramName: String = "rgb_value"
  val propFgName: String = "rgb"
  override val propBgName: String = "bg_rgb"

  def apply(r: Int, g: Int, b: Int): Rgb = {
    val value = r * 255 * 255 + g * 255 + b
    new Rgb(value)
  }

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
