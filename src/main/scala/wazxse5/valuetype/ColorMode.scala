package wazxse5.valuetype

sealed trait ColorMode extends Property[Int] {

  override val propName: String = ColorMode.propName

  override val propBgName: String = ColorMode.propBgName

  override def isBackground: Boolean = false
}

object ColorMode {
  val propName = "color_mode"
  val propBgName = "bg_lmode"

  final case object Color extends ColorMode {
    override val value: Int = 1
  }

  final case object Temperature extends ColorMode {
    override val value: Int = 2
  }

  final case object Hsv extends ColorMode {
    override val value: Int = 3
  }

  def apply(value: Int): ColorMode = value match {
    case Color.value => Color
    case Temperature.value => Temperature
    case Hsv.value => Hsv
  }
}

