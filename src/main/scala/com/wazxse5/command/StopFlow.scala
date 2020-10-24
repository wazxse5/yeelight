package com.wazxse5.command

case object StopFlow extends YeelightCommand0 with YeelightCommandCompanion {
  def companion: YeelightCommandCompanion = this
  override val commandName: String = "stop_cf"
  override val snapshotName: String = "stopFlow"
}
