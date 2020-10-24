package com.wazxse5.command

import com.wazxse5.valuetype.{Duration, Percent}

import scala.concurrent.duration._

case class AdjustTemperature(p1: Percent, p2: Duration) extends YeelightCommand2 {
  override def companion: YeelightCommandCompanion = AdjustTemperature

  override def p1Mandatory: Boolean = true
  override def p2Mandatory: Boolean = true
}

object AdjustTemperature extends YeelightCommandCompanion {
  override val commandName: String = "adjust_ct"
  override val snapshotName: String = "adjustTemperature"

  def apply(p1: Percent): AdjustTemperature = new AdjustTemperature(p1, 500.millis)

  def apply(p1: Int, p2: Int): AdjustTemperature = apply(Percent(p1), p2.millis)

  def apply(p1: Int): AdjustTemperature = apply(Percent(p1))

  def apply: AdjustTemperature = apply(Percent(20))
}
