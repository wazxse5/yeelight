package com.wazxse5.api.message

import com.typesafe.scalalogging.StrictLogging
import com.wazxse5.api.InternalId
import com.wazxse5.core.exception.InvalidMessageException
import play.api.libs.json.JsValue

sealed trait Message extends StrictLogging

trait ControlMessage extends Message // TODO: Może to nie powinno być łączone z normalnymi wiadomościami

sealed trait ApiMessage extends Message {
  def isValid: Boolean

  def json: JsValue

  def text: String
}

trait ApiUnconnectedMessage extends ApiMessage

trait ApiConnectedMessage extends ApiMessage {
  val deviceInternalId: InternalId
}

trait IdentifiableMessage extends ApiConnectedMessage {
  val id: Int
}


object ApiConnectedMessage {

  def fromJson(json: JsValue, deviceInternalId: InternalId): ApiConnectedMessage = {
    val id = json \ "id"
    val result = json \ "result"
    val error = json \ "error"
    val method = (json \ "method").asOpt[String]

    if (id.isDefined && (result.isDefined || error.isDefined)) CommandResultMessage(json, deviceInternalId)
    else if (method.contains("props")) NotificationMessage(json, deviceInternalId)
    else throw new InvalidMessageException
  }

}