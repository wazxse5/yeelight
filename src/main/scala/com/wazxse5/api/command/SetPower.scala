package com.wazxse5.api.command

import com.wazxse5.api.valuetype._

case class SetPower(p1: Power, p2: Effect, p3: Duration, p4Opt: Option[PowerMode]) extends YeelightCommand4 {
  override val name: String = "set_power"

  override def p4: Parameter[_] = p4Opt

  override def p1Mandatory: Boolean = true

  override def p2Mandatory: Boolean = true

  override def p3Mandatory: Boolean = true

  override def p4Mandatory: Boolean = false

}

object SetPower {
  def apply(power: Power): SetPower = new SetPower(power, Effect.smooth, Duration(500), None)

  def apply(power: String): SetPower = apply(Power(power))
}
