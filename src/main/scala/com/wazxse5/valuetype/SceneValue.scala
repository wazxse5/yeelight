package com.wazxse5.valuetype

import play.api.libs.json.{JsNumber, JsString, JsValue}

sealed trait SceneValue[A] extends Parameter[A] {
  override def companion: ParamCompanion = SceneValue
}

object SceneValue extends ParamCompanion {
  val snapshotName: String = "sceneValue"
  val paramName: String = "val"
}


case class StringSceneValue(value: String) extends SceneValue[String] {
  override def paramValue: JsValue = JsString(value)

  override def strValue: String = value

  override def isValid: Boolean = true
}

case class IntSceneValue(value: Int) extends SceneValue[Int] {
  override def paramValue: JsValue = JsNumber(value)

  override def strValue: String = value.toString

  override def isValid: Boolean = true
}


