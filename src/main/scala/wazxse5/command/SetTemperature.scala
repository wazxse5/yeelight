package wazxse5.command

import wazxse5.parameter.{PDuration, PEffect, PTemperature, Parameter}

case class SetTemperature(p1: PTemperature, p2: PEffect, p3: PDuration) extends YeelightCommand {
  override val name: String = "set_ct_abx"
  override val minParameters: Int = 3
  override val maxParameters: Int = 3

  override def params: Seq[Parameter[_]] = List(p1, p2, p3)
}
