package com.wazxse5.yeelight.valuetype

import com.wazxse5.yeelight.valuetype.ValueType.unknown
import play.api.libs.json.{JsString, JsValue}

case class PropertyName(value: Option[String]) extends Parameter[String] {
  override def companion: ParamCompanion = PropertyName

  override def strValue: String = value.getOrElse(unknown)

  override def paramValue: JsValue = JsString(strValue)

  override def isValid: Boolean = value.exists(Property.names.contains)
}

object PropertyName extends ParamCompanion {
  val snapshotName: String = "propName"
  val paramName: String = "prop"

  def apply(value: String): PropertyName = PropertyName(Some(value))
}

