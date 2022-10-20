package com.wazxse5.yeelight.api.valuetype

import com.wazxse5.yeelight.api.exception.InvalidValueTypeValueException
import play.api.libs.json.{JsNumber, JsString, JsValue}

import scala.util.Try

trait ValueTypeCompanion[A, VT <: ValueType[A]] {
  def isValid(value: A): Boolean
  protected def create(value: A): VT

  def apply(value: A): VT = {
    if (isValid(value)) create(value)
    else throw InvalidValueTypeValueException(value.toString, name)
  }

  def fromString(str: String): VT

  def fromJsValue(jsValue: JsValue): VT

  def name: String = this.getClass.getSimpleName.stripSuffix("$")
}

trait IntValueTypeCompanion[VT <: ValueType[Int]] extends ValueTypeCompanion[Int, VT] {
  override def fromString(str: String): VT = {
    Try(str.toInt).map(apply).getOrElse(throw InvalidValueTypeValueException(str, name))
  }

  override def fromJsValue(jsValue: JsValue): VT = {
    jsValue match {
      case JsNumber(value) => apply(value.toInt)
      case other => throw InvalidValueTypeValueException(other, name)
    }
  }
}

trait StringValueTypeCompanion[VT <: ValueType[String]] extends ValueTypeCompanion[String, VT] {
  override def fromString(str: String): VT = apply(str)

  override def fromJsValue(jsValue: JsValue): VT = {
    jsValue match {
      case JsString(value) => apply(value)
      case other => throw InvalidValueTypeValueException(other, name)
    }
  }
}
