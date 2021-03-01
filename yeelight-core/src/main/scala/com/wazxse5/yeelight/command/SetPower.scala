package com.wazxse5.yeelight.command

import com.wazxse5.yeelight.valuetype.{PowerMode, _}

case class SetPower(
  p1: MandatoryParameter[Power],
  p2: MandatoryParameter[Effect],
  p3: MandatoryParameter[Duration],
  p4: OptionalParameter[PowerMode]
) extends YeelightCommand4 {
  override def companion: YeelightCommandCompanion = SetPower
}

object SetPower extends YeelightCommandCompanion {
  override val commandName: String = "set_power"
  override val snapshotName: String = "setPower"

  def apply(power: Power, effect: Effect, duration: Duration, powerMode: PowerMode): SetPower = {
    new SetPower(power, effect, duration, powerMode)
  }

  def apply(power: Power, effect: Effect, duration: Duration): SetPower = {
    new SetPower(power, effect, duration, Parameter.empty)
  }

  def apply(power: Power): SetPower = apply(power, Effect.smooth, Duration(500))

}
