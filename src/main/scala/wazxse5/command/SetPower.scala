package wazxse5.command

import wazxse5.parameter.{PDuration, PEffect, PPower, Parameter}

case class SetPower(p1: PPower, p2: PEffect, p3: PDuration) extends YeelightCommand {
  override val name: String = "set_power"
  override val minParameters: Int = 3
  override val maxParameters: Int = 4 // TODO:

  override def params: Seq[Parameter[_]] = List(p1, p2, p3)
}

object SetPower {
  def apply(p1: PPower): SetPower = new SetPower(p1, PEffect.Smooth, PDuration(500))
}
