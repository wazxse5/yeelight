package com.wazxse5.command

import com.wazxse5.valuetype.{Duration, Effect, Rgb}

case class SetRgb(p1: Rgb, p2: Effect, p3: Duration) extends YeelightCommand3 {
  override def companion: YeelightCommandCompanion = SetRgb

  override def p1Mandatory: Boolean = true
  override def p2Mandatory: Boolean = true
  override def p3Mandatory: Boolean = true
}

object SetRgb extends YeelightCommandCompanion {
  override val commandName: String = "set_rgb"
  override val snapshotName: String = "setRgb"

  def apply(rgb: Rgb): SetRgb = new SetRgb(rgb, Effect.smooth, Duration(500))

  def apply(rgb: Int): SetRgb = apply(Rgb(rgb))
}


