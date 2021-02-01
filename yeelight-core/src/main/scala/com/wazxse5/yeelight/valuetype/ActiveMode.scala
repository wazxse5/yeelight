package com.wazxse5.yeelight.valuetype

import com.wazxse5.yeelight.snapshot.SnapshotInfo
import play.api.libs.json.{JsNumber, JsValue}

import scala.util.Try

sealed trait ActiveMode extends PropValueType[Int] {
  override def companion: PropCompanion = ActiveMode
  override def snapshotInfo: SnapshotInfo = SnapshotInfo(companion.snapshotName, JsNumber(value))
}

object ActiveMode extends PropCompanion {
  override val snapshotName = "activeMode"
  override val propFgName = "active_mode"

  def daylight: ActiveMode = DaylightActiveMode
  def moonlight: ActiveMode = MoonlightActiveMode

  val typeByValue: Map[Int, ActiveMode] = Seq(daylight, moonlight).map(v => v.value -> v).toMap
  val values: Seq[Int] = typeByValue.keys.toSeq

  def fromString(str: String): Option[ActiveMode] = Try(typeByValue(str.toInt)).toOption
  def fromJsValue(jsValue: JsValue): Option[ActiveMode] = jsValue match {
    case JsNumber(value) => fromString(value.toString)
    case _ => None
  }
}

case object DaylightActiveMode extends ActiveMode {
  override val value = 0
  override val strValue = "daylight"
}

case object MoonlightActiveMode extends ActiveMode {
  override val value = 1
  override val strValue = "moonlight"
}

