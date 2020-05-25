package com.wazxse5.api.valuetype


case class Brightness(value: Int, isBackground: Boolean = false) extends Property[Int] with Parameter[Int] {

  override val propFgName: String = Brightness.propFgName

  override val propBgName: Option[String] = Some(Brightness.propBgName)

  override val paramName: String = Brightness.paramName

  override def toJson: JsonValueType[_] = JsonIntValueType(value)

  override def isValid: Boolean = value >= 1 && value <= 100

}

object Brightness {
  val propFgName: String = "bright"
  val propBgName: String = "bg_bright"
  val paramName: String = "rgb"
}
