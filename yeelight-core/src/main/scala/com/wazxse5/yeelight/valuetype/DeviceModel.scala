package com.wazxse5.yeelight.valuetype
import com.wazxse5.yeelight.snapshot.SnapshotInfo
import com.wazxse5.yeelight.valuetype.DeviceModel.name
import play.api.libs.json.{JsString, JsValue}

import scala.util.Try

sealed trait DeviceModel extends ValueType[String] {
  override def strValue: String = value
  override def companion: ValueTypeCompanion = DeviceModel
  override def snapshotInfo: SnapshotInfo = SnapshotInfo(name, JsString(strValue))
}

object DeviceModel extends ValueTypeCompanion {
  override val name = "deviceModel"

  def mono: DeviceModel = DeviceModelMono
  def color: DeviceModel = DeviceModelColor
  def stripe: DeviceModel = DeviceModelStripe
  def ceiling: DeviceModel = DeviceModelCeiling
  def deskLamp: DeviceModel = DeviceModelDeskLamp
  def bsLamp: DeviceModel = DeviceModelBsLamp

  val typeByValue: Map[String, DeviceModel] = Seq(mono, color, stripe, ceiling, deskLamp, bsLamp).map(v => v.value -> v).toMap
  val values: Seq[String] = typeByValue.keys.toSeq

  def fromString(str: String): Option[DeviceModel] = Try(typeByValue(str)).toOption
  def fromJsValue(jsValue: JsValue): Option[DeviceModel] = jsValue match {
    case JsString(value) => fromString(value)
    case _ => None
  }
}

case object DeviceModelMono extends DeviceModel {
  override val value = "mono"
}

case object DeviceModelColor extends DeviceModel {
  override val value = "color"
}

case object DeviceModelStripe extends DeviceModel {
  override val value = "stripe"
}

case object DeviceModelCeiling extends DeviceModel {
  override val value = "ceiling"
}

case object DeviceModelDeskLamp extends DeviceModel {
  override val value = "desklamp"
}

case object DeviceModelBsLamp extends DeviceModel {
  override val value = "bslamp"
}


