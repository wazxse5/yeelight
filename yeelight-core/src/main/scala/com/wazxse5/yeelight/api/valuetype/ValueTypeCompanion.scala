package com.wazxse5.yeelight.api.valuetype

import com.wazxse5.yeelight.api.exception.InvalidValueTypeValueException
import play.api.libs.json.{JsNumber, JsResult, JsString, JsValue, Reads, Writes}

import scala.util.Try

trait ValueTypeCompanion[A, VT <: ValueType[A]] {
  def isValid(value: A): Boolean
  protected def create(value: A): VT

  def apply(value: A): VT = {
    if (isValid(value)) create(value)
    else throw InvalidValueTypeValueException(value.toString, name)
  }

  def fromString(str: String): VT
  def fromStringOpt(str: String): Option[VT] = Try(fromString(str)).toOption

  def fromJsValue(jsValue: JsValue): VT
  def fromJsValueOpt(jsValue: JsValue): Option[VT] = Try(fromJsValue(jsValue)).toOption

  def name: String = this.getClass.getSimpleName.stripSuffix("$")

  implicit def writes: Writes[VT]
  implicit def reads: Reads[VT] = (json: JsValue) => JsResult.fromTry(Try(fromJsValue(json)))
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

  override implicit def writes: Writes[VT] = (o: VT) => JsNumber(o.value)
}

trait StringValueTypeCompanion[VT <: ValueType[String]] extends ValueTypeCompanion[String, VT] {
  override def fromString(str: String): VT = apply(str)

  override def fromJsValue(jsValue: JsValue): VT = {
    jsValue match {
      case JsString(value) => apply(value)
      case other => throw InvalidValueTypeValueException(other, name)
    }
  }

  override implicit def writes: Writes[VT] = (o: VT) => JsString(o.value)
}
