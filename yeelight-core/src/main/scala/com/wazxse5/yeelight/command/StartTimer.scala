package com.wazxse5.yeelight.command

import com.wazxse5.yeelight.valuetype.{Parameter, TimerType, TimerValue}

case class StartTimer(p2: TimerValue) extends YeelightCommand2 {
  def companion: YeelightCommandCompanion = StartTimer

  override def p1: Parameter[_] = TimerType
  override def p1Mandatory: Boolean = true
  override def p2Mandatory: Boolean = true
}

case object StartTimer extends YeelightCommandCompanion {
  override val commandName: String = "cron_add"
  override val snapshotName: String = "startTimer"
}
