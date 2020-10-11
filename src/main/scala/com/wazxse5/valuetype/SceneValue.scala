package com.wazxse5.valuetype

import play.api.libs.json.{JsNumber, JsString, JsValue}

sealed trait SceneValue[A] extends Parameter[A] {
  override val paramName: String = SceneValue.paramName
}

object SceneValue {
  val paramName: String = "val"
}


case class StringSceneValue(value: String) extends SceneValue[String] {
  override def toJson: JsValue = JsString(value)

  override def rawValue: String = value

  override def isValid: Boolean = true
}

case class IntSceneValue(value: Int) extends SceneValue[Int] {
  override def toJson: JsValue = JsNumber(value)

  override def rawValue: String = value.toString

  override def isValid: Boolean = true
}


