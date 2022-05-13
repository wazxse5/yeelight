package com.wazxse5.yeelight.core

import com.wazxse5.yeelight.core.message.{DiscoveryResponseMessage, NotificationMessage}
import com.wazxse5.yeelight.core.valuetype.{Brightness, Power, Temperature}
import play.api.libs.json.{JsString, JsValue}

case class YeelightStateChange(
  isConnected: Option[Boolean] = None,
  power: Option[Power] = None,
  brightness: Option[Brightness] = None,
  temperature: Option[Temperature] = None,
)

object YeelightStateChange {
  
  def fromDiscoveryResponse(message: DiscoveryResponseMessage): YeelightStateChange = {
    YeelightStateChange(
      power = Power.fromString(message.power),
      brightness = Brightness.fromString(message.brightness),
      temperature = Temperature.fromString(message.temperature)
    )
  }
  
  def fromNotification(message: NotificationMessage): YeelightStateChange = {
    def find[VT](name: String, fromJsValue: JsValue => Option[VT]): Option[VT] = {
      message.params.collectFirst {
        case (str, jsValue) if str == name => fromJsValue(jsValue)
      }.flatten
    }
    
    YeelightStateChange(
      power = find(Power.propFgName, Power.fromJsValue),
      brightness = find(Brightness.propFgName, Brightness.fromJsValue),
      temperature = find(Temperature.propFgName, Temperature.fromJsValue)
    )
  }
  
  def isConnected(newValue: Boolean): YeelightStateChange = {
    YeelightStateChange(isConnected = Some(newValue))
  }
  
}