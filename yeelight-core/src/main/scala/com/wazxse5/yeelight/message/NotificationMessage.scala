package com.wazxse5.yeelight.message

import com.wazxse5.yeelight.core.{InternalId, PropsUpdate}
import com.wazxse5.yeelight.snapshot.SnapshotInfo
import play.api.libs.json.{JsResultException, JsValue, Json}

case class NotificationMessage private(params: Map[String, JsValue], deviceId: InternalId, json: JsValue, isValid: Boolean = true) extends YeelightConnectedMessage {

  override def text: String = Json.stringify(json)

  def toStateUpdate: PropsUpdate = PropsUpdate(this)

  override def snapshotInfo: SnapshotInfo = SnapshotInfo(
    "notificationMessage", Json.obj(
      "deviceId" -> deviceId.snapshotInfo.value,
      "params" -> params
    )
  )

}

object NotificationMessage {

  def apply(json: JsValue, deviceInternalId: InternalId): NotificationMessage = {
    try {
      val method = (json \ "method").as[String]
      val params = (json \ "params").as[Map[String, JsValue]]
      new NotificationMessage(params, deviceInternalId, json)
    } catch {
      case _: JsResultException =>
        println(Json.prettyPrint(json))
        new NotificationMessage(Map.empty, deviceInternalId, json, false)
    }

  }

}
