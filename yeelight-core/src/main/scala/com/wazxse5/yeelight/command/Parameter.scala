package com.wazxse5.yeelight.command

import com.wazxse5.yeelight.valuetype.ParamValueType

import scala.language.implicitConversions

sealed trait Parameter[A <: ParamValueType[_]] {
  def value: Option[A]
  def isEmpty: Boolean = value.isEmpty
  def isOptional: Boolean
  final def nonEmpty: Boolean = !isEmpty
  final def isMandatory: Boolean = !isOptional
}

case class MandatoryParameter[A <: ParamValueType[_]](getValue: A) extends Parameter[A] {
  override def value: Option[A] = Some(getValue)
  override val isOptional: Boolean = false
}

case class OptionalParameter[A <: ParamValueType[_]](value: Option[A]) extends Parameter[A] {
  override val isOptional: Boolean = true
}


object Parameter {
  def empty[A <: ParamValueType[_]]: OptionalParameter[A] = OptionalParameter(None)

  implicit def paramValueToOptParam[A <: ParamValueType[_]](param: A): OptionalParameter[A] = {
    OptionalParameter(Some(param))
  }
  implicit def paramValueToManParam[A <: ParamValueType[_]](param: A): MandatoryParameter[A] = {
    MandatoryParameter(param)
  }
}

