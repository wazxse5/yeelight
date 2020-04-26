package wazxse5.command

import wazxse5.valuetype.{Duration, Effect, Parameter, Power}

case class SetPower(p1: Power, p2: Effect, p3: Duration) extends YeelightCommand {
  override val name: String = "set_power"

  override val minParameters: Int = 3

  override val maxParameters: Int = 4 // TODO:

  override def params: Seq[Parameter[_]] = List(p1, p2, p3)
}

object SetPower {
  def apply(power: Power): SetPower = new SetPower(power, Effect.Smooth, Duration(500))

  def apply(power: String): SetPower = apply(Power(power))
}
