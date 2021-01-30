package com.wazxse5.yeelight.valuetype
import com.wazxse5.yeelight.snapshot.SnapshotInfo
import com.wazxse5.yeelight.valuetype.DeviceModel.snapshotName
import play.api.libs.json.{JsString, JsValue}

import scala.util.Try

sealed trait DeviceModel extends ValueType[String] {
  override def strValue: String = value
  override def companion: ValueTypeCompanion = DeviceModel
  override def snapshotInfo: SnapshotInfo = SnapshotInfo(snapshotName, JsString(strValue))
}

object DeviceModel extends ValueTypeCompanion {
  override val snapshotName = "deviceModel"

  def mono: DeviceModel = MonoDeviceModel
  def color: DeviceModel = ColorDeviceModel
  def stripe: DeviceModel = StripeDeviceModel
  def ceiling: DeviceModel = CeilingDeviceModel
  def deskLamp: DeviceModel = DeskLampDeviceModel
  def bsLamp: DeviceModel = BsLampDeviceModel

  val typeByValue: Map[String, DeviceModel] = Seq(mono, color, stripe, ceiling, deskLamp, bsLamp).map(v => v.value -> v).toMap
  val values: Seq[String] = typeByValue.keys.toSeq

  def fromString(str: String): Option[DeviceModel] = Try(typeByValue(str)).toOption
  def fromJsValue(jsValue: JsValue): Option[DeviceModel] = jsValue match {
    case JsString(value) => fromString(value)
    case _ => None
  }
}

case object MonoDeviceModel extends DeviceModel {
  override val value = "mono"
}

case object ColorDeviceModel extends DeviceModel {
  override val value = "color"
}

case object StripeDeviceModel extends DeviceModel {
  override val value = "stripe"
}

case object CeilingDeviceModel extends DeviceModel {
  override val value = "ceiling"
}

case object DeskLampDeviceModel extends DeviceModel {
  override val value = "desklamp"
}

case object BsLampDeviceModel extends DeviceModel {
  override val value = "bslamp"
}


