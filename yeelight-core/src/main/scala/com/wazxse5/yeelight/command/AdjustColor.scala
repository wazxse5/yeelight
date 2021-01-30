package com.wazxse5.yeelight.command

import com.wazxse5.yeelight.valuetype.{Duration, Percent}

case class AdjustColor(
  p1: MandatoryParameter[Percent],
  p2: MandatoryParameter[Duration]
) extends YeelightCommand2 {
  def companion: YeelightCommandCompanion = AdjustColor
}

case object AdjustColor extends YeelightCommandCompanion {
  val commandName: String = "adjust_color"
  val snapshotName: String = "adjustColor"
}
