package com.wazxse5.valuetype

sealed trait PropOrParam[A] {
  def value: A

  def isProp: Boolean = false

  def isParam: Boolean = false

  final def isPropAndParam: Boolean = isProp && isParam
}

trait Property[A] extends PropOrParam[A] {

  def propName: String

  def propBgName: String

  def isBackground: Boolean // TODO: czy to jest w sumie potrzebne?

  override final def isProp: Boolean = true
}

object Property {
  def applyByName(name: String, value: String): Property[_] = name match {
    case Brightness.propName => Brightness(value.toInt)
    case ColorMode.propName => ColorMode(value.toInt)
    case Hue.propName => Hue(value.toInt)
    case Power.propName => Power(value)
    case Rgb.propName => Rgb(value.toInt)
    case Saturation.propName => Saturation(value.toInt)
    case Temperature.propName => Temperature(value.toInt)
  }

}

trait Parameter[A] extends PropOrParam[A] {

  def paramName: String

  def toJson: JsonValueType[_]

  def isValid: Boolean

  override final def isParam: Boolean = true
}