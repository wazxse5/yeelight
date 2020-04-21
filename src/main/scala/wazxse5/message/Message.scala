package wazxse5.message

import com.typesafe.scalalogging.StrictLogging

sealed trait Message extends StrictLogging

trait ControlMessage extends Message // TODO: Może to nie powinno być łączone z normalnymi wiadomościami

sealed trait ApiMessage extends Message {
  def isValid: Boolean

  def text: String
}

trait ApiUnconnectedMessage extends ApiMessage

trait ApiConnectedMessage extends ApiMessage

trait IdentifiableMessage extends ApiConnectedMessage {
  val id: Int
}


object ApiConnectedMessage {
  def fromMessageText(messageText: String): ApiConnectedMessage = {
    // TODO: Może try-catch tu zrobić
    if (messageText.contains(""""method":"props"""")) NotificationMessage(messageText)
    else CommandResultMessage(messageText)
  }
}