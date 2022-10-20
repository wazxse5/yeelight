package com.wazxse5.yeelight.api.valuetype

case class Duration private(value: Int) extends IntParamValueType

object Duration extends IntValueTypeCompanion[Duration] {
  val paramName = "duration"

  override def isValid(value: Int): Boolean = value >= 30

  override protected def create(value: Int): Duration = new Duration(value)
}