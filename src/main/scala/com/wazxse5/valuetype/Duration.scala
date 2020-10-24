package com.wazxse5.valuetype

import play.api.libs.json.{JsNumber, JsValue}

import scala.concurrent.duration._
import scala.language.implicitConversions

case class Duration(value: Int) extends Parameter[Int] {
  override def companion: ParamCompanion = Duration

  override def strValue: String = value.toString

  override def paramValue: JsValue = JsNumber(value)

  override def isValid: Boolean = value >= 30
}

object Duration extends ParamCompanion {
  val snapshotName: String = "duration"
  val paramName: String = "duration"

  implicit def finiteDurationToDuration(finiteDuration: FiniteDuration): Duration = {
    Duration(finiteDuration.toMillis.toInt)
  }
}
