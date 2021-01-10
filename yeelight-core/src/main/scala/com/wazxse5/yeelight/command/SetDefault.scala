package com.wazxse5.yeelight.command

case object SetDefault extends YeelightCommand0 with YeelightCommandCompanion {
  def companion: YeelightCommandCompanion = this
  override val commandName: String = "set_default"
  override val snapshotName: String = "setDefault"
}
