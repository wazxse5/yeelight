package com.wazxse5.api.valuetype

sealed trait PropOrParam[A] {
  def value: A

  def isProp: Boolean = false

  def isParam: Boolean = false

  final def isPropAndParam: Boolean = isProp && isParam
}

trait Parameter[A] extends PropOrParam[A] {

  def paramName: String

  def toJson: JsonValueType[_]

  def isValid: Boolean

  override final def isParam: Boolean = true
}

trait Property[A] extends PropOrParam[A] {

  final def propName: String = {
    if (isBackground) propBgName.get
    else propFgName
  }

  def propFgName: String

  def propBgName: Option[String]

  def isBackground: Boolean // TODO: czy to jest w sumie potrzebne?

  override final def isProp: Boolean = true
}

object Property {
  def applyByName(name: String, value: String): Property[_] = name match {
    case Brightness.propFgName => Brightness(value.toInt)
    case ColorMode.propFgName => ColorMode(value.toInt)
    case FlowExpression.propFgName => FlowExpression(value)
    case FlowPower.propFgName => FlowPower(value.toInt)
    case Hue.propFgName => Hue(value.toInt)
    case MusicPower.propFgName => MusicPower(value.toInt)
    case Name.propFgName => Name(value)
    case Power.propFgName => Power(value)
    case Rgb.propFgName => Rgb(value.toInt)
    case Saturation.propFgName => Saturation(value.toInt)
    case Temperature.propFgName => Temperature(value.toInt)
    case TimerValue.propFgName => TimerValue(value.toInt)
      //
    case Brightness.propBgName => Brightness(value.toInt, isBackground = true)
    case ColorMode.propBgName => ColorMode(value.toInt, isBackground = true)
    case FlowExpression.propBgName => FlowExpression(value, isBackground = true)
    case FlowPower.propBgName => FlowPower(value.toInt, isBackground = true)
    case Hue.propBgName => Hue(value.toInt, isBackground = true)
    case Power.propBgName => Power(value, isBackground = true)
    case Rgb.propBgName => Rgb(value.toInt, isBackground = true)
    case Saturation.propBgName => Saturation(value.toInt, isBackground = true)
    case Temperature.propBgName => Temperature(value.toInt, isBackground = true)
  }

  val fgNames: Seq[String] = List(
    Brightness.propFgName,
    ColorMode.propFgName,
    FlowExpression.propFgName,
    FlowPower.propFgName,
    Hue.propFgName,
    MusicPower.propFgName,
    Name.propFgName,
    Power.propFgName,
    Rgb.propFgName,
    Saturation.propFgName,
    Temperature.propFgName,
    TimerValue.propFgName
  )
  
  val bgNames: Seq[String] = List(
    Brightness.propBgName,
    ColorMode.propBgName,
    FlowExpression.propBgName,
    FlowPower.propBgName,
    Hue.propBgName,
    Power.propBgName,
    Rgb.propBgName,
    Saturation.propBgName,
    Temperature.propBgName,
  )

  val names: Seq[String] = fgNames ++ bgNames

}
