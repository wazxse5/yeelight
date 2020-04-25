package wazxse5.model

import akka.actor.{ActorRef, ActorSystem}
import com.typesafe.scalalogging.StrictLogging
import wazxse5.UID
import wazxse5.command.YeelightCommand
import wazxse5.connection.{Connector, Discoverer, Listener, NetworkLocation}
import wazxse5.message._

class YeelightService extends IYeelightService with StrictLogging {
  private implicit val service: YeelightService = this
  private implicit val actorSystem: ActorSystem = ActorSystem("yeelight-actor-system")

  private val discoverer: ActorRef = actorSystem.actorOf(Discoverer.props(service = this))
  private val listener: ActorRef = actorSystem.actorOf(Listener.props(service = this))
  private var devicesInternal: Map[UID, KnownDevice] = Map.empty


  override def devices: Set[IYeelightDevice] = devicesInternal.values.map(cdi => YeelightDevice(cdi.deviceInfo)).toSet

  override def deviceInfo(internalId: UID): Option[DeviceInfo] = devicesInternal.get(internalId).map(_.deviceInfo)

  override def deviceOf(deviceInfo: DeviceInfo): IYeelightDevice = ??? // TODO:

  override def deviceOf(address: String, port: Int = 55443): IYeelightDevice = {
    val location = NetworkLocation(address, port)
    findDeviceByLocation(location) match {
      case Some(existingDevice) =>
        YeelightDevice(existingDevice.deviceInfo)
      case None =>
        val newDevice = KnownDevice(DeviceInfo(location))
        insertOrUpdateDevice(newDevice)
        YeelightDevice(newDevice.deviceInfo)
    }
  }

  override def search(): Unit = discoverer ! Discoverer.Search

  override def startListening(): Unit = listener ! Listener.Start

  override def stopListening(): Unit = listener ! Listener.Stop

  def performCommand(internalId: UID, command: YeelightCommand): Unit = {
    devicesInternal.get(internalId) match {
      case Some(device) => device.performCommand(command)
      case None => logger.warn(s"Cannot perform command $command") // TODO: Do refaktoryzacji na później
    }
  }

  def handleMessage(message: Message): Unit = message match {
    case apiMessage: ApiMessage => handleApiMessage(apiMessage)
    case controlMessage: ControlMessage => handleControlMessage(controlMessage)
  }

  private def handleApiMessage(message: ApiMessage): Unit = message match {
    case deviceInfoMessage: DeviceInfoMessage =>
      val location = NetworkLocation(deviceInfoMessage.location, deviceInfoMessage.locationPort)
      findDeviceByLocation(location) match {
        case Some(device) =>
          val newDeviceInfo = DeviceInfo(deviceInfoMessage).withValue(isConnected = device.deviceInfo.isConnected)
          device.setDeviceInfo(newDeviceInfo)
        case None =>
          val newDevice = KnownDevice(DeviceInfo(deviceInfoMessage))
          insertOrUpdateDevice(newDevice)
      }
    case resultMessage: CommandResultMessage =>
      println(s"CommandResultMessage ${resultMessage.text}\tresult=${resultMessage.result}")
    case notification: NotificationMessage =>
      devicesInternal.get(notification.deviceInternalId).foreach(_.updateDeviceInfo(notification))
    case _ =>
      println(s"--Unknown message--  ${message.text}")
  }

  private def handleControlMessage(message: ControlMessage): Unit = message match {
    case Connector.ConnectionSucceeded(deviceInternalId) =>
      devicesInternal.get(deviceInternalId).foreach(_.updateDeviceInfo(isConnected = true))
    case Connector.Disconnected(deviceInternalId) =>
      devicesInternal.get(deviceInternalId).foreach(_.updateDeviceInfo(isConnected = false))
  }

  private def insertOrUpdateDevice(device: KnownDevice): Unit =
    devicesInternal += device.internalId -> device

  private def findDeviceByLocation(location: NetworkLocation): Option[KnownDevice] =
    devicesInternal.find(pair => pair._2.deviceInfo.location.contains(location)).map(_._2)

  private def findDeviceById(deviceId: String): Option[KnownDevice] =
    devicesInternal.find(pair => pair._2.deviceInfo.id.contains(deviceId)).map(_._2)
}
