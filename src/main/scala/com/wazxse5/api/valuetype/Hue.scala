package com.wazxse5.api.valuetype

final case class Hue(value: Int, isBackground: Boolean = false) extends Property[Int] with Parameter[Int] {
  override val propFgName: String = Hue.propFgName

  override val propBgName: Option[String] = Some(Hue.propBgName)

  override val paramName: String = Hue.paramName

  override def toJson: JsonValueType[_] = JsonIntValueType(value)

  override def isValid: Boolean = value >= 0 && value <= 359
}

object Hue {
  val propFgName: String = "hue"
  val propBgName: String = "bg_hue"
  val paramName: String = "hue"
}