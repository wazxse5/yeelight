package com.wazxse5.yeelight.command

import com.wazxse5.yeelight.valuetype.Name

case class SetName(
  p1: MandatoryParameter[Name]
) extends YeelightCommand1 {
  def companion: YeelightCommandCompanion = SetName
}

object SetName extends YeelightCommandCompanion {
  val commandName: String = "setName"
  val snapshotName: String = "setName"
  def apply(name: String): SetName = new SetName(Name(name))
}
