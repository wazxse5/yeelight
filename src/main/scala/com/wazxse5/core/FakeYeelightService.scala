package com.wazxse5.core
import com.wazxse5.command.YeelightCommand
import com.wazxse5.connection.ConnectionAdapter
import com.wazxse5.message.YeelightMessage

class FakeYeelightService(val devices: Set[YeelightDevice]) extends YeelightService {
  override def deviceInfo(internalId: InternalId): Option[DeviceInfo] = ???

  override def deviceOf(deviceInfo: DeviceInfo): YeelightDevice = ???

  override def deviceOf(address: String, port: Int): YeelightDevice = ???

  override def search(): Unit = ???

  override def startListening(): Unit = ???

  override def stopListening(): Unit = ???

  override def performCommand(internalId: InternalId, command: YeelightCommand): Unit = ???

  override def handleMessage(message: YeelightMessage): Unit = ???

  override def connectionAdapter: ConnectionAdapter = ???
}
