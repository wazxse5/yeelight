package wazxse5.message

import com.typesafe.scalalogging.StrictLogging

trait Message extends StrictLogging

trait ControlMessage extends Message

trait ApiMessage extends Message {
  def isValid: Boolean

  def text: String
}
