package wazxse5.valuetype

sealed trait Power extends Property[String] with Parameter[String] {
  override val propName: String = Power.propName

  override val propBgName: String = Power.propBgName

  override val paramName: String = Power.paramName

  override def toJson: JsonValueType[_] = JsonStringValueType(value)

  override def isValid: Boolean = value == "on" || value == "off"
}

object Power {
  val propName: String = "power"
  val propBgName: String = "bg_power"
  val paramName: String = "power"

  final case class On(isBackground: Boolean = false) extends Power {
    override val value: String = "on"
  }

  final case class Off(isBackground: Boolean = false) extends Power {
    override val value: String = "off"
  }

  def apply(value: String, isBackground: Boolean = false): Power = value match {
    case "on" => On(isBackground)
    case "off" => Off(isBackground)
  }

  val values = Set("on", "off")
}

