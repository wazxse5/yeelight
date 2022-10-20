package com.wazxse5.yeelight.core.message

import com.wazxse5.yeelight.core.util.Logger
import play.api.libs.json._

import scala.util.Try

case class CommandResultMessage(
  id: Int,
  deviceId: String,
  result: CommandResultMessageResult
) extends YeelightConnectedMessage {

  override def json: JsValue = JsObject(Map(
    "id" -> JsNumber(id),
    result match {
      case ResultOk =>
        "result" -> JsArray(Seq(JsString("ok")))
      case ResultGetProps(propertyNames) =>
        "result" -> JsArray(propertyNames.map(JsString.apply))
      case ResultError(code, errorMessage) =>
        "error" -> JsObject(Map(
          "code" -> JsNumber(code),
          "message" -> JsString(errorMessage)
        ))
    }
  ))
}

object CommandResultMessage {
  def fromJson(json: JsValue, deviceId: String): Try[CommandResultMessage] = {
    Try {
      val id = (json \ "id").as[Int]
      val resultOpt = (json \ "result").asOpt[Seq[String]]
      val errorOpt = (json \ "error").asOpt[JsObject]
      val result = (resultOpt, errorOpt) match {
        case (Some(result), None) if result == Seq("ok") =>
          ResultOk
        case (Some(result), None) =>
          ResultGetProps(result)
        case (None, Some(error)) =>
          val errorCode = (error \ "code").as[Int]
          val errorMessage = (error \ "message").as[String]
          ResultError(errorCode, errorMessage)
        case _ => Logger.errorThrow("Invalid CommandResultMessage, missing result or error")
      }

      new CommandResultMessage(id, deviceId, result)
    }
  }
}


sealed trait CommandResultMessageResult {
  def isOk: Boolean
}

object ResultOk extends CommandResultMessageResult {
  override def isOk: Boolean = true
}

case class ResultGetProps(propertyValues: Seq[String]) extends CommandResultMessageResult {
  override def isOk: Boolean = true
}

case class ResultError(code: Int, errorMessage: String) extends CommandResultMessageResult {
  override def isOk: Boolean = false
}