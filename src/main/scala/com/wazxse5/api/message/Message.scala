package com.wazxse5.api.message

import com.typesafe.scalalogging.StrictLogging
import com.wazxse5.api.InternalId

sealed trait Message extends StrictLogging

trait ControlMessage extends Message // TODO: Może to nie powinno być łączone z normalnymi wiadomościami

sealed trait ApiMessage extends Message {
  def isValid: Boolean

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
  def fromJsonText(messageText: String, deviceInternalId: InternalId): ApiConnectedMessage = {
    // TODO: Może try-catch tu zrobić
    if (messageText.contains(""""method":"props"""")) NotificationMessage(messageText, deviceInternalId)
    else CommandResultMessage(messageText, deviceInternalId)
  }
}