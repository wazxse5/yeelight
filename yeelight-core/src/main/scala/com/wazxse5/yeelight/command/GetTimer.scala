package com.wazxse5.yeelight.command

import com.wazxse5.yeelight.valuetype.{Parameter, TimerType}

case object GetTimer extends YeelightCommand1 with YeelightCommandCompanion {
  def companion: YeelightCommandCompanion = this
  override val commandName: String = "cron_get"
  override val snapshotName: String = "getTimer"

  override def p1: Parameter[_] = TimerType
  override def p1Mandatory: Boolean = true
}
