package com.wazxse5.command

import com.wazxse5.valuetype.{Brightness, Duration, Effect}

case class SetBrightness(p1: Brightness, p2: Effect, p3: Duration) extends YeelightCommand3 {
  def companion: YeelightCommandCompanion = SetBrightness

  override def p1Mandatory: Boolean = true
  override def p2Mandatory: Boolean = true
  override def p3Mandatory: Boolean = true
}

object SetBrightness extends YeelightCommandCompanion {
  override val commandName: String = "set_bright"
  override val snapshotName: String = "setBrightness"

  def apply(brightness: Brightness): SetBrightness = new SetBrightness(brightness, Effect.smooth, Duration(500))

  def apply(brightness: Int): SetBrightness = apply(Brightness(brightness))
}
