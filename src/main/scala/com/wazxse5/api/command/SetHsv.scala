package com.wazxse5.api.command

import com.wazxse5.api.valuetype._

case class SetHsv(p1: Hue, p2: Saturation, p3: Effect, p4: Duration) extends YeelightCommand {
  override val name: String = "set_hsv"

  override val minParameters: Int = 4

  override val maxParameters: Int = 4

  override def params: Seq[Parameter[_]] = List(p1, p2, p3)
}