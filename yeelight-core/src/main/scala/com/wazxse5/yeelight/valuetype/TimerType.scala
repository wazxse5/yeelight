package com.wazxse5.yeelight.valuetype

import play.api.libs.json.{JsNumber, JsString, JsValue}

import scala.util.Try

trait TimerType extends ParamValueType[Int] {
  override def strValue: String = value.toString
  override def paramValue: JsValue = JsNumber(value)
  override def companion: ParamCompanion = TimerType
}

object TimerType extends ParamCompanion {
  override val name = "timerType"
  override val paramName = "type"

  def default: TimerType = TimerTypeDefault

  val typeByValue: Map[Int, TimerType] = Map(0 -> TimerTypeDefault)
  def fromString(str: String): Option[TimerType] = Try(typeByValue(str.toInt)).toOption
  def fromJsValue(jsValue: JsValue): Option[TimerType] = jsValue match {
    case JsNumber(n) => fromString(n.toString)
    case JsString(s) => fromString(s)
    case _ => None
  }
}

case object TimerTypeDefault extends TimerType {
  override val value = 0
}
