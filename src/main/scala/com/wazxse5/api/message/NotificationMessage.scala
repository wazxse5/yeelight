package com.wazxse5.api.message

import com.wazxse5.api.InternalId
import com.wazxse5.core.StateUpdate
import play.api.libs.json.{JsResultException, JsValue, Json}

case class NotificationMessage private(params: Map[String, JsValue], deviceId: InternalId, json: JsValue, isValid: Boolean = true) extends ApiConnectedMessage {

  override def text: String = Json.stringify(json)

  def toStateUpdate: StateUpdate = StateUpdate(this)

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
