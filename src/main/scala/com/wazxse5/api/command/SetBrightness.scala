package com.wazxse5.api.command

import com.wazxse5.api.valuetype.{Brightness, Duration, Effect}

case class SetBrightness(p1: Brightness, p2: Effect, p3: Duration) extends YeelightCommand3 {
  override val name: String = "set_bright"

  override def p1Mandatory: Boolean = true

  override def p2Mandatory: Boolean = true

  override def p3Mandatory: Boolean = true

}

object SetBrightness {
  def apply(brightness: Brightness): SetBrightness = new SetBrightness(brightness, Effect.smooth, Duration(500))

  def apply(brightness: Int): SetBrightness = apply(Brightness(brightness))
}
