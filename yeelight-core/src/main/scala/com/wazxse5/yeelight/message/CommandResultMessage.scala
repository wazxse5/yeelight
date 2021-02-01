package com.wazxse5.yeelight.message

import com.wazxse5.yeelight.snapshot.SnapshotInfo
import play.api.libs.json.{JsValue, Json}

import scala.util.Try

case class CommandResultMessage private (id: Int, deviceId: String, json: JsValue) extends YeelightConnectedMessage {

 val result: Option[Seq[String]] = (json \ "result").asOpt[Seq[String]]
 val errorCode: Option[Int] = (json \ "error" \ "code").asOpt[Int]
 val errorMessage: Option[String] = (json \ "error" \ "message").asOpt[String]
 val isOk: Boolean = result.exists(_.contains("ok"))
 val isError: Boolean = result.isEmpty && errorCode.nonEmpty && errorMessage.nonEmpty

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

  def fromJson(json: JsValue, deviceId: String): Option[CommandResultMessage] = {
    Try {
      val id = (json \ "id").as[Int]
      new CommandResultMessage(id, deviceId, json)
    }.toOption
  }

}
