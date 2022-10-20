package com.wazxse5.yeelight.core.message

import com.wazxse5.yeelight.api.valuetype.PropertyName
import com.wazxse5.yeelight.core.util.Logger
import play.api.libs.json.{JsValue, Json}

import scala.util.Try

case class NotificationMessage(
  deviceId: String,
  propertyValueMap: Map[PropertyName, JsValue]
) extends YeelightConnectedMessage {

  override def json: JsValue = Json.obj(
    "method" -> "props",
    "params" -> propertyValueMap.map { case (name, value) => name.value -> value }
  )

  def params: Map[String, JsValue] = (json \ "params").as[Map[String, JsValue]]
}

object NotificationMessage {
  
  def fromJson(json: JsValue, deviceId: String): Try[NotificationMessage] = {
    Try {
      val method = (json \ "method").as[String]
      val params = (json \ "params").as[Map[String, JsValue]]
      if (method == "props") {
        val propertyValueMap = params.map { case (propNameString, propValue) =>
          val propertyName = PropertyName.fromString(propNameString)
          propertyName -> propValue
        }
        new NotificationMessage(deviceId, propertyValueMap)
      } else {
        Logger.errorThrow(s"NotificationMessage for device=$deviceId method is not props ($method)")
      }
    }
  }
  
}