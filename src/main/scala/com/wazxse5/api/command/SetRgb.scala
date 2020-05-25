package com.wazxse5.api.command

import com.wazxse5.api.valuetype.{Duration, Effect, Parameter, Rgb}

case class SetRgb(p1: Rgb, p2: Effect, p3: Duration) extends YeelightCommand {
  override val name: String = "set_rgb"

  override val minParameters: Int = 3

  override val maxParameters: Int = 3

  override def params: Seq[Parameter[_]] = List(p1, p2, p3)
}

object SetRgb {
  def apply(rgb: Rgb): SetRgb = new SetRgb(rgb, Effect.smooth, Duration(500))

  def apply(rgb: Int): SetRgb = apply(Rgb(rgb))
}


