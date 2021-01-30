package com.wazxse5.yeelight.command

import com.wazxse5.yeelight.valuetype.{Duration, Percent}

case class AdjustTemperature(
  p1: MandatoryParameter[Percent],
  p2: MandatoryParameter[Duration]
) extends YeelightCommand2 {
  override def companion: YeelightCommandCompanion = AdjustTemperature
}

object AdjustTemperature extends YeelightCommandCompanion {
  override val commandName: String = "adjust_ct"
  override val snapshotName: String = "adjustTemperature"

  def apply(p1: Percent): AdjustTemperature = new AdjustTemperature(p1, Duration(500))

  def apply(p1: Int, p2: Int): AdjustTemperature = apply(Percent(p1), Duration(p2))

  def apply(p1: Int): AdjustTemperature = apply(Percent(p1))

  def apply: AdjustTemperature = apply(Percent(20))
}
