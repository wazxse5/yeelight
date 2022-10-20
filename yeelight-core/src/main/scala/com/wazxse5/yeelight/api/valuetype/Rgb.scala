package com.wazxse5.yeelight.api.valuetype

case class Rgb private(value: Int) extends IntParamValueType {
  def red: Int = value >>> 16

  def green: Int = value << 16 >>> 24

  def blue: Int = value << 24 >>> 24
}

object Rgb extends IntValueTypeCompanion[Rgb] {
  val paramName = "rgb_value"
  val propFgName = "rgb"
  val propBgName = "bg_rgb"

  def apply(R: Int, G: Int, B: Int): Rgb = apply(R * 65536 + G * 256 + B)

  override def isValid(value: Int): Boolean = 1 <= value && value <= 16777215

  override protected def create(value: Int): Rgb = new Rgb(value)
}
