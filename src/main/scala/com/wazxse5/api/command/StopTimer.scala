package com.wazxse5.api.command

import com.wazxse5.api.valuetype.{Parameter, TimerType}

case object StopTimer extends YeelightCommand1 {
  override def name: String = "cron_add"

  override def p1: Parameter[_] = TimerType

  override def p1Mandatory: Boolean = true
}
