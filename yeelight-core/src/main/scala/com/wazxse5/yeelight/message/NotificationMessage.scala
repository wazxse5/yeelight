package com.wazxse5.yeelight.message

import com.wazxse5.yeelight.snapshot.SnapshotInfo
import play.api.libs.json.{JsValue, Json}

import scala.util.Try

case class NotificationMessage private(deviceId: String, json: JsValue) extends YeelightConnectedMessage {

  def params: Map[String, JsValue] = (json \ "params").as[Map[String, JsValue]]

  override def snapshotInfo: SnapshotInfo = SnapshotInfo(
    "notificationMessage", Json.obj(
      "deviceId" -> deviceId,
      "params" -> params
    )
  )

}

object NotificationMessage {

  def fromJson(json: JsValue, deviceId: String): Option[NotificationMessage] = {
    Try {
      val method = (json \ "method").as[String]
      new NotificationMessage(deviceId, json)
    }.toOption
  }

}
