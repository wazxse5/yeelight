package wazxse5.model

import akka.actor.{ActorRef, ActorSystem}
import com.typesafe.scalalogging.StrictLogging
import wazxse5.UID
import wazxse5.command.YeelightCommand
import wazxse5.connection.{Connector, Discoverer, Listener, NetworkLocation}
import wazxse5.message._

class YeelightService extends IYeelightService with StrictLogging {
  private implicit val service: IYeelightService = this
  private implicit val actorSystem: ActorSystem = ActorSystem("yeelight-actor-system")

  private val discoverer: ActorRef = actorSystem.actorOf(Discoverer.props(service = this))
  private val listener: ActorRef = actorSystem.actorOf(Listener.props(service = this))
  private var connectors: Map[NetworkLocation, ActorRef] = Map.empty

  private var devicesInfos: Map[UID, DeviceInfo] = Map.empty


  override def devices: Set[IYeelightDevice] = devicesInfos.values.map(YeelightDevice(_)).toSet

  override def deviceInfo(internalId: UID): Option[DeviceInfo] = devicesInfos.get(internalId)

  override def deviceOf(deviceInfo: DeviceInfo): IYeelightDevice = ??? // TODO:

  override def deviceOf(location: NetworkLocation): IYeelightDevice = {
    findDeviceByLocation(location) match {
      case Some(existingDevice) => YeelightDevice(existingDevice)
      case None =>
        val newDeviceInfo = DeviceInfo.empty.withValue(location = Some(location))
        devicesInfos += newDeviceInfo.internalId -> newDeviceInfo
        YeelightDevice(newDeviceInfo)
    }
  }

  override def search(): Unit = discoverer ! Discoverer.Search

  override def startListening(): Unit = listener ! Listener.Start

  override def stopListening(): Unit = listener ! Listener.Stop

  override def performCommand(internalId: UID, command: YeelightCommand): Unit = {
    devicesInfos.get(internalId) match {
      case Some(deviceInfo) if deviceInfo.location.nonEmpty =>
        connectors.get(deviceInfo.location.get) match {
          case Some(connector) if deviceInfo.isConnected =>
            connector ! Connector.Send(CommandMessage(command))
          case _ =>
            connectTo(deviceInfo.location.get)
            connectors.get(deviceInfo.location.get).foreach(_ ! Connector.Send(CommandMessage(command), stash = true))
        }
      case None => logger.warn(s"Cannot perform command $command") // TODO: Do refaktoryzacji na później
    }
  }

  def handleMessage(message: Message): Unit = message match {
    case apiMessage: ApiMessage => handleApiMessage(apiMessage)
    case controlMessage: ControlMessage => handleControlMessage(controlMessage)
  }

  private def handleApiMessage(message: ApiMessage): Unit = message match {
    case deviceInfoMessage: DeviceInfoMessage =>
      val newDeviceInfo = DeviceInfo(deviceInfoMessage, isConnected = false)
      devicesInfos.get(newDeviceInfo.internalId) match {
        case Some(deviceInfo) =>
          devicesInfos += newDeviceInfo.internalId -> newDeviceInfo.withValue(isConnected = deviceInfo.isConnected)
        case None =>
          connectTo(newDeviceInfo.location.get)
          devicesInfos += newDeviceInfo.internalId -> newDeviceInfo
      }
    case CommandResultMessage(id, result, text) =>
      println(s"handled CommandResultMessage result=$result text=\n$text")
  }

  private def handleControlMessage(message: ControlMessage): Unit = message match {
    case Connector.ConnectionSucceeded(location) =>
      findDeviceByLocation(location).map(_.withValue(isConnected = true))
    case Connector.Disconnected(location) =>
      findDeviceByLocation(location).map(_.withValue(isConnected = false))
  }

  private def connectTo(location: NetworkLocation): Unit = {
    val newConnector = actorSystem.actorOf(Connector.props(location, service = this))
    connectors += location -> newConnector
  }

  private def findDeviceByLocation(location: NetworkLocation): Option[DeviceInfo] =
    devicesInfos.find(pair => pair._2.location.contains(location)).map(_._2)

  private def findDeviceById(deviceId: String): Option[DeviceInfo] =
    devicesInfos.find(pair => pair._2.id.contains(deviceId)).map(_._2)
}
