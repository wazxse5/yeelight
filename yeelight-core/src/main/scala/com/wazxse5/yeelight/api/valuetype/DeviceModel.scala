package com.wazxse5.yeelight.api.valuetype

case class DeviceModel private(value: String) extends ValueType[String]

object DeviceModel extends StringValueTypeCompanion[DeviceModel] {
  val mono: DeviceModel = new DeviceModel("mono")
  val color: DeviceModel = new DeviceModel("color")
  val stripe: DeviceModel = new DeviceModel("stripe")
  val ceiling: DeviceModel = new DeviceModel("ceiling")
  val deskLamp: DeviceModel = new DeviceModel("desklamp")
  val bsLamp: DeviceModel = new DeviceModel("bslamp")
  val typeByValue: Map[String, DeviceModel] = Set(mono, color, stripe, ceiling, deskLamp, bsLamp).map(v => v.value -> v).toMap

  override def isValid(value: String): Boolean = typeByValue.contains(value)

  override protected def create(value: String): DeviceModel = typeByValue(value)
}
