package com.wazxse5.yeelight.api.valuetype

case class Percent private(value: Int) extends IntParamValueType

object Percent extends IntValueTypeCompanion[Percent] {
  val paramName = "percentage"

  override def isValid(value: Int): Boolean = -100 <= value && value <= 100

  override protected def create(value: Int): Percent = new Percent(value)
}