package com.wazxse5.yeelight.api.valuetype

import play.api.libs.json.JsValue

trait ValueType[A] {
  def value: A
  def isValid: Boolean = true
}

trait ParamValueType[A] extends ValueType[A] {
  def paramValue: JsValue
}
