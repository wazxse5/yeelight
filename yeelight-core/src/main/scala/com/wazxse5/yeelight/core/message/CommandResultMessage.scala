package com.wazxse5.yeelight.core.message

import play.api.libs.json.{JsArray, JsObject, JsValue}

import scala.util.Try

case class CommandResultMessage(deviceId: String, json: JsValue) extends YeelightConnectedMessage {
  
  def id: Int = (json \ "id").as[Int]
  
  def result: CommandResultMessageStatus = {
    val resultOpt = (json \ "result").asOpt[JsArray]
    val errorOpt = (json \ "error").asOpt[JsObject]
    (resultOpt, errorOpt) match {
      case (Some(result), None) => Result(result)
      case (None, Some(error)) => Error(error)
      case _ => ???
    }
  }
  
  def resultSeq: Seq[String] = result match {
    case Result(value) => value.asOpt[Seq[String]].getOrElse(Seq.empty)
    case Error(_) => Seq.empty
  }
}

object CommandResultMessage {
  def fromJson(json: JsValue, deviceId: String): Option[CommandResultMessage] = {
    Try { new CommandResultMessage(deviceId, json) }.toOption
  }
}


sealed trait CommandResultMessageStatus {
  def value: JsValue
}

final case class Result(value: JsArray) extends CommandResultMessageStatus

final case class Error(value: JsObject) extends CommandResultMessageStatus {
  def errorCode: Int = (value \ "code").as[Int]
  def errorMessage: String = (value \ "message").as[String]
}