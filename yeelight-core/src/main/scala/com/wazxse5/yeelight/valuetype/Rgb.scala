package com.wazxse5.yeelight.valuetype

import play.api.libs.json.{JsNumber, JsString, JsValue}

import scala.util.Try


case class Rgb(value: Int) extends PropAndParamValueType[Int] {
  override def strValue: String = value.toString
  override def paramValue: JsValue = JsNumber(value)
  override def companion: PropAndParamCompanion = Rgb
  override def isValid: Boolean = 1 <= value && value <= 16777215
}

object Rgb extends PropAndParamCompanion {
  override val name = "rgb"
  override val paramName = "rgb_value"
  override val propFgName = "rgb"
  override val propBgName = "bg_rgb"

  def apply(R: Int, G: Int, B: Int): Rgb = new Rgb(R * 65536 + G * 256 + B)
  def fromString(str: String): Option[Rgb] = Try(Rgb(str.toInt)).filter(_.isValid).orElse(Try(Rgb(predefinedColors(str)))).toOption
  def fromJsValue(jsValue: JsValue): Option[Rgb] = jsValue match {
    case JsNumber(value) => fromString(value.toString)
    case JsString(value) => predefinedColors.get(value).map(Rgb(_))
    case _ => None
  }

  val predefinedColors: Map[String, Int] = Map(
    0x00FFFF -> "aqua",
    0x0000FF -> "blue",
    0xFF00FF -> "fuchsia",
    0x808080 -> "gray",
    0x008000 -> "green",
    0x00FF00 -> "lime",
    0x800000 -> "maroon",
    0x000080 -> "navy",
    0x808000 -> "olive",
    0x800080 -> "purple",
    0xFF0000 -> "red",
    0xC0C0C0 -> "silver",
    0x008080 -> "teal",
    0xFFFFFF -> "white",
    0xFFFF00 -> "yellow",
  ).map(kv => kv._2 -> kv._1)
  val predefinedColorsNames: Set[String] = predefinedColors.keySet

  def aqua: Rgb = Rgb(predefinedColors("aqua"))
  def blue: Rgb = Rgb(predefinedColors("blue"))
  def fuchsia: Rgb = Rgb(predefinedColors("fuchsia"))
  def gray: Rgb = Rgb(predefinedColors("gray"))
  def green: Rgb = Rgb(predefinedColors("green"))
  def lime: Rgb = Rgb(predefinedColors("lime"))
  def maroon: Rgb = Rgb(predefinedColors("maroon"))
  def navy: Rgb = Rgb(predefinedColors("navy"))
  def olive: Rgb = Rgb(predefinedColors("olive"))
  def purple: Rgb = Rgb(predefinedColors("purple"))
  def red: Rgb = Rgb(predefinedColors("red"))
  def silver: Rgb = Rgb(predefinedColors("silver"))
  def teal: Rgb = Rgb(predefinedColors("teal"))
  def white: Rgb = Rgb(predefinedColors("white"))
  def yellow: Rgb = Rgb(predefinedColors("yellow"))
}
