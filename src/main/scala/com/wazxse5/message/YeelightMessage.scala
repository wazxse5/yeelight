package com.wazxse5.message

import com.typesafe.scalalogging.StrictLogging
import com.wazxse5.core.InternalId
import com.wazxse5.exception.InvalidMessageException
import play.api.libs.json.JsValue

trait YeelightMessage extends StrictLogging

sealed trait InternalApiMessage extends YeelightMessage {
  def isValid: Boolean

  def json: JsValue

  def text: String
}

trait InternalUnconnectedMessage extends InternalApiMessage

trait InternalConnectedMessage extends InternalApiMessage {
  val deviceId: InternalId
}

trait Identifiable {
  val id: Int
}


object InternalConnectedMessage {

  def fromJson(json: JsValue, deviceInternalId: InternalId): InternalConnectedMessage = {
    val id = json \ "id"
    val result = json \ "result"
    val error = json \ "error"
    val method = (json \ "method").asOpt[String]

    if (id.isDefined && (result.isDefined || error.isDefined)) CommandResultMessage(json, deviceInternalId)
    else if (method.contains("props")) NotificationMessage(json, deviceInternalId)
    else throw new InvalidMessageException
  }

}