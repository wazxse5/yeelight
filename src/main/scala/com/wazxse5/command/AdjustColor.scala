package com.wazxse5.command

import com.wazxse5.valuetype.{Duration, Percent}

case class AdjustColor(p1: Percent, p2: Duration) extends YeelightCommand2 {
  override val name: String = "adjust_color"

  override def p1Mandatory: Boolean = true

  override def p2Mandatory: Boolean = true

}
