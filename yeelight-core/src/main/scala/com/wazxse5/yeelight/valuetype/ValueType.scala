package com.wazxse5.yeelight.valuetype

import com.wazxse5.yeelight.snapshot.{SnapshotInfo, Snapshotable}
import play.api.libs.json.{JsBoolean, JsNumber, JsString, JsValue}

trait ValueType[A] extends Snapshotable {
  def value: Option[A]
  def strValue: String
  def companion: ValueTypeCompanion

  def isKnown: Boolean = value.isDefined
  def isUnknown: Boolean = !isKnown
  def isProp: Boolean = false
  def isParam: Boolean = false
  final def isPropAndParam: Boolean = isProp && isParam
  final def isPropOrParam: Boolean = isProp || isParam
}

object ValueType {
  val unknown = "/unknown/"
  val undefined = "/undefined/"
  def strValueOrUnknown(i: Option[Int]): String = i.map(_.toString).getOrElse(unknown)
  def jsValueOrUnknown(i: Option[Int]): JsValue = i.map(JsNumber(_)).getOrElse(JsString(unknown))
}

trait Parameter[A] extends ValueType[A] {
  def paramName: String = companion.paramName
  def paramValue: JsValue
  def companion: ParamCompanion
  def snapshotInfo: SnapshotInfo = SnapshotInfo(companion.snapshotName, paramValue)
  def isValid: Boolean
  def isEmptyParam: Boolean = false
  override final def isParam: Boolean = true
}

trait Property[A] extends ValueType[A] {
  def propFgName: String = companion.propFgName
  def propBgName: String = companion.propBgName
  final def propName: String = if (isBackground) propBgName else propFgName
  def companion: PropCompanion
  def isBackground: Boolean
  override final def isProp: Boolean = true
}

trait PropAndParam[A] extends Property[A] with Parameter[A] {
  def companion: PropAndParamCompanion
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

  def applyFromJsValue(name: String, jsValue: JsValue): Property[_] = jsValue match {
    case JsString(string) => applyByName(name, string)
    case JsNumber(number) => applyByName(name, number.toString)
    case JsBoolean(bool) => applyByName(name, bool.toString)
  }

  val all = Seq(
    Brightness, ColorMode, FlowExpression, FlowPower, Hue, MusicPower,
    Name, Power, Rgb, Saturation, Temperature, TimerValue
  )
  val fgNames: Seq[String] = all.map(_.propFgName)
  val bgNames: Seq[String] = all.map(_.propBgName)
  val names: Seq[String] = fgNames ++ bgNames
}
