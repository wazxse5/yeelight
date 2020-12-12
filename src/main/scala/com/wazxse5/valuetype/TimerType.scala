package com.wazxse5.valuetype

import play.api.libs.json.{JsNumber, JsValue}

case object TimerType extends Parameter[Int] with ParamCompanion {
  override def companion: ParamCompanion = this

  override val snapshotName: String = "timerType"

  override val paramName: String = "type"

  override val value: Option[Int] = Some(0)

  override def strValue: String = value.get.toString

  override def paramValue: JsValue = JsNumber(value.get)

  override def isValid: Boolean = value.contains(0)
}
