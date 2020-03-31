package wazxse5.property

sealed trait PowerMode {
  def name: String
}

object PowerMode {

  case object On extends PowerMode {
    override val name: String = "on"
  }

  case object Off extends PowerMode {
    override val name: String = "off"
  }

  case object Unknown extends PowerMode {
    override val name: String = "unknown"
  }

  def apply(name: String): PowerMode = name match {
    case On.name => On
    case Off.name => Off
    case _ => Unknown
  }

  val names = Set(On.name, Off.name)
}

