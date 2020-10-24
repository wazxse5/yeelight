package com.wazxse5.valuetype

import play.api.libs.json.{JsNumber, JsValue}

case object TimerType extends Parameter[Int] with ParamCompanion {
  override def companion: ParamCompanion = this

  override val snapshotName: String = "timerType"

  override val paramName: String = "type"

  override val value: Int = 0

  override def strValue: String = value.toString

  override def paramValue: JsValue = JsNumber(value)

  override def isValid: Boolean = value == 0
}
