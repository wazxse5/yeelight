package com.wazxse5.yeelight.command

import com.wazxse5.yeelight.valuetype._

case class SetMusic(
  p1: MandatoryParameter[MusicPower],
  p2: MandatoryParameter[IpAddress],
  p3: MandatoryParameter[Port]
) extends YeelightCommand3 {
  def companion: YeelightCommandCompanion = SetMusic
}

case object SetMusic extends YeelightCommandCompanion {
  override val commandName: String = "set_music"
  override val snapshotName: String = "setMusic"
}