package com.wazxse5.yeelight.valuetype

import play.api.libs.json.{JsNumber, JsValue}

import scala.util.Try

case class Temperature(value: Int) extends PropAndParamValueType[Int] {
  override def strValue: String = value.toString
  override def paramValue: JsValue = JsNumber(value)
  override def companion: PropAndParamCompanion = Temperature
  override def isValid: Boolean = 1700 <= value && value <= 6500
}

object Temperature extends PropAndParamCompanion {
  override val name = "temperature"
  override val paramName = "ct_value"
  override val propFgName = "ct"
  override val propBgName = "bg_ct"

  def fromString(str: String): Option[Temperature] = Try(Temperature(str.toInt)).filter(_.isValid).toOption
  def fromJsValue(jsValue: JsValue): Option[Temperature] = jsValue match {
    case JsNumber(value) => fromString(value.toString)
    case _ => None
  }
}