package com.wazxse5.api.command

import com.wazxse5.api.valuetype.{Parameter, TimerType}

case object GetTimer extends YeelightCommand {
  override def name: String = "cron_get"

  override def minParameters: Int = 1

  override def maxParameters: Int = 1

  override def params: Seq[Parameter[_]] = List(TimerType)
}
