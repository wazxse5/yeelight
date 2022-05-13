package com.wazxse5.yeelight.core.command

import com.wazxse5.yeelight.core.valuetype.{Brightness, Duration, Effect}
import play.api.libs.json.JsValue

case class SetBrightness(
  brightness: Brightness,
  effect: Effect,
  duration: Duration
) extends YeelightCommand {
  override def name: String = SetBrightness.commandName
  
  override def args: Seq[JsValue] = Seq(brightness, effect, duration).map(_.paramValue)
}

object SetBrightness {
  val commandName: String = "set_bright"
  
  def apply(brightness: Brightness): SetBrightness = new SetBrightness(brightness, Effect.smooth, Duration(500))
  
  def apply(brightness: Int): SetBrightness = apply(Brightness(brightness))
}
