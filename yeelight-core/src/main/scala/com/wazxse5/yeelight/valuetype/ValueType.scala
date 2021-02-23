package com.wazxse5.yeelight.valuetype

import com.wazxse5.yeelight.snapshot.{SnapshotInfo, Snapshotable}
import play.api.libs.json.{JsNumber, JsString, JsValue}

trait ValueType[A] extends Snapshotable {
  def value: A
  def strValue: String

  def companion: ValueTypeCompanion
  def snapshotInfo: SnapshotInfo = SnapshotInfo(companion.name, JsString(strValue))

  def isValid: Boolean = true
  def isForProp: Boolean = false
  def isForParam: Boolean = false
  final def isForPropAndParam: Boolean = isForProp && isForParam
  final def isForPropOrParam: Boolean = isForProp || isForParam
}

object ValueType {
  val unknown = "/unknown/"
  val undefined = "/undefined/"
  def strValueOrUnknown(i: Option[Int]): String = i.map(_.toString).getOrElse(unknown)
  def jsValueOrUnknown(i: Option[Int]): JsValue = i.map(JsNumber(_)).getOrElse(JsString(unknown))
}

trait ParamValueType[A] extends ValueType[A] {
  def paramValue: JsValue
  def paramName: String = companion.paramName

  def companion: ParamCompanion
  override def snapshotInfo: SnapshotInfo = SnapshotInfo(companion.name, paramValue)

  override final def isForParam: Boolean = true
}

trait PropValueType[A] extends ValueType[A] {
  def propFgName: String = companion.propFgName
  def propBgName: String = companion.propBgName

  def companion: PropCompanion

  override final def isForProp: Boolean = true
}

trait PropAndParamValueType[A] extends PropValueType[A] with ParamValueType[A] {
  def companion: PropAndParamCompanion
}

object PropValueType {
  val all = Seq(
    Brightness, ColorMode, FlowExpression, FlowPower, Hue, MusicPower,
    Name, Power, Rgb, Saturation, Temperature, TimerValue
  )
  val fgNames: Seq[String] = all.map(_.propFgName)
  val bgNames: Seq[String] = all.filter(_.supportsBackground).map(_.propBgName)
  val names: Seq[String] = fgNames ++ bgNames
}
