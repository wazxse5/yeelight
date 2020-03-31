package wazxse5.property

sealed trait ColorMode extends Property[Int] {
  def name: String = "color_mode"

  override def bgName: String = "bg_lmode"

  override def isBackground: Boolean = false
}

object ColorMode {

  case object Color extends ColorMode {
    override val value: Int = 1
  }

  case object Temperature extends ColorMode {
    override val value: Int = 2
  }

  case object Hsv extends ColorMode {
    override val value: Int = 3
  }

  case object Unknown extends ColorMode {
    override val value: Int = -1
  }

  def apply(value: Int): ColorMode = value match {
    case Color.value => Color
    case Temperature.value => Temperature
    case Hsv.value => Hsv
    case _ => Unknown
  }
}

