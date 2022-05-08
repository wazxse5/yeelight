package com.wazxse5.yeelight.core.command
import play.api.libs.json.JsValue

case object Toggle extends YeelightCommand {
  override val name: String = "toggle"
  override val args: Seq[JsValue] = Seq.empty
}
