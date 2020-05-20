package com.wazxse5.valuetype


case class Brightness(value: Int, isBackground: Boolean = false) extends Property[Int] with Parameter[Int] {

  override val propName: String = Brightness.propName

  override val propBgName: String = Brightness.propBgName

  override val paramName: String = Brightness.paramName

  override def toJson: JsonValueType[_] = JsonIntValueType(value)

  override def isValid: Boolean = value >= 1 && value <= 100

}

object Brightness {
  val propName: String = "bright"
  val propBgName: String = "bg_bright"
  val paramName: String = "rgb"
}
