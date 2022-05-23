package com.wazxse5.yeelight.api.command

import com.wazxse5.yeelight.api.valuetype.{Duration, Percent}
import play.api.libs.json.JsValue

case class AdjustBrightness(
  percent: Percent,
  duration: Duration
) extends YeelightCommand {
  override def name: String = AdjustBrightness.commandName
  
  override def args: Seq[JsValue] = Seq(percent, duration).map(_.paramValue)
}

object AdjustBrightness {
  val commandName: String = "adjust_bright"
  
  def apply(percent: Percent): AdjustBrightness = new AdjustBrightness(percent, new Duration(500))
  
  def apply(percent: Int): AdjustBrightness = apply(new Percent(percent))
  
}
