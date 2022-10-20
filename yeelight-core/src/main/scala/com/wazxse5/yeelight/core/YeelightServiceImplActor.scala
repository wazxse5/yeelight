package com.wazxse5.yeelight.core

import akka.actor.{Props, Stash}
import com.wazxse5.yeelight.api.YeelightEventListener
import com.wazxse5.yeelight.api.command.GetProps
import com.wazxse5.yeelight.api.valuetype.DeviceModel
import com.wazxse5.yeelight.core.message.ServiceMessage._
import com.wazxse5.yeelight.core.message._
import com.wazxse5.yeelight.core.util.Implicits.ReturnNone
import com.wazxse5.yeelight.core.util.Logger


class YeelightServiceImplActor(
  yeelightService: YeelightServiceImpl,
  knownDevices: Map[String, YeelightKnownDevice]
) extends YeelightActor with Stash {

  type Data = YeelightServiceImplActorData

  override def receive: Receive = {
    case _ =>
      stash()
      unstashAll()
      context.become(receiveBeforeStart(YeelightServiceImplActorData(self)))
  }

  private def receiveBeforeStart(data: Data): Receive = {
    case StartYeelightService(connectionAdapter) =>
      unstashAll()
      knownDevices.foreach { case (deviceId, device) => connectionAdapter ! ConnectDevice(deviceId, device.ip, device.port) }
      context.become(receiveReady(data.copy(connectionAdapter = connectionAdapter)))
    case AddEventListener(listener) =>
      context.become(receiveBeforeStart(handleAddEventListener(data, listener)))
    case _ =>
      stash()
  }

  private def receiveReady(data: Data): Receive = (message: Any) => {
    val newData = message match {
      case message: ServiceMessage => handleServiceMessage(data, message)
      case message: YeelightMessage => handleYeelightMessage(data, message)
      case _ => data
    }
    newData.events.foreach { e => newData.eventListeners.foreach(_ ! e) }
    context.become(receiveReady(newData.withoutEvents))
  }

  private def handleServiceMessage(data: Data, message: ServiceMessage): Data = {
    message match {
      case AddEventListener(listener) =>
        handleAddEventListener(data, listener)
      case GetDevices =>
        (sender() ! GetDevicesResponse(data.devicesMap)).andReturn(data)
      case m: ServiceConnectionMessage =>
        handleServiceConnectionMessage(data, m)
    }
  }

  private def handleServiceConnectionMessage(data: Data, message: ServiceConnectionMessage): Data = {
    message match {
      case ConnectionSucceeded(deviceId, address, port) =>
        val isConnected = YeelightStateChange.isConnected(newValue = true, Some(address), Some(port))
        (data.devicesMap.get(deviceId), knownDevices.get(deviceId)) match {
          case (Some(_), _) =>
            Logger.info(s"Connected new device $deviceId")
            data.withDeviceUpdated(deviceId, isConnected)
          case (None, Some(known)) =>
            Logger.info(s"Connected known device $deviceId")
            self ! SendCommandMessage(CommandMessage(deviceId, GetProps.all))
            data.withDeviceAdded(
              new YeelightDeviceImpl(deviceId, known.model, known.firmwareVersion, known.supportedCommands.toSeq, yeelightService),
              isConnected
            )
          case _ =>
            Logger.warn(s"Connected unknown device $deviceId").andReturn(data)
        }
      case ConnectionFailed(deviceId) =>
        Logger.info(s"Failed to connect device $deviceId").andReturn(data)
      case DisconnectedDevice(deviceId) =>
        if (data.devicesMap.contains(deviceId)) {
          Logger.info(s"Disconnected device $deviceId")
          data.withDeviceUpdated(deviceId, YeelightStateChange.isConnected(newValue = false))
        } else {
          Logger.warn(s"Disconnected unknown device $deviceId").andReturn(data)
        }
      case m: SendCommandMessage =>
        data.connectionAdapter.forward(m)
        data.withMessage(m.message)
      case other =>
        data.connectionAdapter.forward(other).andReturn(data)
    }
  }

  private def handleYeelightMessage(data: Data, message: YeelightMessage): Data = {
    message match {
      case m: AdvertisementMessage => handleAdvertisementMessage(data, m)
      case m: DiscoveryResponseMessage => handleDiscoveryResponseMessage(data, m)
      case m: CommandResultMessage => handleCommandResultMessage(data, m)
      case m: NotificationMessage => handleNotificationMessage(data, m)
      case _ => Logger.error(s"Unknown YeelightMessage: $message").andReturn(data)
    }
  }

  private def handleAdvertisementMessage(data: Data, m: AdvertisementMessage): Data = {
    Logger.warn(s"Unimplemented handle message: $m").andReturn(data)
  }

  private def handleDiscoveryResponseMessage(data: Data, m: DiscoveryResponseMessage): Data = {
    val yeelightStateChange = YeelightStateChange.fromDiscoveryResponse(m)
    data.devicesMap.get(m.deviceId) match {
      case Some(device) =>
        data.withDeviceUpdated(device.deviceId, yeelightStateChange)
      case None =>
        data.connectionAdapter ! ConnectDevice(m.deviceId, m.address, m.port)
        data.withDeviceAdded(
          new YeelightDeviceImpl(m.deviceId, DeviceModel(m.model), m.firmwareVersion, m.supportedCommands, yeelightService),
          yeelightStateChange
        )
    }
  }

  private def handleCommandResultMessage(data: Data, m: CommandResultMessage): Data = {
    data.messageRegistry.get(m.id) match {
      case Some(commandMessage) if data.devicesMap.contains(m.deviceId) =>
        val stateChange = YeelightStateChange.fromCommandResult(commandMessage.command, m)
        data.withDeviceUpdated(commandMessage.deviceId, stateChange).withoutMessage(m.id)
      case Some(_) =>
        Logger.warn(s"CommandResultMessage $m for unknown device ${m.deviceId}")
        data.withoutMessage(m.id)
      case None =>
        Logger.warn(s"CommandResultMessage $m for unknown CommandMessage").andReturn(data)
    }
  }

  private def handleNotificationMessage(data: Data, m: NotificationMessage): Data = {
    if (data.devicesMap.contains(m.deviceId))
      data.withDeviceUpdated(m.deviceId, YeelightStateChange.fromNotification(m))
    else
      Logger.warn(s"Notification from unknown device ${m.deviceId}").andReturn(data)
  }

  private def handleAddEventListener(data: Data, listener: YeelightEventListener): Data = {
    val actorEventListener = system.actorOf(YeelightEventListenerExecutor.props(listener))
    data.withEventListener(actorEventListener)
  }
}

object YeelightServiceImplActor {
  def props(
    service: YeelightServiceImpl,
    knownDevices: Map[String, YeelightKnownDevice]
  ): Props = Props(new YeelightServiceImplActor(service, knownDevices))
}
