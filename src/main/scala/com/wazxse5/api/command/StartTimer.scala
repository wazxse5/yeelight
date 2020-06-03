package com.wazxse5.api.command

import com.wazxse5.api.valuetype.{Parameter, TimerType, TimerValue}

case class StartTimer(p1: TimerValue) extends YeelightCommand {
  override def name: String = "cron_add"

  override def minParameters: Int = 2

  override def maxParameters: Int = 2

  override def params: Seq[Parameter[_]] = List(TimerType, p1)
}
