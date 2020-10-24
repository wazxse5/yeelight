package com.wazxse5.valuetype

import play.api.libs.json.{JsString, JsValue}

case class Name(value: String) extends PropAndParam[String] {
  override def companion: PropAndParamCompanion = Name

  override def strValue: String = value

  override def isBackground: Boolean = false

  override def paramValue: JsValue = JsString(value)

  override def isValid: Boolean = value.nonEmpty
}

object Name extends PropAndParamCompanion {
  val snapshotName: String = "commandName"
  val paramName: String = "commandName"
  val propFgName: String = "commandName"
}
