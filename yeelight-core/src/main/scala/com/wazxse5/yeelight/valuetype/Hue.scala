package com.wazxse5.yeelight.valuetype

import play.api.libs.json.{JsNumber, JsValue}

import scala.util.Try

case class Hue(value: Int) extends PropAndParamValueType[Int] {
  override def strValue: String = value.toString
  override def paramValue: JsValue = JsNumber(value)
  override def companion: PropAndParamCompanion = Hue
  override def isValid: Boolean = value >= 0 && value <= 359
}

object Hue extends PropAndParamCompanion {
  override val snapshotName = "hue"
  override val paramName = "hue"
  override val propFgName = "hue"
  override val propBgName = "bg_hue"

  def fromString(str: String): Option[Hue] = Try(Hue(str.toInt)).filter(_.isValid).toOption
  def fromJsValue(jsValue: JsValue): Option[Hue] = jsValue match {
    case JsNumber(value) => fromString(value.toString)
    case _ => None
  }
}