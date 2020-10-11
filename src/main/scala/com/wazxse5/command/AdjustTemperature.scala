package com.wazxse5.command

import com.wazxse5.valuetype.{Duration, Percent}

import scala.concurrent.duration._

case class AdjustTemperature(p1: Percent, p2: Duration) extends YeelightCommand2 {
  override val name: String = "adjust_ct"

  override def p1Mandatory: Boolean = true

  override def p2Mandatory: Boolean = true

}

object AdjustTemperature {
  def apply(p1: Percent): AdjustTemperature = new AdjustTemperature(p1, 500.millis)

  def apply(p1: Int, p2: Int): AdjustTemperature = apply(Percent(p1), p2.millis)

  def apply(p1: Int): AdjustTemperature = apply(Percent(p1))

  def apply: AdjustTemperature = apply(Percent(20))
}
