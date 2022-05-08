package com.wazxse5.yeelight.core

import com.wazxse5.yeelight.core.message.{DiscoveryResponseMessage, NotificationMessage}
import com.wazxse5.yeelight.core.valuetype.{Brightness, Temperature}
import play.api.libs.json.JsString

case class YeelightStateChange(
  isConnected: Option[Boolean] = None,
  brightness: Option[Brightness] = None,
  temperature: Option[Temperature] = None,
)

object YeelightStateChange {
  
  def fromDiscoveryResponse(message: DiscoveryResponseMessage): YeelightStateChange = {
    YeelightStateChange(
      brightness = Brightness.fromString(message.brightness),
      temperature = Temperature.fromString(message.temperature)
    )
  }
  
  def fromNotification(message: NotificationMessage): YeelightStateChange = {
    def find[VT](name: String, stringToValueType: String => Option[VT]): Option[VT] = {
      message.params.collectFirst {
        case (str, JsString(value)) if str == name => stringToValueType(value)
      }.flatten
    }
    
    YeelightStateChange(
      brightness = find(Brightness.propFgName, Brightness.fromString),
      temperature = find(Temperature.propFgName, Temperature.fromString)
    )
  }
  
  def isConnected(newValue: Boolean): YeelightStateChange = {
    YeelightStateChange(isConnected = Some(newValue))
  }
  
}