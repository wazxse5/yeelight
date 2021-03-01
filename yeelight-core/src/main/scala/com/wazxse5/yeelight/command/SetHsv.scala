package com.wazxse5.yeelight.command

import com.wazxse5.yeelight.valuetype._

case class SetHsv(
  p1: MandatoryParameter[Hue],
  p2: MandatoryParameter[Saturation],
  p3: MandatoryParameter[Effect],
  p4: MandatoryParameter[Duration]
) extends YeelightCommand4 {
  override def companion: YeelightCommandCompanion = SetHsv
}

case object SetHsv extends YeelightCommandCompanion {
  override val commandName: String = "set_hsv"
  override val snapshotName: String = "setHsv"

  def apply(hue: Hue, saturation: Saturation): SetHsv = {
    SetHsv(hue, saturation, Effect.smooth, Duration(500))
  }
}