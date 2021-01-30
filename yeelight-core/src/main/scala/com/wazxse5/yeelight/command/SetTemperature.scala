package com.wazxse5.yeelight.command

import com.wazxse5.yeelight.valuetype.{Duration, Effect, Temperature}

case class SetTemperature(
  p1: MandatoryParameter[Temperature],
  p2: MandatoryParameter[Effect],
  p3: MandatoryParameter[Duration]
) extends YeelightCommand3 {
  def companion: YeelightCommandCompanion = SetTemperature
}

object SetTemperature extends YeelightCommandCompanion {
  override val commandName: String = "set_ct_abx"
  override val snapshotName: String = "setTemperature"

  def apply(temperature: Temperature): SetTemperature = new SetTemperature(temperature, Effect.smooth, Duration(500))

  def apply(temperature: Int): SetTemperature = apply(Temperature(temperature))
}
