package com.wazxse5.yeelight.api.valuetype

import play.api.libs.json.{JsString, JsValue}

sealed trait DeviceModel extends ValueType[String]

object DeviceModel {
  def mono: DeviceModel = DeviceModelMono
  def color: DeviceModel = DeviceModelColor
  def stripe: DeviceModel = DeviceModelStripe
  def ceiling: DeviceModel = DeviceModelCeiling
  def deskLamp: DeviceModel = DeviceModelDeskLamp
  def bsLamp: DeviceModel = DeviceModelBsLamp
  
  val typeByValue: Map[String, DeviceModel] = Seq(mono, color, stripe, ceiling, deskLamp, bsLamp).map(v => v.value -> v).toMap
  val values: Seq[String] = typeByValue.keys.toSeq
  
  def fromString(str: String): Option[DeviceModel] = typeByValue.get(str)
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