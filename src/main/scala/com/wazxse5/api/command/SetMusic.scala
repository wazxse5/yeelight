package com.wazxse5.api.command

import com.wazxse5.api.valuetype._

case class SetMusic(p1: MusicPower, p2: IpAddress, p3: TcpPort) extends YeelightCommand3 {
  override val name: String = "set_music"

  override def p1Mandatory: Boolean = true

  override def p2Mandatory: Boolean = false

  override def p3Mandatory: Boolean = false

}