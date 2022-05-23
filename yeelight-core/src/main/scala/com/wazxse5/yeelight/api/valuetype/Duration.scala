package com.wazxse5.yeelight.api.valuetype

import play.api.libs.json.{JsNumber, JsValue}

class Duration(val value: Int) extends ParamValueType[Int] {
  override def paramValue: JsValue = JsNumber(value)
  override def isValid: Boolean = value >= 30
}

object Duration {
  val paramName = "duration"
}