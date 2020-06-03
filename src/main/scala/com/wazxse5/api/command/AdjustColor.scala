package com.wazxse5.api.command

import com.wazxse5.api.valuetype.{Duration, Parameter, Percent}

case class AdjustColor(p1: Percent, p2: Duration) extends YeelightCommand {
  override val name: String = "adjust_color"

  override val minParameters: Int = 2

  override val maxParameters: Int = 2

  override def params: Seq[Parameter[_]] = List(p1, p2)
}
