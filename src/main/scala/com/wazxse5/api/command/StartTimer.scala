package com.wazxse5.api.command

import com.wazxse5.api.valuetype.{Parameter, TimerType, TimerValue}

case class StartTimer(p2: TimerValue) extends YeelightCommand2 {
  override def name: String = "cron_add"

  override def p1: Parameter[_] = TimerType

  override def p1Mandatory: Boolean = true

  override def p2Mandatory: Boolean = true

}
