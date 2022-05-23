package com.wazxse5.yeelight.api.command

import com.wazxse5.yeelight.api.valuetype.{Duration, Effect, Temperature}
import play.api.libs.json.JsValue

case class SetTemperature(
  temperature: Temperature,
  effect: Effect,
  duration: Duration
) extends YeelightCommand {
  override def name: String = SetTemperature.commandName
  
  override def args: Seq[JsValue] = Seq(temperature, effect, duration).map(_.paramValue)
}

object SetTemperature {
  val commandName: String = "set_ct_abx"
  
  def apply(temperature: Temperature): SetTemperature = new SetTemperature(temperature, Effect.smooth, new Duration(500))
  
  def apply(temperature: Int): SetTemperature = apply(new Temperature(temperature))
}

