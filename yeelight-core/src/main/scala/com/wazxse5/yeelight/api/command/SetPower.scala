package com.wazxse5.yeelight.api.command

import com.wazxse5.yeelight.api.valuetype.{Duration, Effect, Power}
import play.api.libs.json.JsValue

case class SetPower(
  power: Power,
  effect: Effect,
  duration: Duration
) extends YeelightCommand {
  override def name: String = SetPower.commandName
  
  override def args: Seq[JsValue] = Seq(power, effect, duration).map(_.paramValue)
}

object SetPower {
  val commandName: String = "set_power"
  
  def on: SetPower = SetPower(Power.on, Effect.smooth, Duration(500))
  
  def off: SetPower = SetPower(Power.off, Effect.smooth, Duration(500))
}
