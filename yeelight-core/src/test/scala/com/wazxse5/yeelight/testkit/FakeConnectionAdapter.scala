package com.wazxse5.yeelight.testkit

import com.wazxse5.yeelight.command.{GetProps, GetTimer}
import com.wazxse5.yeelight.connection.ConnectionAdapter
import com.wazxse5.yeelight.connection.Connector.ConnectionSucceeded
import com.wazxse5.yeelight.core.{DeviceInfo, YeelightService}
import MessageConverter.deviceInfoToDiscoveryResponse
import com.wazxse5.yeelight.message.{CommandMessage, CommandResultMessage, Result}
import com.wazxse5.yeelight.valuetype._
import play.api.libs.json.{JsString, Json}

import scala.collection.mutable
import scala.language.implicitConversions

case class FakeConnectionAdapter(service: YeelightService) extends ConnectionAdapter {
  private var isListening = false
  private val devices: mutable.Map[String, DeviceInfo] = {
    mutable.Map.from(FakeData.all.map(device => device.deviceId -> device))
  }

  override def search(): Unit = {
    val responseMessages = devices.values.map(deviceInfoToDiscoveryResponse)
    responseMessages.foreach(service.handleMessage)
  }

  override def startListening(): Unit = {
    isListening = true
  }

  override def stopListening(): Unit = {
    isListening = false
  }

  override def connect(deviceId: String, address: IpAddress, port: Port): Unit = {
    val deviceInfoOpt = devices.get(deviceId)
    deviceInfoOpt.filter(i => i.ipAddress.contains(address) && i.port.contains(port)) match {
      case Some(deviceInfo) if !deviceInfo.isConnected =>
        devices.update(deviceId, deviceInfo.copy(isConnected = true))
        handleMessage(ConnectionSucceeded(deviceId))
      case _ =>
    }
  }

  override def isConnected(deviceId: String): Boolean = {
    devices.get(deviceId).exists(_.isConnected)
  }

  override def send(message: CommandMessage): Unit = {
    val deviceInfo = devices(message.deviceId)

    val resultAndNotificationMessages = {
      if (message.commandName == GetProps.commandName) getProps(message, deviceInfo).toSeq
      else if (message.commandName == GetTimer.commandName) getTimer(message, deviceInfo).toSeq
      else {
        val change = YeelightServiceFakeHandler.handle(deviceInfo, message)
        devices.update(message.deviceId, deviceInfo.update(change))
        val resultMessage = CommandResultMessage(message.id, message.deviceId, Result.ok)
        val notificationMessage = MessageConverter.deviceInfoChangeToNotification(message.deviceId, change)
        Seq(resultMessage, notificationMessage)
      }
    }

    resultAndNotificationMessages.foreach(service.handleMessage)
  }

  private def getProps(message: CommandMessage, deviceInfo: DeviceInfo): Option[CommandResultMessage] = {
    implicit def vtOpt2Str[A <: ValueType[_]](opt: Option[A]): String = opt.map(_.strValue).getOrElse("")

    val propNames = message.arguments.collect { case JsString(propName) => propName }
    val propValues: Seq[String] = propNames.map {
      case Brightness.propFgName => deviceInfo.brightness
      case ColorMode.propFgName => deviceInfo.colorMode
      case FlowExpression.propFgName => deviceInfo.flowExpression
      case FlowPower.propFgName => deviceInfo.flowPower
      case Hue.propFgName => deviceInfo.hue
      case MusicPower.propFgName => deviceInfo.musicPower
      case Name.propFgName => deviceInfo.name
      case Power.propFgName => deviceInfo.power
      case Rgb.propFgName => deviceInfo.rgb
      case Saturation.propFgName => deviceInfo.saturation
      case Temperature.propFgName => deviceInfo.temperature
      case TimerValue.propFgName => deviceInfo.timerValue
      case Brightness.propBgName => deviceInfo.bgBrightness
      case ColorMode.propBgName => deviceInfo.bgColorMode
      case FlowExpression.propBgName => deviceInfo.bgFlowExpression
      case FlowPower.propBgName => deviceInfo.bgFlowPower
      case Hue.propBgName => deviceInfo.bgHue
      case Power.propBgName => deviceInfo.bgPower
      case Rgb.propBgName => deviceInfo.bgRgb
      case Saturation.propBgName => deviceInfo.bgSaturation.map(_.strValue).getOrElse("")
      case Temperature.propBgName => deviceInfo.bgTemperature
      case Brightness.propNlName => deviceInfo.nlBrightness
      case ActiveMode.propFgName => deviceInfo.activeMode
      case _ => ""
    }
    val resultJson = Json.obj("id" -> message.id, "result" -> propValues)
    CommandResultMessage.fromJson(resultJson, message.deviceId)
  }

  private def getTimer(message: CommandMessage, deviceInfo: DeviceInfo): Option[CommandResultMessage] = {
    val timerValue = deviceInfo.timerValue.map(_.strValue).getOrElse("0")
    val resultJson = Json.obj(
      "id" -> message.id,
      "result" -> Seq(
        Json.obj(
          "type" -> 0,
          "delay" -> timerValue,
          "mix" -> 0
        )
      )
    ) // TODO sprawdzić
    CommandResultMessage.fromJson(resultJson, message.deviceId)
  }

  override def exit: Int = 0

}
