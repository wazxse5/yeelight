package com.wazxse5.valuetype

import com.wazxse5.valuetype.ValueType.unknown
import play.api.libs.json.{JsString, JsValue}

sealed trait SceneValue[A] extends Parameter[A] {
  override def companion: ParamCompanion = SceneValue
}

object SceneValue extends ParamCompanion {
  val snapshotName: String = "sceneValue"
  val paramName: String = "val"
}


case class StringSceneValue(value: Option[String]) extends SceneValue[String] {
  override def strValue: String = value.getOrElse(unknown)

  override def paramValue: JsValue = JsString(strValue)

  override def isValid: Boolean = true
}

case class IntSceneValue(value: Option[Int]) extends SceneValue[Int] {
  override def strValue: String = ValueType.strValueOrUnknown(value)

  override def paramValue: JsValue = ValueType.jsValueOrUnknown(value)

  override def isValid: Boolean = true
}


