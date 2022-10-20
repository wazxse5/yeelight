package com.wazxse5.yeelight.api.valuetype

import play.api.libs.json.{JsNumber, JsString, JsValue}


trait ValueType[A] {
  def value: A
}

trait ParamValueType[A] extends ValueType[A] {
  def paramValue: JsValue
}

trait IntParamValueType extends ParamValueType[Int] {
  override def paramValue: JsValue = JsNumber(value)
}

trait StringParamValueType extends ParamValueType[String] {
  override def paramValue: JsValue = JsString(value)
}
