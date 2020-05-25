package com.wazxse5.api.command

import com.wazxse5.api.valuetype.{Duration, Effect, Parameter, Temperature}

case class SetTemperature(p1: Temperature, p2: Effect, p3: Duration) extends YeelightCommand {
  override val name: String = "set_ct_abx"

  override val minParameters: Int = 3

  override val maxParameters: Int = 3

  override def params: Seq[Parameter[_]] = List(p1, p2, p3)
}

object SetTemperature {
  def apply(temperature: Temperature): SetTemperature = new SetTemperature(temperature, Effect.smooth, Duration(500))

  def apply(temperature: Int): SetTemperature = apply(Temperature(temperature))
}
