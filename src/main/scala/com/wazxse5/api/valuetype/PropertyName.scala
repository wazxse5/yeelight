package com.wazxse5.api.valuetype

import play.api.libs.json.{JsString, JsValue}

case class PropertyName(value: String) extends Parameter[String] {
  override val paramName: String = PropertyName.paramName

  override def rawValue: String = value

  override def toJson: JsValue = JsString(value)

  override def isValid: Boolean = Property.names.contains(value)
}

object PropertyName {
  val paramName: String = "prop"
}

