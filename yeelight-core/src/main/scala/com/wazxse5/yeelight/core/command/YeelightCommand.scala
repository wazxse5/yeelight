package com.wazxse5.yeelight.core.command

import play.api.libs.json.JsValue

trait YeelightCommand {
  def name: String
  
  def args: Seq[JsValue]
}


