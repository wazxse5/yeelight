package com.wazxse5.yeelight.command

import com.wazxse5.yeelight.valuetype.TimerType

case object StopTimer extends YeelightCommand1 with YeelightCommandCompanion {
  def companion: YeelightCommandCompanion = this
  override val commandName: String = "cron_del"
  override val snapshotName: String = "stopTimer"

  override def p1: MandatoryParameter[TimerType] = TimerType.default
}
