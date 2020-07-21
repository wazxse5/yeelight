package com.wazxse5.api.valuetype

import play.api.libs.json.{JsNumber, JsValue}


final case class Rgb(value: Int, isBackground: Boolean = false) extends Property[Int] with Parameter[Int] {
  override val propFgName: String = Rgb.propFgName

  override val propBgName: Option[String] = Some(Rgb.propBgName)

  override val paramName: String = Rgb.paramName

  override def rawValue: String = value.toString

  override def toJson: JsValue = JsNumber(value)

  override def isValid: Boolean = value >= 0 && value <= 16777215
}

object Rgb {
  val propFgName: String = "rgb"
  val propBgName: String = "bg_rgb"
  val paramName: String = "rgb_value"

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
