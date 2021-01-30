package com.wazxse5.yeelight.command

import com.wazxse5.yeelight.valuetype.{TimerType, TimerValue}

case class StartTimer(
  p2: MandatoryParameter[TimerValue]
) extends YeelightCommand2 {
  def companion: YeelightCommandCompanion = StartTimer
  override def p1: MandatoryParameter[TimerType] = TimerType.default
}

case object StartTimer extends YeelightCommandCompanion {
  override val commandName: String = "cron_add"
  override val snapshotName: String = "startTimer"
}
