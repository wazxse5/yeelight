package wazxse5.model
import wazxse5.command.YeelightCommand

class FakeYeelightService(val devices: Set[IYeelightDevice]) extends IYeelightService {
  override def deviceInfo(deviceId: String): Option[DeviceInfo] = ???

  override def search(): Unit = ???

  override def startListening(): Unit = ???

  override def stopListening(): Unit = ???

  override def performCommand(deviceId: String, command: YeelightCommand): Unit = ???
}
