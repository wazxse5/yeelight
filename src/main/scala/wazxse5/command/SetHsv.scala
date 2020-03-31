package wazxse5.command

import wazxse5.parameter._

case class SetHsv(p1: PHue, p2: PSaturation, p3: PEffect, p4: PDuration) extends YeelightCommand {
  override val name: String = "set_hsv"
  override val minParameters: Int = 4
  override val maxParameters: Int = 4

  override def params: Seq[Parameter[_]] = List(p1, p2, p3)
}