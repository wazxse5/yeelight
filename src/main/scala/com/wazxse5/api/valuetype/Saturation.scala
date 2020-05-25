package com.wazxse5.api.valuetype

final case class Saturation(value: Int, isBackground: Boolean = false) extends Property[Int] with Parameter[Int] {
  override val propFgName: String = Saturation.propFgName

  override val propBgName: Option[String] = Some(Saturation.propBgName)

  override val paramName: String = Saturation.paramName

  override def toJson: JsonValueType[_] = JsonIntValueType(value)

  override def isValid: Boolean = value >= 0 && value <= 100
}

object Saturation {
  val propFgName: String = "sat"
  val propBgName: String = "bg_sat"
  val paramName: String = "sat"

}