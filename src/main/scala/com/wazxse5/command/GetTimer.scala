package com.wazxse5.command

import com.wazxse5.valuetype.{Parameter, TimerType}

case object GetTimer extends YeelightCommand1 {
  override val name: String = "cron_get"

  override def p1: Parameter[_] = TimerType

  override def p1Mandatory: Boolean = true
}
