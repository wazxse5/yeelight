package com.wazxse5.valuetype

sealed trait DeviceModel {
  def name: String
}

object DeviceModel {

  final case object Mono extends DeviceModel {
    override val name: String = "mono"
  }

  final case object Color extends DeviceModel {
    override val name: String = "color"
  }

  final case object Stripe extends DeviceModel {
    override val name: String = "stripe"
  }

  final case object Ceiling extends DeviceModel {
    override val name: String = "ceiling"
  }

  final case object DeskLamp extends DeviceModel {
    override val name: String = "desklamp"
  }

  final case object BsLamp extends DeviceModel {
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


