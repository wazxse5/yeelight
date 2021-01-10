package com.wazxse5.yeelight.valuetype
import com.wazxse5.yeelight.snapshot.SnapshotInfo
import com.wazxse5.yeelight.valuetype.DeviceModel.snapshotName
import com.wazxse5.yeelight.valuetype.ValueType.unknown
import play.api.libs.json.JsString

sealed trait DeviceModel extends ValueType[String] {

  override def companion: ValueTypeCompanion = DeviceModel

  override def strValue: String = value.getOrElse(unknown)

  override def snapshotInfo: SnapshotInfo = SnapshotInfo(snapshotName, JsString(strValue))
}

object DeviceModel extends ValueTypeCompanion {
  val snapshotName: String = "deviceModel"

  final case object Mono extends DeviceModel {
    override val value: Option[String] = Some("mono")
  }

  final case object Color extends DeviceModel {
    override val value: Option[String] = Some("color")
  }

  final case object Stripe extends DeviceModel {
    override val value: Option[String] = Some("stripe")
  }

  final case object Ceiling extends DeviceModel {
    override val value: Option[String] = Some("ceiling")
  }

  final case object DeskLamp extends DeviceModel {
    override val value: Option[String] = Some("desklamp")
  }

  final case object BsLamp extends DeviceModel {
    override val value: Option[String] = Some("bslamp")
  }

  def apply(name: String): DeviceModel = name match {
    case "mono" => Mono
    case "color" => Color
    case "stripe" => Stripe
    case "ceiling" => Ceiling
    case "desklamp" => DeskLamp
    case "bslamp" => BsLamp
  }

  val values: Set[String] = Set(Mono, Color, Stripe, Ceiling, DeskLamp, BsLamp).map(_.value.get)
}


