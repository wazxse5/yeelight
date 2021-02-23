package com.wazxse5.yeelight.valuetype

import play.api.libs.json.{JsNumber, JsValue}

import scala.util.Try

case class FlowCount(value: Int) extends ParamValueType[Int] {
  override def strValue: String = value.toString
  override def paramValue: JsValue = JsNumber(value)
  override def companion: ParamCompanion = FlowCount
  override def isValid: Boolean = value >= 0
}

object FlowCount extends ParamCompanion {
  override val name = "flowCount"
  override val paramName = "count"
  
  def infinite: FlowCount = FlowCount(0)

  def fromString(str: String): Option[FlowCount] = Try(FlowCount(str.toInt)).filter(_.isValid).toOption
  def fromJsValue(jsValue: JsValue): Option[FlowCount] = jsValue match {
    case JsNumber(value) => fromString(value.toString)
    case _ => None
  }
}
