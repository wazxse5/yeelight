package com.wazxse5.message

import com.wazxse5.core.InternalId
import play.api.libs.json.{JsValue, Json}

case class CommandResultMessage private (
  id: Int,
  deviceId: InternalId,
  result: Option[Seq[String]] = None,
  errorCode: Option[Int] = None,
  errorMessage: Option[String] = None,
  json: JsValue,
  isValid: Boolean = true,
) extends InternalConnectedMessage with Identifiable {

  override def text: String = Json.stringify(json)

  def isOk: Boolean = result.contains("ok")

  def isError: Boolean = result.isEmpty && errorCode.nonEmpty && errorMessage.nonEmpty
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
