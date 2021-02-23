package com.wazxse5.yeelight.valuetype

import play.api.libs.json.{JsNumber, JsValue}

import scala.util.Try

case class Saturation(value: Int) extends PropAndParamValueType[Int] {
  override def strValue: String = value.toString
  override def paramValue: JsValue = JsNumber(value)
  override def companion: PropAndParamCompanion = Saturation
  override def isValid: Boolean = 0 <= value && value <= 100
}

object Saturation extends PropAndParamCompanion {
  override val name = "saturation"
  override val paramName = "sat"
  override val propFgName = "sat"
  override val propBgName = "bg_sat"

  def fromString(str: String): Option[Saturation] = Try(Saturation(str.toInt)).filter(_.isValid).toOption
  def fromJsValue(jsValue: JsValue): Option[Saturation] = jsValue match {
    case JsNumber(value) => fromString(value.toString)
    case _ => None
  }
}
