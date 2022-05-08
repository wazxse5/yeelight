package com.wazxse5.yeelight.core.message

import play.api.libs.json.{JsValue, Json}

import scala.util.Try

sealed trait Message

trait ServiceMessage extends Message

trait YeelightMessage extends Message

trait YeelightConnectedMessage extends YeelightMessage {
  val deviceId: String
  def json: JsValue
  def text: String = Json.stringify(json)
}

object YeelightConnectedMessage {
  
  def fromJson(json: JsValue, deviceId: String): Option[YeelightConnectedMessage] = {
    Try {
      val id = json \ "id"
      val result = json \ "result"
      val error = json \ "error"
      val method = (json \ "method").asOpt[String]

      val message = if (id.isDefined && (result.isDefined || error.isDefined)) {
        CommandResultMessage.fromJson(json, deviceId)
      } else if (method.contains("props")) {
        NotificationMessage.fromJson(json, deviceId)
      } else {
        None
      }
      message.get
    }.toOption
  }
  
}