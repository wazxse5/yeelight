package com.wazxse5.api.valuetype

import play.api.libs.json.{JsString, JsValue}

case class Name(value: String) extends Property[String] with Parameter[String] {
  override val propFgName: String = Name.propFgName

  override val propBgName: Option[String] = None

  override val paramName: String = Name.paramName

  override def rawValue: String = value

  override def isBackground: Boolean = false

  override def toJson: JsValue = JsString(value)

  override def isValid: Boolean = value.nonEmpty
}

object Name {
  val propFgName: String = "name"
  val paramName: String = "name"
}
