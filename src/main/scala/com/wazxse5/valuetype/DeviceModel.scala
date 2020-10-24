package com.wazxse5.valuetype
import com.wazxse5.snapshot.SnapshotInfo
import com.wazxse5.valuetype.DeviceModel.snapshotName
import play.api.libs.json.JsString

sealed trait DeviceModel extends ValueType[String] {

  override def companion: ValueTypeCompanion = DeviceModel

  override def strValue: String = value

  override def snapshotInfo: SnapshotInfo = SnapshotInfo(snapshotName, JsString(value))
}

object DeviceModel extends ValueTypeCompanion {
  val snapshotName: String = "deviceModel"

  final case object Mono extends DeviceModel {
    override val value: String = "mono"
  }

  final case object Color extends DeviceModel {
    override val value: String = "color"
  }

  final case object Stripe extends DeviceModel {
    override val value: String = "stripe"
  }

  final case object Ceiling extends DeviceModel {
    override val value: String = "ceiling"
  }

  final case object DeskLamp extends DeviceModel {
    override val value: String = "desklamp"
  }

  final case object BsLamp extends DeviceModel {
    override val value: String = "bslamp"
  }

  def apply(name: String): DeviceModel = name match {
    case Mono.value => Mono
    case Color.value => Color
    case Stripe.value => Stripe
    case Ceiling.value => Ceiling
    case DeskLamp.value => DeskLamp
    case BsLamp.value => BsLamp
  }

  val values: Set[String] = Set(Mono, Color, Stripe, Ceiling, DeskLamp, BsLamp).map(_.value)
}


