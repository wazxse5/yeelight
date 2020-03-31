package wazxse5.command

import wazxse5.parameter.{PBrightness, PDuration, PEffect, Parameter}

case class SetBrightness(p1: PBrightness, p2: PEffect, p3: PDuration) extends YeelightCommand {
  override val name: String = "set_bright"
  override val minParameters: Int = 3
  override val maxParameters: Int = 3

  override def params: Seq[Parameter[_]] = List(p1, p2, p3)
}

