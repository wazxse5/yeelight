package com.wazxse5.yeelight.connection

import com.wazxse5.yeelight.command._
import com.wazxse5.yeelight.core.{Change, DeviceInfo, DeviceInfoChange, Modify}
import com.wazxse5.yeelight.message.CommandMessage
import com.wazxse5.yeelight.valuetype._

import scala.language.implicitConversions

object FakeDeviceHandler {

  def handle(deviceInfo: DeviceInfo, message: CommandMessage): DeviceInfoChange = {
    message.commandName match {
      case AdjustBrightness.commandName => adjustBrightness(deviceInfo, message)
      case AdjustColor.commandName => adjustColor(deviceInfo, message)
      case AdjustTemperature.commandName => adjustTemperature(deviceInfo, message)
      case SetBrightness.commandName => DeviceInfoChange(brightness = Brightness.fromJsValue(message.arguments.head))
      case SetDefault.commandName => ??? // TODO
      case SetHsv.commandName => DeviceInfoChange(hue = Hue.fromJsValue(message.arguments.head))
      case SetMusic.commandName => DeviceInfoChange(musicPower = MusicPower.fromJsValue(message.arguments.head))
      case SetName.commandName => DeviceInfoChange(name = Name.fromJsValue(message.arguments.head))
      case SetPower.commandName => DeviceInfoChange(power = Power.fromJsValue(message.arguments.head))
      case SetRgb.commandName => DeviceInfoChange(rgb = Rgb.fromJsValue(message.arguments.head))
      case SetScene.commandName => setScene(deviceInfo, message)
      case SetTemperature.commandName => DeviceInfoChange(temperature = Temperature.fromJsValue(message.arguments.head))
      case StartFlow.commandName => startFlow(deviceInfo, message)
      case StopFlow.commandName => DeviceInfoChange(flowPower = Modify(FlowPower.off))
      case StopTimer.commandName => ??? // TODO
      case Toggle.commandName => toggle(deviceInfo, message)
    }
  }

  private def adjustBrightness(deviceInfo: DeviceInfo, message: CommandMessage): DeviceInfoChange = {
    val percentOpt = Percent.fromJsValue(message.arguments.head)
    (deviceInfo.brightness, percentOpt) match {
      case (Some(oldValue), Some(percent)) =>
        DeviceInfoChange(brightness = Modify(Brightness(oldValue.value * percent.value)))
      case _ => DeviceInfoChange.empty
    }
  }

  private def adjustColor(deviceInfo: DeviceInfo, message: CommandMessage): DeviceInfoChange = {
    val percentOpt = Percent.fromJsValue(message.arguments.head)
    (deviceInfo.rgb, percentOpt) match {
      case (Some(oldValue), Some(percent)) =>
        DeviceInfoChange(rgb = Modify(Rgb(oldValue.value * percent.value)))
      case _ => DeviceInfoChange.empty
    }
  }

  private def adjustTemperature(deviceInfo: DeviceInfo, message: CommandMessage): DeviceInfoChange = {
    val percentOpt = Percent.fromJsValue(message.arguments.head)
    (deviceInfo.temperature, percentOpt) match {
      case (Some(oldValue), Some(percent)) =>
        DeviceInfoChange(temperature = Modify(Temperature(oldValue.value * percent.value)))
      case _ => DeviceInfoChange.empty
    }
  }

  private def setScene(deviceInfo: DeviceInfo, message: CommandMessage): DeviceInfoChange = {
    ??? // TODO
  }

  private def startFlow(deviceInfo: DeviceInfo, message: CommandMessage): DeviceInfoChange = {
    ??? // TODO
  }

  private def toggle(deviceInfo: DeviceInfo, message: CommandMessage): DeviceInfoChange = {
    val newValue = deviceInfo.power.getOrElse(PowerOff) match {
      case PowerOn => PowerOff
      case PowerOff => PowerOn
    }
    DeviceInfoChange(power = Modify(newValue))
  }


  private implicit def optionToChange[A](opt: Option[A]): Change[A] = Change.fromOption(opt)

}
