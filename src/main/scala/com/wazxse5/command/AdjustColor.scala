package com.wazxse5.command

import com.wazxse5.valuetype.{Duration, Percent}

case class AdjustColor(p1: Percent, p2: Duration) extends YeelightCommand2 {
  def companion: YeelightCommandCompanion = AdjustColor

  override def p1Mandatory: Boolean = true
  override def p2Mandatory: Boolean = true
}

case object AdjustColor extends YeelightCommandCompanion {
  val commandName: String = "adjust_color"
  val snapshotName: String = "adjustColor"
}
