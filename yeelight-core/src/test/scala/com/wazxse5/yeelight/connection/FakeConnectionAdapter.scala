package com.wazxse5.yeelight.connection
import com.wazxse5.yeelight.command.{GetProps, GetTimer}
import com.wazxse5.yeelight.connection.Connector.ConnectionSucceeded
import com.wazxse5.yeelight.core.{DeviceInfo, YeelightService}
import com.wazxse5.yeelight.message.MessageConverter.deviceInfoToDiscoveryResponse
import com.wazxse5.yeelight.message.{CommandMessage, CommandResultMessage}
import com.wazxse5.yeelight.valuetype.{IpAddress, Port}
import play.api.libs.json.Json

import scala.collection.mutable
import scala.language.implicitConversions

case class FakeConnectionAdapter(service: YeelightService) extends ConnectionAdapter {
  private var isListening = false
  private val devices: mutable.Map[String, DeviceInfo] = {
    mutable.Map.from(FakeDevices.all.map(device => device.deviceId -> device))
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

    val resultJson = {
      if (message.commandName == GetProps.commandName) ???
      else if (message.commandName == GetTimer.commandName) ???
      else {
        val change = FakeDeviceHandler.handle(deviceInfo, message)
        devices.update(message.deviceId, deviceInfo.update(change))
        Json.obj("id" -> message.id, "result" -> Seq("ok"))
      }
    }

    val resultMessage = CommandResultMessage.fromJson(resultJson, message.deviceId)
    service.handleMessage(resultMessage.get)
  }
  

  override def exit: Int = 0

}
