package com.wazxse5.api.command

import com.wazxse5.api.valuetype.{Duration, Percent}

case class AdjustBrightness(p1: Percent, p2: Duration) extends YeelightCommand2 {
  override val name: String = "adjust_bright"

  override def p1Mandatory: Boolean = true

  override def p2Mandatory: Boolean = true

}
