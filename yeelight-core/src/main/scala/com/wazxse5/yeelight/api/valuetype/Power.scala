package com.wazxse5.yeelight.api.valuetype

case class Power private(value: String) extends StringParamValueType

object Power extends StringValueTypeCompanion[Power] {
  val paramName = "power"
  val propFgName = "power"
  val propBgName = "bg_power"

  val on: Power = new Power("on")
  val off: Power = new Power("off")
  val typeByValue: Map[String, Power] = Seq(on, off).map(v => v.value -> v).toMap

  override def isValid(value: String): Boolean = typeByValue.contains(value)

  override protected def create(value: String): Power = typeByValue(value)
}
