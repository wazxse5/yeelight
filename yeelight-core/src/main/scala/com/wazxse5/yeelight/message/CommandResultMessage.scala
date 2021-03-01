package com.wazxse5.yeelight.message

import com.wazxse5.yeelight.exception.InvalidMessageException
import com.wazxse5.yeelight.snapshot.SnapshotInfo
import play.api.libs.json.{JsArray, JsObject, JsValue, Json}

import scala.util.Try

case class CommandResultMessage(deviceId: String, json: JsValue) extends YeelightConnectedMessage {

  def id: Int = (json \ "id").as[Int]

  def result: ResultMessageResult = {
    val resultOpt = (json \ "result").asOpt[JsArray]
    val errorOpt = (json \ "error").asOpt[JsObject]
    (resultOpt, errorOpt) match {
      case (Some(result), None) => Result(result)
      case (None, Some(error)) => Error(error)
      case _ => throw new InvalidMessageException
    }
  }

  def resultSeq: Seq[String] = result match {
    case Result(value) => value.asOpt[Seq[String]].getOrElse(Seq.empty)
    case Error(_) => Seq.empty
  }

  override def snapshotInfo: SnapshotInfo = SnapshotInfo(
    "commandResultMessage", Json.obj(
      "deviceId" -> deviceId,
      "json" -> json
    )
  )
}

object CommandResultMessage {

  def fromJson(json: JsValue, deviceId: String): Option[CommandResultMessage] = {
    Try {
      new CommandResultMessage(deviceId, json)
    }.toOption
  }

  def apply(id: Int, deviceId: String, result: ResultMessageResult): CommandResultMessage = {
    val json = Json.obj("id" -> id, result.name -> result.value)
    CommandResultMessage(deviceId, json)
  }

}
