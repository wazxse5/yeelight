package com.wazxse5.api.command

import com.wazxse5.api.valuetype.{Duration, Parameter, Percent}

case class AdjustTemperature(p1: Percent, p2: Duration) extends YeelightCommand {
  override val name: String = "adjust_ct"

  override val minParameters: Int = 2

  override val maxParameters: Int = 2

  override def params: Seq[Parameter[_]] = List(p1, p2)
}
