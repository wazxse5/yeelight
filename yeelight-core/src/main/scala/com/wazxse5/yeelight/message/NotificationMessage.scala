package com.wazxse5.yeelight.message

import com.wazxse5.yeelight.snapshot.SnapshotInfo
import play.api.libs.json.{JsResultException, JsValue, Json}

case class NotificationMessage private(
  params: Map[String, JsValue],
  deviceId: String,
  json: JsValue,
  isValid: Boolean = true
) extends YeelightConnectedMessage {

  override def text: String = Json.stringify(json)

  override def snapshotInfo: SnapshotInfo = SnapshotInfo(
    "notificationMessage", Json.obj(
      "deviceId" -> deviceId,
      "params" -> params
    )
  )

}

object NotificationMessage {

  def apply(json: JsValue, deviceId: String): NotificationMessage = {
    try {
      val method = (json \ "method").as[String]
      val params = (json \ "params").as[Map[String, JsValue]]
      new NotificationMessage(params, deviceId, json)
    } catch {
      case _: JsResultException =>
        println(Json.prettyPrint(json))
        new NotificationMessage(Map.empty, deviceId, json, false)
    }

  }

}
