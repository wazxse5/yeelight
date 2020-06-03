package com.wazxse5.api.command

import com.wazxse5.api.valuetype._

case class SetMusic(p1: MusicPower, p2: IpAddress, p3: TcpPort) extends YeelightCommand {
  override val name: String = "set_music"

  override val minParameters: Int = 1

  override val maxParameters: Int = 3

  override def params: Seq[Parameter[_]] = List(p1, p2, p3)
}