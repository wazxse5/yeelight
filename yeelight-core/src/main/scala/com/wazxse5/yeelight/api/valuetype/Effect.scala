package com.wazxse5.yeelight.api.valuetype

case class Effect private(value: String) extends StringParamValueType

object Effect extends StringValueTypeCompanion[Effect] {
  val paramName = "effect"

  val sudden: Effect = new Effect("sudden")
  val smooth: Effect = new Effect("smooth")
  val typeByValue: Map[String, Effect] = Set(sudden, smooth).map(v => v.value -> v).toMap

  override def isValid(value: String): Boolean = typeByValue.contains(value)

  override protected def create(value: String): Effect = typeByValue(value)
}
