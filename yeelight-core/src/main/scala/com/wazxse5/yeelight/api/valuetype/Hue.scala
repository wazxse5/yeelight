package com.wazxse5.yeelight.api.valuetype

case class Hue private(value: Int) extends IntParamValueType

object Hue extends IntValueTypeCompanion[Hue] {
  val paramName = "hue"
  val propFgName = "hue"
  val propBgName = "bg_hue"

  override def isValid(value: Int): Boolean = 0 <= value && value <= 359

  override protected def create(value: Int): Hue = new Hue(value)
}




