package com.wazxse5.yeelight.core.message

import play.api.libs.json.JsValue

import scala.util.Try

case class NotificationMessage(deviceId: String, json: JsValue) extends YeelightConnectedMessage {
  def params: Map[String, JsValue] = (json \ "params").as[Map[String, JsValue]]
}

object NotificationMessage {
  
  def fromJson(json: JsValue, deviceId: String): Option[NotificationMessage] = {
    Try {
      val method = (json \ "method").as[String]
      new NotificationMessage(deviceId, json)
    }.toOption
  }
  
}