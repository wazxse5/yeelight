package com.wazxse5.core
import com.wazxse5.api.InternalId
import com.wazxse5.api.command.YeelightCommand
import com.wazxse5.api.message.YeelightMessage
import com.wazxse5.api.model.{IYeelightDevice, IYeelightService}

class FakeYeelightService(val devices: Set[IYeelightDevice]) extends IYeelightService {
  override def deviceInfo(internalId: InternalId): Option[DeviceInfo] = ???

  override def deviceOf(deviceInfo: DeviceInfo): IYeelightDevice = ???

  override def deviceOf(address: String, port: Int): IYeelightDevice = ???

  override def search(): Unit = ???

  override def startListening(): Unit = ???

  override def stopListening(): Unit = ???

  override def performCommand(internalId: InternalId, command: YeelightCommand): Unit = ???

  override def handleMessage(message: YeelightMessage): Unit = ???

  override def connectionAdapter: ConnectionAdapter = ???
}
