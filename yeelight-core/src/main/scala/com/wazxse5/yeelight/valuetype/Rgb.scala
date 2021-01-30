package com.wazxse5.yeelight.valuetype

import play.api.libs.json.{JsNumber, JsString, JsValue}

import scala.util.Try


case class Rgb(value: Int) extends PropAndParamValueType[Int] {
  override def strValue: String = value.toString
  override def paramValue: JsValue = JsNumber(value)
  override def companion: PropAndParamCompanion = Rgb
  override def isValid: Boolean = 0 <= value && value <= 16777215
}

object Rgb extends PropAndParamCompanion {
  override val snapshotName = "rgb"
  override val paramName = "rgb_value"
  override val propFgName = "rgb"
  override val propBgName = "bg_rgb"

  def apply(r: Int, g: Int, b: Int): Rgb = new Rgb(r * 255 * 255 + g * 255 + b)
  def apply(name: String): Rgb = predefinedColors(name)

  def red: Rgb = apply(255, 0, 0)
  def green: Rgb = apply(0, 255, 0)
  def blue: Rgb = apply(0, 0, 255)
  def yellow: Rgb = apply(255, 255, 0)

  def fromString(str: String): Option[Rgb] = Try(Rgb(str.toInt)).filter(_.isValid).toOption
  def fromJsValue(jsValue: JsValue): Option[Rgb] = jsValue match {
    case JsNumber(value) => fromString(value.toString)
    case JsString(value) => predefinedColors.get(value)
    case _ => None
  }

  val predefinedColors: Map[String, Rgb] = Map(
    "red" -> red,
    "green" -> green,
    "blue" -> blue,
    "yellow" -> yellow
  )
  val predefinedColorsNames: Set[String] = predefinedColors.keySet
}
