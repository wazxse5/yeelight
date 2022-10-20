package com.wazxse5.yeelight.api.valuetype

case class Saturation private(value: Int) extends IntParamValueType

object Saturation extends IntValueTypeCompanion[Saturation] {
  val paramName = "sat"
  val propFgName = "sat"
  val propBgName = "bg_sat"

  override def isValid(value: Int): Boolean = 0 <= value && value <= 100

  override protected def create(value: Int): Saturation = new Saturation(value)
}


