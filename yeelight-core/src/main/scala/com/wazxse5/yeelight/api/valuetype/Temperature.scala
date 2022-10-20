package com.wazxse5.yeelight.api.valuetype

case class Temperature(value: Int) extends IntParamValueType

object Temperature extends IntValueTypeCompanion[Temperature] {
  val paramName = "ct_value"
  val propFgName = "ct"
  val propBgName = "bg_ct"

  override def isValid(value: Int): Boolean = 1700 <= value && value <= 6500

  override protected def create(value: Int): Temperature = new Temperature(value)
}
