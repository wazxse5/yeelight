package com.wazxse5.yeelight.command

import com.wazxse5.yeelight.valuetype._

case class SetMusic(p1: MusicPower, p2: IpAddress, p3: TcpPort) extends YeelightCommand3 {
  def companion: YeelightCommandCompanion = SetMusic

  override def p1Mandatory: Boolean = true
  override def p2Mandatory: Boolean = false
  override def p3Mandatory: Boolean = false
}

case object SetMusic extends YeelightCommandCompanion {
  override val commandName: String = "set_music"
  override val snapshotName: String = "setMusic"
}