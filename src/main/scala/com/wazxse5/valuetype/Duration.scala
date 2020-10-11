package com.wazxse5.valuetype

import play.api.libs.json.{JsNumber, JsValue}

import scala.concurrent.duration._

case class Duration(value: Int) extends Parameter[Int] {
  override val paramName: String = Duration.paramName

  override def rawValue: String = value.toString

  override def toJson: JsValue = JsNumber(value)

  override def isValid: Boolean = value >= 30
}

object Duration {
  val paramName = "duration"

  implicit def finiteDurationToDuration(finiteDuration: FiniteDuration): Duration = {
    Duration(finiteDuration.toMillis.toInt)
  }
}
