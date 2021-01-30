package com.wazxse5.yeelight.core

import com.wazxse5.yeelight.command.YeelightCommand
import com.wazxse5.yeelight.connection.ConnectionAdapter
import com.wazxse5.yeelight.message.Message

class YeelightServiceFake(val devices: Set[YeelightDevice]) extends YeelightService {
  override def deviceInfo(deviceId: String): DeviceInfo = ???

  override def deviceOf(deviceInfo: DeviceInfo): YeelightDevice = ???

  override def search(): Unit = ???

  override def startListening(): Unit = ???

  override def stopListening(): Unit = ???

  override def performCommand(deviceId: String, command: YeelightCommand): Unit = ???

  override def handleMessage(message: Message): Unit = ???

  override def connectionAdapter: ConnectionAdapter = ???
}
