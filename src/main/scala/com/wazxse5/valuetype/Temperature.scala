package com.wazxse5.valuetype

import play.api.libs.json.{JsNumber, JsValue}

final case class Temperature(value: Int, isBackground: Boolean = false) extends Property[Int] with Parameter[Int] {
  override val propFgName: String = Temperature.propFgName

  override val propBgName: Option[String] = Some(Temperature.propBgName)

  override val paramName: String = Temperature.paramName

  override def rawValue: String = value.toString

  override def toJson: JsValue = JsNumber(value)

  override def isValid: Boolean = value >= 1700 && value <= 6500
}

object Temperature {
  val propFgName: String = "ct"
  val propBgName: String = "bg_ct"
  val paramName: String = "ct_value"
}