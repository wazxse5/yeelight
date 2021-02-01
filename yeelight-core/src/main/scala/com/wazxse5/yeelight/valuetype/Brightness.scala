package com.wazxse5.yeelight.valuetype

import play.api.libs.json.{JsNumber, JsValue}

import scala.util.Try

case class Brightness(value: Int) extends PropAndParamValueType[Int] {
  override def strValue: String = value.toString
  override def paramValue: JsValue = JsNumber(value)
  override def companion: PropAndParamCompanion = Brightness
  override def isValid: Boolean = value >= 1 && value <= 100
}

object Brightness extends PropAndParamCompanion {
  override val snapshotName = "brightness"
  override val paramName = "brightness"
  override val propFgName = "bright"
  override val propBgName = "bg_bright"
  val propNlName = "nl_br"

  def fromString(str: String): Option[Brightness] = Try(Brightness(str.toInt)).filter(_.isValid).toOption
  def fromJsValue(jsValue: JsValue): Option[Brightness] = jsValue match {
    case JsNumber(value) => fromString(value.toString)
    case _ => None
  }
}
