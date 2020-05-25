package com.wazxse5.api.valuetype

case class TimerValue(value: Int) extends Property[Int] with Parameter[Int] {
  override def propFgName: String = TimerValue.propFgName

  override def propBgName: Option[String] = None

  override def paramName: String = TimerValue.paramName

  override def isBackground: Boolean = false

  override def toJson: JsonValueType[_] = JsonIntValueType(value)

  override def isValid: Boolean = 0 < value && value <= 60
}

object TimerValue {
  val propFgName: String = "delayoff"
  val paramName: String = "value"
}
