package com.wazxse5.command

import com.wazxse5.valuetype.{Duration, Percent}

import scala.concurrent.duration._

case class AdjustBrightness(p1: Percent, p2: Duration) extends YeelightCommand2 {
  def companion: YeelightCommandCompanion = AdjustBrightness

  override def p1Mandatory: Boolean = true
  override def p2Mandatory: Boolean = true
}

object AdjustBrightness extends YeelightCommandCompanion {
  override val commandName: String = "adjust_bright"
  override val snapshotName: String = "adjustBrightness"

  def apply(p1: Percent): AdjustBrightness = new AdjustBrightness(p1, 500.millis)

  def apply(p1: Int, p2: Int): AdjustBrightness = apply(Percent(p1), p2.millis)

  def apply(p1: Int): AdjustBrightness = apply(Percent(p1))

  def apply: AdjustBrightness = apply(Percent(20))
}
