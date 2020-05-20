package com.wazxse5.model
import com.wazxse5.InternalId
import com.wazxse5.api.{IYeelightDevice, IYeelightService}
import com.wazxse5.command.YeelightCommand

class FakeYeelightService(val devices: Set[IYeelightDevice]) extends IYeelightService {
  override def deviceInfo(internalId: InternalId): Option[DeviceInfo] = ???

  override def deviceOf(deviceInfo: DeviceInfo): IYeelightDevice = ???

  override def deviceOf(address: String, port: Int): IYeelightDevice = ???

  override def search(): Unit = ???

  override def startListening(): Unit = ???

  override def stopListening(): Unit = ???

  override def performCommand(internalId: InternalId, command: YeelightCommand): Unit = ???
}
