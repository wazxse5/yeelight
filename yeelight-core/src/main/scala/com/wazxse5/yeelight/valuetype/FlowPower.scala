package com.wazxse5.yeelight.valuetype
import com.wazxse5.yeelight.snapshot.SnapshotInfo
import play.api.libs.json.{JsNumber, JsValue}

import scala.util.Try

sealed trait FlowPower extends PropValueType[Int] {
  override def strValue: String = value.toString
  override def companion: PropCompanion = FlowPower
  override def snapshotInfo: SnapshotInfo = SnapshotInfo(companion.snapshotName, JsNumber(value))
}

object FlowPower extends PropCompanion {
  override val snapshotName = "flowPower"
  override val propFgName = "flowing"
  override val propBgName = "bg_flowing"
  
  def on: FlowPower = FlowOn
  def off: FlowPower = FlowOff

  val typeByValue: Map[Int, FlowPower] = Seq(on, off).map(v => v.value -> v).toMap
  val values: Seq[Int] = typeByValue.keys.toSeq

  def fromString(str: String): Option[FlowPower] = Try(typeByValue(str.toInt)).toOption
  def fromJsValue(jsValue: JsValue): Option[FlowPower] = jsValue match {
    case JsNumber(value) => fromString(value.toString)
    case _ => None
  }
}

case object FlowOn extends FlowPower {
  override val value = 1
}

case object FlowOff extends FlowPower {
  override val value = 0
}
