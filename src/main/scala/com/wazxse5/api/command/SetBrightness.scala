package com.wazxse5.api.command

import com.wazxse5.api.valuetype.{Brightness, Duration, Effect, Parameter}

case class SetBrightness(p1: Brightness, p2: Effect, p3: Duration) extends YeelightCommand {
  override val name: String = "set_bright"

  override val minParameters: Int = 3

  override val maxParameters: Int = 3

  override def params: Seq[Parameter[_]] = List(p1, p2, p3)
}

object SetBrightness {
  def apply(brightness: Brightness): SetBrightness = new SetBrightness(brightness, Effect.smooth, Duration(500))

  def apply(brightness: Int): SetBrightness = apply(Brightness(brightness))
}
