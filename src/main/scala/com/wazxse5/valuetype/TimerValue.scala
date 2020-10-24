package com.wazxse5.valuetype

import play.api.libs.json.{JsNumber, JsValue}

case class TimerValue(value: Int) extends PropAndParam[Int] {
  override def companion: PropAndParamCompanion = TimerValue

  override def strValue: String = value.toString

  override def isBackground: Boolean = false

  override def paramValue: JsValue = JsNumber(value)

  override def isValid: Boolean = 0 < value && value <= 60
}

object TimerValue extends PropAndParamCompanion {
  val snapshotName: String = "timerValue"
  val paramName: String = "value"
  val propFgName: String = "delayoff"
}
