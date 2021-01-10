package com.wazxse5.yeelight.valuetype

import com.wazxse5.yeelight.valuetype.ValueType.{undefined, unknown}
import play.api.libs.json.{JsString, JsValue}

case class Name(value: Option[String]) extends PropAndParam[String] {
  override def companion: PropAndParamCompanion = Name

  override def strValue: String = value match {
    case Some(v) if v.isEmpty => undefined
    case Some(v) => v
    case None => unknown
  }

  override def isBackground: Boolean = false

  override def paramValue: JsValue = JsString(strValue)

  override def isValid: Boolean = value.nonEmpty
}

object Name extends PropAndParamCompanion {
  val snapshotName: String = "commandName"
  val paramName: String = "commandName"
  val propFgName: String = "commandName"

  def apply(value: String): Name = new Name(Some(value))
  def unknown: Name = new Name(None)
}
