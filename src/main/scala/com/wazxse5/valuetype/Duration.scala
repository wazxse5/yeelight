package com.wazxse5.valuetype

import play.api.libs.json.JsValue

import scala.concurrent.duration._
import scala.language.implicitConversions

case class Duration(value: Option[Int]) extends Parameter[Int] {
  override def companion: ParamCompanion = Duration

  override def strValue: String = ValueType.strValueOrUnknown(value)

  override def paramValue: JsValue = ValueType.jsValueOrUnknown(value)

  override def isValid: Boolean = value.exists(_ >= 30)
}

object Duration extends ParamCompanion {
  val snapshotName: String = "duration"
  val paramName: String = "duration"

  def apply(value: Int): Duration = new Duration(Some(value))

  implicit def finiteDurationToDuration(finiteDuration: FiniteDuration): Duration = {
    Duration(finiteDuration.toMillis.toInt)
  }
}
