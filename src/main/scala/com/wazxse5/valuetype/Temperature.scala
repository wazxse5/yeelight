package com.wazxse5.valuetype

final case class Temperature(value: Int, isBackground: Boolean = false) extends Property[Int] with Parameter[Int] {
  override val propName: String = Temperature.propName

  override val propBgName: String = Temperature.propBgName

  override val paramName: String = Temperature.paramName

  override def toJson: JsonValueType[_] = JsonIntValueType(value)

  override def isValid: Boolean = value >= 1700 && value <= 6500
}

object Temperature {
  val propName: String = "ct"
  val propBgName: String = "bg_ct"
  val paramName: String = "ct_value"
}