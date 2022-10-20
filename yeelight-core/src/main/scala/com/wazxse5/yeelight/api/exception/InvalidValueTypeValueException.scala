package com.wazxse5.yeelight.api.exception

import play.api.libs.json.{JsArray, JsBoolean, JsNull, JsNumber, JsObject, JsString, JsValue, Json}

case class InvalidValueTypeValueException(
  value: String,
  valueTypeName: String
) extends Exception {
  override def getMessage: String = s"Invalid value for $valueTypeName [$value]"
}

object InvalidValueTypeValueException {
  def apply(value: JsValue, valueTypeName: String): InvalidValueTypeValueException = {
    InvalidValueTypeValueException(value.getClass.getSimpleName.stripSuffix("$"), valueTypeName)
  }
}

