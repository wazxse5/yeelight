package com.wazxse5.yeelight.command

import com.wazxse5.yeelight.valuetype.{Brightness, Duration, Effect}

case class SetBrightness(
  p1: MandatoryParameter[Brightness],
  p2: MandatoryParameter[Effect],
  p3: MandatoryParameter[Duration]
) extends YeelightCommand3 {
  def companion: YeelightCommandCompanion = SetBrightness
}

object SetBrightness extends YeelightCommandCompanion {
  override val commandName: String = "set_bright"
  override val snapshotName: String = "setBrightness"

  def apply(brightness: Brightness): SetBrightness = new SetBrightness(brightness, Effect.smooth, Duration(500))

  def apply(brightness: Int): SetBrightness = apply(Brightness(brightness))
}
