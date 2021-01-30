package com.wazxse5.yeelight.message

import com.typesafe.scalalogging.StrictLogging
import com.wazxse5.yeelight.exception.InvalidMessageException
import com.wazxse5.yeelight.snapshot.Snapshotable
import play.api.libs.json.JsValue

trait Message extends StrictLogging

sealed trait YeelightMessage extends Message with Snapshotable {
  def isValid: Boolean
  def json: JsValue
  def text: String
}

trait YeelightUnconnectedMessage extends YeelightMessage

trait YeelightConnectedMessage extends YeelightMessage {
  val deviceId: String
}

object YeelightConnectedMessage {

  def fromJson(json: JsValue, deviceId: String): YeelightConnectedMessage = {
    val id = json \ "id"
    val result = json \ "result"
    val error = json \ "error"
    val method = (json \ "method").asOpt[String]

    if (id.isDefined && (result.isDefined || error.isDefined)) CommandResultMessage(json, deviceId)
    else if (method.contains("props")) NotificationMessage(json, deviceId)
    else throw new InvalidMessageException
  }

}