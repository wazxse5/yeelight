package com.wazxse5.yeelight.api.valuetype

case class Brightness private(value: Int) extends IntParamValueType

object Brightness extends IntValueTypeCompanion[Brightness] {
  val paramName = "brightness"
  val propFgName = "bright"
  val propBgName = "bg_bright"
  val propNlName = "nl_br"

  override def isValid(value: Int): Boolean = value >= 1 && value <= 100

  override protected def create(value: Int): Brightness = new Brightness(value)
}