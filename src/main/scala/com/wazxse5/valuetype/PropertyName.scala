package com.wazxse5.valuetype

import play.api.libs.json.{JsString, JsValue}

case class PropertyName(value: String) extends Parameter[String] {
  override def companion: ParamCompanion = PropertyName

  override def strValue: String = value

  override def paramValue: JsValue = JsString(value)

  override def isValid: Boolean = Property.names.contains(value)
}

object PropertyName extends ParamCompanion {
  val snapshotName: String = "propName"
  val paramName: String = "prop"
}

