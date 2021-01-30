package com.wazxse5.yeelight.valuetype

import play.api.libs.json.{JsNumber, JsValue}

import scala.util.Try

case class Percent(value: Int) extends ParamValueType[Int] {
  override def strValue: String = value.toString
  override def paramValue: JsValue = JsNumber(value)
  override def companion: ParamCompanion = Percent
  override def isValid: Boolean = -100 <= value && value <= 100 
}

object Percent extends ParamCompanion {
  override val snapshotName = "percent"
  override val paramName = "percentage"

  def fromString(str: String): Option[Percent] = Try(Percent(str.toInt)).filter(_.isValid).toOption
  def fromJsValue(jsValue: JsValue): Option[Percent] = jsValue match {
    case JsNumber(value) => fromString(value.toString)
    case _ => None
  }
}
