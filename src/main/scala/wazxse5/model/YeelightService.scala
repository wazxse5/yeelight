package wazxse5.model

import akka.actor.{ActorRef, ActorSystem}
import com.typesafe.scalalogging.StrictLogging
import wazxse5.command.YeelightCommand
import wazxse5.connection.ConnectionSupervisorActor.{Send, StartListening, StopListening}
import wazxse5.connection.{ConnectionSupervisorActor, NetworkLocation}
import wazxse5.message._
import wazxse5.property._

class YeelightService extends IYeelightService with StrictLogging {
  private val actorSystem = ActorSystem("yeelight-actor-system")
  private val connectionsSupervisor: ActorRef = actorSystem.actorOf(ConnectionSupervisorActor.props(this))

  var devicesInfos: Map[String, DeviceInfo] = Map.empty


  override def devices: Set[IYeelightDevice] = devicesInfos.values.map(YeelightDevice(_, this)).toSet

  override def deviceInfo(deviceId: String): Option[DeviceInfo] = devicesInfos.get(deviceId)

  override def search(): Unit = connectionsSupervisor ! ConnectionSupervisorActor.PerformSearch

  override def startListening(): Unit = connectionsSupervisor ! StartListening

  override def stopListening(): Unit = connectionsSupervisor ! StopListening

  override def performCommand(deviceId: String, command: YeelightCommand): Unit = {
    devicesInfos.get(deviceId) match {
      case Some(device) if device.isConnected => connectionsSupervisor ! Send(deviceId, CommandMessage(command))
      case _ => logger.warn(s"Cannot perform command $command") // TODO: Do refaktoryzacji na później
    }
  }


  def handleMessage(message: YeelightMessage): Unit = message match {
    case _: DeviceMessage => handleDeviceMessage(_)
    case _: ResponseMessage => println("handled ResponseMessage")
    case _: ControlMessage => handleControlMessage(_)
  }

  private def handleDeviceMessage(message: DeviceMessage): Unit = {
    val newDeviceInfo = DeviceInfo(
      message.deviceId,
      DeviceModel(message.model),
      message.firmwareVersion,
      message.supportedCommands,
      Some(NetworkLocation(message.locationAddress, message.locationPort)),
      isConnected = true,
      PowerMode(message.power),
      Brightness(message.brightness),
      Temperature(message.temperature),
      Rgb(message.rgb),
      Hue(message.hue),
      Saturation(message.saturation),
      ColorMode(message.colorMode)
    )
    devicesInfos += message.deviceId -> newDeviceInfo
    connectionsSupervisor ! ConnectionSupervisorActor.Connect(message.deviceId, newDeviceInfo.location.get)
  }

  private def handleControlMessage(message: ControlMessage): Unit = message match {
    case DeviceConnected(deviceId) if devicesInfos.contains(deviceId) =>
      devicesInfos = devicesInfos + (deviceId -> devicesInfos(deviceId).withValue(isConnected = true))
    case DeviceDisconnected(deviceId) if devicesInfos.contains(deviceId) =>
      devicesInfos = devicesInfos + (deviceId -> devicesInfos(deviceId).withValue(isConnected = false))
  }
}
