package wazxse5.model
import wazxse5.UID
import wazxse5.command.YeelightCommand
import wazxse5.connection.NetworkLocation

class FakeYeelightService(val devices: Set[IYeelightDevice]) extends IYeelightService {
  override def deviceInfo(internalId: UID): Option[DeviceInfo] = ???

  override def deviceOf(deviceInfo: DeviceInfo): IYeelightDevice = ???

  override def deviceOf(location: NetworkLocation): IYeelightDevice = ???

  override def search(): Unit = ???

  override def startListening(): Unit = ???

  override def stopListening(): Unit = ???

  override def performCommand(internalId: UID, command: YeelightCommand): Unit = ???
}
