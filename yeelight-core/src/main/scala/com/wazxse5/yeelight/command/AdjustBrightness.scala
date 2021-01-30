package com.wazxse5.yeelight.command

import com.wazxse5.yeelight.valuetype.{Duration, Percent}

case class AdjustBrightness(
  p1: MandatoryParameter[Percent],
  p2: MandatoryParameter[Duration]
) extends YeelightCommand2 {
  override def companion: YeelightCommandCompanion = AdjustBrightness
}

object AdjustBrightness extends YeelightCommandCompanion {
  override val commandName: String = "adjust_bright"
  override val snapshotName: String = "adjustBrightness"

  def apply(p1: Percent): AdjustBrightness = new AdjustBrightness(p1, Duration(500))

  def apply(p1: Int, p2: Int): AdjustBrightness = apply(Percent(p1), Duration(p2))

  def apply(p1: Int): AdjustBrightness = apply(Percent(p1))

  def apply: AdjustBrightness = apply(Percent(20))
}
