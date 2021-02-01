package com.wazxse5.yeelight.connection

import com.wazxse5.yeelight.core.YeelightService
import com.wazxse5.yeelight.message.{CommandMessage, Message}
import com.wazxse5.yeelight.valuetype.{IpAddress, Port}

trait ConnectionAdapter {

  def service: YeelightService

  def search(): Unit

  def startListening(): Unit

  def stopListening(): Unit

  def connect(deviceId: String, address: IpAddress, port: Port): Unit

  def isConnected(deviceId: String): Boolean

  def send(message: CommandMessage): Unit

  def handleMessage(message: Message): Unit

  def exit: Int
}
