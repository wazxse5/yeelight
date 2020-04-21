package wazxse5.valuetype

sealed trait PowerMode extends Parameter[Int] {
  override val paramName: String = PowerMode.paramName

  override def toJson: JsonIntValueType = JsonIntValueType(value)

  override def isValid: Boolean = value >= 0 && value <= 5
}

object PowerMode {
  val paramName: String = "mode"

  final case object Normal extends PowerMode {
    override val value: Int = 0
  }

  final case object Ct extends PowerMode {
    override val value: Int = 1
  }

  final case object Rgb extends PowerMode {
    override val value: Int = 2
  }

  final case object Hsv extends PowerMode {
    override val value: Int = 3
  }

  final case object Flow extends PowerMode {
    override val value: Int = 4
  }

  final case object Night extends PowerMode {
    override val value: Int = 5
  }

}
