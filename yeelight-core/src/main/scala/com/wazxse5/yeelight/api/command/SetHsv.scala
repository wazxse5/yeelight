package com.wazxse5.yeelight.api.command

import com.wazxse5.yeelight.api.valuetype.{Duration, Effect, Hue, Saturation}
import play.api.libs.json.JsValue

case class SetHsv(
  hue: Hue,
  saturation: Saturation,
  effect: Effect,
  duration: Duration
) extends YeelightCommand {
  override def name: String = SetHsv.commandName
  
  override def args: Seq[JsValue] = Seq(hue, saturation, effect, duration).map(_.paramValue)
}

object SetHsv {
  val commandName: String = "set_hsv"
  
  def apply(hue: Hue, saturation: Saturation): SetHsv =
    new SetHsv(hue, saturation, Effect.smooth, new Duration(500))
}
