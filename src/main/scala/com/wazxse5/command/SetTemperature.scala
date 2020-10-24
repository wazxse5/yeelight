package com.wazxse5.command

import com.wazxse5.valuetype.{Duration, Effect, Temperature}

case class SetTemperature(p1: Temperature, p2: Effect, p3: Duration) extends YeelightCommand3 {
  def companion: YeelightCommandCompanion = SetTemperature

  override def p1Mandatory: Boolean = true
  override def p2Mandatory: Boolean = true
  override def p3Mandatory: Boolean = true
}

object SetTemperature extends YeelightCommandCompanion {
  override val commandName: String = "set_ct_abx"
  override val snapshotName: String = "setTemperature"

  def apply(temperature: Temperature): SetTemperature = new SetTemperature(temperature, Effect.smooth, Duration(500))

  def apply(temperature: Int): SetTemperature = apply(Temperature(temperature))
}
