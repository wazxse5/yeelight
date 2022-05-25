package com.wazxse5.yeelight.core

import com.wazxse5.yeelight.api.command.{SetBrightness, SetPower, SetTemperature, YeelightCommand}
import com.wazxse5.yeelight.api.valuetype.{Brightness, Power, Temperature}
import com.wazxse5.yeelight.core.message.{CommandResultMessage, DiscoveryResponseMessage, NotificationMessage}
import play.api.libs.json.JsValue

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
  
  def fromCommandResult(command: YeelightCommand, resultMessage: CommandResultMessage): Option[YeelightStateChange] = {
    def isOk = resultMessage.result.isOk
    
    command match {
      case SetBrightness(brightness, _, _) if isOk => Some(YeelightStateChange(brightness = Some(brightness)))
      case SetTemperature(temperature, _, _) if isOk => Some(YeelightStateChange(temperature = Some(temperature)))
      case SetPower(power, _, _) if isOk => Some(YeelightStateChange(power = Some(power)))
      case _ => None
    }
  }
  
  def isConnected(newValue: Boolean): YeelightStateChange = {
    YeelightStateChange(isConnected = Some(newValue))
  }
  
}