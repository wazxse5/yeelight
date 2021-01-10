package com.wazxse5.yeelight.valuetype

import play.api.libs.json.JsValue

case class TimerValue(value: Option[Int]) extends PropAndParam[Int] {
  override def companion: PropAndParamCompanion = TimerValue

  override def strValue: String = ValueType.strValueOrUnknown(value)

  override def isBackground: Boolean = false

  override def paramValue: JsValue = ValueType.jsValueOrUnknown(value)

  override def isValid: Boolean = value.exists(v => 0 < v && v <= 60)
}

object TimerValue extends PropAndParamCompanion {
  val snapshotName: String = "timerValue"
  val paramName: String = "value"
  val propFgName: String = "delayoff"

  def unknown: TimerValue = TimerValue(None)
  def apply(value: Int): TimerValue = new TimerValue(Some(value))
}
