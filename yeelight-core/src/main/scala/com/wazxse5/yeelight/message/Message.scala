package com.wazxse5.yeelight.message

import com.typesafe.scalalogging.StrictLogging
import com.wazxse5.yeelight.core.InternalId
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
  val deviceId: InternalId
}

trait Identifiable {
  val id: Int
}


object YeelightConnectedMessage {

  def fromJson(json: JsValue, deviceInternalId: InternalId): YeelightConnectedMessage = {
    val id = json \ "id"
    val result = json \ "result"
    val error = json \ "error"
    val method = (json \ "method").asOpt[String]

    if (id.isDefined && (result.isDefined || error.isDefined)) CommandResultMessage(json, deviceInternalId)
    else if (method.contains("props")) NotificationMessage(json, deviceInternalId)
    else throw new InvalidMessageException
  }

}