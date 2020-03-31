package wazxse5.model

sealed trait DeviceModel {
  def name: String
}

object DeviceModel {

  case object Mono extends DeviceModel {
    override val name: String = "mono"
  }

  case object Color extends DeviceModel {
    override val name: String = "color"
  }

  case object Stripe extends DeviceModel {
    override val name: String = "stripe"
  }

  case object Ceiling extends DeviceModel {
    override val name: String = "ceiling"
  }

  case object DeskLamp extends DeviceModel {
    override val name: String = "desklamp"
  }

  case object BsLamp extends DeviceModel {
    override val name: String = "bslamp"
  }

  def apply(name: String): DeviceModel = name match {
    case Mono.name => Mono
    case Color.name => Color
    case Stripe.name => Stripe
    case Ceiling.name => Ceiling
    case DeskLamp.name => DeskLamp
    case BsLamp.name => BsLamp
  }

  val names = Set(Mono.name, Color.name, Stripe.name, Ceiling.name, DeskLamp.name, BsLamp.name)
}


