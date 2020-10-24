package com.wazxse5.message

import com.wazxse5.core.InternalId
import com.wazxse5.snapshot.SnapshotInfo
import play.api.libs.json.{JsValue, Json}

case class CommandResultMessage private (
  id: Int,
  deviceId: InternalId,
  result: Option[Seq[String]] = None,
  errorCode: Option[Int] = None,
  errorMessage: Option[String] = None,
  json: JsValue,
  isValid: Boolean = true,
) extends YeelightConnectedMessage with Identifiable {

  override def text: String = Json.stringify(json)

  def isOk: Boolean = result.exists(_.contains("ok"))

  def isError: Boolean = result.isEmpty && errorCode.nonEmpty && errorMessage.nonEmpty

  override def snapshotInfo: SnapshotInfo = SnapshotInfo(
    "commandResultMessage", Json.obj(
      "id" -> id,
      "deviceId" -> deviceId.snapshotInfo.value,
      "result" -> result,
      "errorCode" -> errorCode,
      "errorMessage" -> errorMessage
    )
  )
}

object CommandResultMessage {

  def apply(json: JsValue, deviceInternalId: InternalId): CommandResultMessage = {
    val id = (json \ "id").as[Int]
    val errorCode = (json \ "error" \ "code").asOpt[Int]
    val errorMessage = (json \ "error" \ "command").asOpt[String]
    val result = (json \ "result").asOpt[Seq[String]]
    new CommandResultMessage(id, deviceInternalId, result, errorCode, errorMessage, json)
  }

}
