package com.wazxse5.yeelight.valuetype

import play.api.libs.json.{JsNumber, JsValue}

import scala.util.Try

case class TimerValue(value: Int) extends PropAndParamValueType[Int] {
  override def strValue: String = value.toString
  override def paramValue: JsValue = JsNumber(value)
  override def companion: PropAndParamCompanion = TimerValue
  override def isValid: Boolean = 0 <= value && value <= 60
}

object TimerValue extends PropAndParamCompanion {
  override val snapshotName = "timerValue"
  override val paramName = "value"
  override val propFgName = "delayoff"

  def fromString(str: String): Option[TimerValue] = Try(TimerValue(str.toInt)).filter(_.isValid).toOption
  def fromJsValue(jsValue: JsValue): Option[TimerValue] = jsValue match {
    case JsNumber(value) => fromString(value.toString)
    case _ => None
  }
}
