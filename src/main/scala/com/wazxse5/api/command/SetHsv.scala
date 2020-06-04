package com.wazxse5.api.command

import com.wazxse5.api.valuetype._

case class SetHsv(p1: Hue, p2: Saturation, p3: Effect, p4: Duration) extends YeelightCommand4 {
  override val name: String = "set_hsv"

  override def p1Mandatory: Boolean = true

  override def p2Mandatory: Boolean = true

  override def p3Mandatory: Boolean = true

  override def p4Mandatory: Boolean = true

}