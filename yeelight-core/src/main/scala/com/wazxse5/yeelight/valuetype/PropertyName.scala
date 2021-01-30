package com.wazxse5.yeelight.valuetype

import play.api.libs.json.{JsString, JsValue}

case class PropertyName(value: String) extends ParamValueType[String] {
  override def strValue: String = value
  override def paramValue: JsValue = JsString(strValue)
  override def companion: ParamCompanion = PropertyName
  override def isValid: Boolean = PropValueType.names.contains(value)
}

object PropertyName extends ParamCompanion {
  override val snapshotName = "propName"
  override val paramName = "prop"

  def fromString(str: String): Option[PropertyName] = Some(PropertyName(str)).filter(_.isValid)
  def fromJsValue(jsValue: JsValue): Option[PropertyName] = jsValue match {
    case JsString(value) => fromString(value)
    case _ => None
  }
}

