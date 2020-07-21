package com.wazxse5.api.command

import com.wazxse5.api.valuetype.{Duration, Percent}

import scala.concurrent.duration._

case class AdjustBrightness(p1: Percent, p2: Duration) extends YeelightCommand2 {
  override val name: String = "adjust_bright"

  override def p1Mandatory: Boolean = true

  override def p2Mandatory: Boolean = true

}

object AdjustBrightness {
  def apply(p1: Percent): AdjustBrightness = new AdjustBrightness(p1, 500.millis)

  def apply(p1: Int, p2: Int): AdjustBrightness = apply(Percent(p1), p2.millis)

  def apply(p1: Int): AdjustBrightness = apply(Percent(p1))

  def apply: AdjustBrightness = apply(Percent(20))
}
