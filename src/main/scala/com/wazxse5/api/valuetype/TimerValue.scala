package com.wazxse5.api.valuetype

import play.api.libs.json.{JsNumber, JsValue}

case class TimerValue(value: Int) extends Property[Int] with Parameter[Int] {
  override val propFgName: String = TimerValue.propFgName

  override val propBgName: Option[String] = None

  override val paramName: String = TimerValue.paramName

  override def rawValue: String = value.toString

  override def isBackground: Boolean = false

  override def toJson: JsValue = JsNumber(value)

  override def isValid: Boolean = 0 < value && value <= 60
}

object TimerValue {
  val propFgName: String = "delayoff"
  val paramName: String = "value"
}
