package com.wazxse5.yeelight.valuetype

import play.api.libs.json.{JsString, JsValue}

case class Name(value: String) extends PropAndParamValueType[String] {
  override def strValue: String = value
  override def paramValue: JsValue = JsString(strValue)
  override def companion: PropAndParamCompanion = Name
  override def isValid: Boolean = value.nonEmpty
}

object Name extends PropAndParamCompanion {
  override val snapshotName = "commandName"
  override val paramName = "commandName"
  override val propFgName = "commandName"

  def fromString(str: String): Option[Name] = Some(Name(str)).filter(_.isValid)
  def fromJsValue(jsValue: JsValue): Option[Name] = jsValue match {
    case JsString(value) => fromString(value)
    case _ => None
  }
}
