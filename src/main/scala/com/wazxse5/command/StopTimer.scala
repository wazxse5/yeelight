package com.wazxse5.command

import com.wazxse5.valuetype.{Parameter, TimerType}

case object StopTimer extends YeelightCommand1 with YeelightCommandCompanion {
  def companion: YeelightCommandCompanion = this
  override val commandName: String = "cron_del"
  override val snapshotName: String = "stopTimer"

  override def p1: Parameter[_] = TimerType
  override def p1Mandatory: Boolean = true
}
