package com.wazxse5.yeelight.core.command

import com.wazxse5.yeelight.core.valuetype.{Duration, Effect, Rgb}
import play.api.libs.json.JsValue

case class SetRgb(
  rgb: Rgb,
  effect: Effect,
  duration: Duration
) extends YeelightCommand {
  override def name: String = SetRgb.commandName
  
  override def args: Seq[JsValue] = Seq(rgb, effect, duration).map(_.paramValue)
}

object SetRgb {
  val commandName: String = "set_rgb"
  
  def apply(rgb: Rgb): SetRgb = new SetRgb(rgb, Effect.smooth, Duration(500))
}
