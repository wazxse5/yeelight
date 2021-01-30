package com.wazxse5.yeelight.message

import com.wazxse5.yeelight.snapshot.SnapshotInfo
import play.api.libs.json.{JsValue, Json}

case class CommandResultMessage private (
  id: Int,
  deviceId: String,
  result: Option[Seq[String]] = None,
  errorCode: Option[Int] = None,
  errorMessage: Option[String] = None,
  json: JsValue,
  isValid: Boolean = true,
) extends YeelightConnectedMessage {

  override def text: String = Json.stringify(json)

  def isOk: Boolean = result.exists(_.contains("ok"))

  def isError: Boolean = result.isEmpty && errorCode.nonEmpty && errorMessage.nonEmpty

  override def snapshotInfo: SnapshotInfo = SnapshotInfo(
    "commandResultMessage", Json.obj(
      "id" -> id,
      "deviceId" -> deviceId,
      "result" -> result,
      "errorCode" -> errorCode,
      "errorMessage" -> errorMessage
    )
  )
}

object CommandResultMessage {

  def apply(json: JsValue, deviceId: String): CommandResultMessage = {
    val id = (json \ "id").as[Int]
    val errorCode = (json \ "error" \ "code").asOpt[Int]
    val errorMessage = (json \ "error" \ "cliCommand").asOpt[String]
    val result = (json \ "result").asOpt[Seq[String]]
    new CommandResultMessage(id, deviceId, result, errorCode, errorMessage, json)
  }

}
