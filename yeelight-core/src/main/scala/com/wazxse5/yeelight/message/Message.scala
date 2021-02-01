package com.wazxse5.yeelight.message

import com.typesafe.scalalogging.StrictLogging
import com.wazxse5.yeelight.snapshot.Snapshotable
import play.api.libs.json.{JsValue, Json}

import scala.util.Try

trait Message extends StrictLogging

sealed trait YeelightMessage extends Message with Snapshotable {
  def text: String
}

trait YeelightUnconnectedMessage extends YeelightMessage

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