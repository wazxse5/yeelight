package com.wazxse5.yeelight.command

case object Toggle extends YeelightCommand0 with YeelightCommandCompanion {
  def companion: YeelightCommandCompanion = this
  override val commandName: String = "toggle"
  override val snapshotName: String = "toggle"
}
