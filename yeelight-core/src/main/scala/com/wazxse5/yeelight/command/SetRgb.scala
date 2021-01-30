package com.wazxse5.yeelight.command

import com.wazxse5.yeelight.valuetype.{Duration, Effect, Rgb}

case class SetRgb(
  p1: MandatoryParameter[Rgb],
  p2: MandatoryParameter[Effect],
  p3: MandatoryParameter[Duration]
) extends YeelightCommand3 {
  override def companion: YeelightCommandCompanion = SetRgb
}

object SetRgb extends YeelightCommandCompanion {
  override val commandName: String = "set_rgb"
  override val snapshotName: String = "setRgb"

  def apply(rgb: Rgb): SetRgb = new SetRgb(rgb, Effect.smooth, Duration(500))

  def apply(rgb: Int): SetRgb = apply(Rgb(rgb))
}


