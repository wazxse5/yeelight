package com.wazxse5.yeelight.core

import com.wazxse5.yeelight.api.command._
import com.wazxse5.yeelight.api.valuetype._
import com.wazxse5.yeelight.core.message.{CommandResultMessage, DiscoveryResponseMessage, NotificationMessage, ResultGetProps}
import com.wazxse5.yeelight.core.util.Logger

case class YeelightStateChange(
  isConnected: Option[Boolean] = None,
  address: Option[String] = None,
  port: Option[Int] = None,
  brightness: Option[Brightness] = None,
  hue: Option[Hue] = None,
  power: Option[Power] = None,
  rgb: Option[Rgb] = None,
  saturation: Option[Saturation] = None,
  temperature: Option[Temperature] = None,
) {
  def containsChanges: Boolean = productIterator.exists {
    case option: Option[_] => option.isDefined
  }
}

object YeelightStateChange {

  val empty: YeelightStateChange = YeelightStateChange()

  def fromDiscoveryResponse(message: DiscoveryResponseMessage): YeelightStateChange = {
    YeelightStateChange(
      brightness = Brightness.fromStringOpt(message.brightness),
      hue = Hue.fromStringOpt(message.hue),
      power = Power.fromStringOpt(message.power),
      rgb = Rgb.fromStringOpt(message.rgb),
      saturation = Saturation.fromStringOpt(message.saturation),
      temperature = Temperature.fromStringOpt(message.temperature),
    )
  }

  def fromNotification(message: NotificationMessage): YeelightStateChange = {
    val propertyValueMap = message.propertyValueMap

    YeelightStateChange(
      brightness = propertyValueMap.get(PropertyName.brightness).map(Brightness.fromJsValue),
      hue = propertyValueMap.get(PropertyName.hue).map(Hue.fromJsValue),
      power = propertyValueMap.get(PropertyName.power).map(Power.fromJsValue),
      rgb = propertyValueMap.get(PropertyName.rgb).map(Rgb.fromJsValue),
      saturation = propertyValueMap.get(PropertyName.saturation).map(Saturation.fromJsValue),
      temperature = propertyValueMap.get(PropertyName.temperature).map(Temperature.fromJsValue),
    )
  }

  def fromCommandResult(command: YeelightCommand, resultMessage: CommandResultMessage): YeelightStateChange = {
    val result = resultMessage.result

    command match {
      case GetProps(propertyNames) =>
        resultMessage.result match {
          case ResultGetProps(propertyValues) =>
            val propertyValueMap = propertyNames.zip(propertyValues).filter(_._2.nonEmpty).toMap
            YeelightStateChange(
              brightness = propertyValueMap.get(PropertyName.brightness).map(Brightness.fromString),
              hue = propertyValueMap.get(PropertyName.hue).map(Hue.fromString),
              power = propertyValueMap.get(PropertyName.power).map(Power.fromString),
              rgb = propertyValueMap.get(PropertyName.rgb).map(Rgb.fromString),
              saturation = propertyValueMap.get(PropertyName.saturation).map(Saturation.fromString),
              temperature = propertyValueMap.get(PropertyName.temperature).map(Temperature.fromString),
            )
          case other =>
            Logger.error(s"CommandResultMessage for Command GetProps does not contain property values [$other]")
            YeelightStateChange.empty
        }
      case SetBrightness(brightness, _, _) if result.isOk => YeelightStateChange(brightness = Some(brightness))
      case SetHsv(hue, saturation, _, _) if result.isOk => YeelightStateChange(hue = Some(hue), saturation = Some(saturation))
      case SetPower(power, _, _) if result.isOk => YeelightStateChange(power = Some(power))
      case SetRgb(rgb, _, _) if result.isOk => YeelightStateChange(rgb = Some(rgb))
      case SetTemperature(temperature, _, _) if result.isOk => YeelightStateChange(temperature = Some(temperature))
      case _ => empty
    }
  }

  def isConnected(newValue: Boolean, address: Option[String] = None, port: Option[Int] = None): YeelightStateChange = {
    YeelightStateChange(
      isConnected = Some(newValue),
      address = address,
      port = port
    )
  }

}