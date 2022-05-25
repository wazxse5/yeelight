package com.wazxse5.yeelight.core

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import com.wazxse5.yeelight.api._
import com.wazxse5.yeelight.api.command.YeelightCommand
import com.wazxse5.yeelight.api.valuetype.DeviceModel
import com.wazxse5.yeelight.core.connection.ConnectionAdapter
import com.wazxse5.yeelight.core.connection.ConnectionAdapter._
import com.wazxse5.yeelight.core.message._
import com.wazxse5.yeelight.core.util.Logger

import scala.collection.concurrent.TrieMap
import scala.concurrent.Await
import scala.concurrent.duration.DurationInt
import scala.util.{Failure, Success, Try}

class YeelightServiceImpl extends YeelightService {
  private implicit val actorSystem: ActorSystem = ActorSystem("yeelight-actor-system")
  private val yeelightServiceImplActor = actorSystem.actorOf(YeelightServiceImplActor.props)
  private val connectionAdapter = actorSystem.actorOf(ConnectionAdapter.props(yeelightServiceImplActor))
  private var eventListeners = Seq.empty[ActorRef]
  private val devicesMap: TrieMap[String, YeelightDeviceImpl] = TrieMap.empty
  private val messages: TrieMap[Int, Message] = TrieMap.empty
  
  override def devices: Map[String, YeelightDevice] = devicesMap.toMap
  
  override def search(): Unit = {
    Logger.info("Searching for new devices")
    connectionAdapter ! Discover
  }
  
  override def startListening(): Unit = {
    Logger.info("Start listening")
    connectionAdapter ! StartListening
  }
  
  override def stopListening(): Unit = {
    Logger.info("Stop listening")
    connectionAdapter ! StopListening
  }
  
  override def performCommand(deviceId: String, command: YeelightCommand): Unit = {
    if (devicesMap.contains(deviceId)) {
      val message = CommandMessage(deviceId, command)
      messages.put(message.id, message)
      connectionAdapter ! SendMessage(message)
      Logger.info(s"Sending message $message")
    } else {
      Logger.error(s"Cannot perform command on unknown device $deviceId")
    }
  }
  
  override def addEventListener(listener: YeelightEventListener): Unit = {
    val actorEventListener = actorSystem.actorOf(YeelightEventListenerExecutor.props(listener))
    eventListeners = eventListeners.appended(actorEventListener)
  }
  
  override def exit(): Unit = {
    val exit = Try(Await.result(actorSystem.terminate(), 5.seconds)) match {
      case Success(_) => 0
      case Failure(_) => -1
    }
    Logger.info(s"Exit with code $exit")
  }
  
  
  class YeelightServiceImplActor extends Actor {
    private var postAction: Option[YeelightEvent] = None
  
    override def aroundReceive(receive: Receive, msg: Any): Unit = {
      this.postAction = None
      super.aroundReceive(receive, msg)
      postAction.foreach(action =>
        eventListeners.foreach(_ ! action)
      )
    }
    
    override def receive: Receive = {
      case message: ServiceMessage => handleServiceMessage(message)
      case message: YeelightMessage => handleYeelightMessage(message)
    }
  
    private def handleServiceMessage(message: ServiceMessage): Unit = {
      message match {
        case ConnectionSucceeded(deviceId) =>
          devicesMap.get(deviceId) match {
            case Some(device) =>
              Logger.info(s"Connected device $deviceId")
              updateDevice(device, YeelightStateChange.isConnected(true))
            case None =>
              Logger.error(s"Connected unknown device $deviceId")
          }
        case Disconnected(deviceId) =>
          devicesMap.get(deviceId) match {
            case Some(device) =>
              Logger.info(s"Disconnected device $deviceId")
              updateDevice(device, YeelightStateChange.isConnected(false))
            case None =>
              Logger.error(s"Disconnected unknown device $deviceId")
          }
      }
    }
  
    private def handleYeelightMessage(message: YeelightMessage): Unit = {
      message match {
        case m: AdvertisementMessage => handleAdvertisementMessage(m)
        case m: DiscoveryResponseMessage => handleDiscoveryResponseMessage(m)
        case m: CommandResultMessage => handleCommandResultMessage(m)
        case m: NotificationMessage => handleNotificationMessage(m)
        case _ => Logger.error(s"Unknown YeelightMessage: $message")
      }
    }
  
    private def handleAdvertisementMessage(m: AdvertisementMessage): Unit = {
      Logger.warn(s"Unimplemented handle message: $m")
    }
  
    private def handleDiscoveryResponseMessage(m: DiscoveryResponseMessage): Unit = {
      val yeelightStateChange = YeelightStateChange.fromDiscoveryResponse(m)
      devicesMap.get(m.deviceId) match {
        case Some(device) =>
          updateDevice(device, yeelightStateChange)
        case None =>
          val device = new YeelightDeviceImpl(m.deviceId, DeviceModel.fromString(m.model).get, m.firmwareVersion, m.supportedCommands, YeelightServiceImpl.this)
          addDevice(device, m.address, m.port, yeelightStateChange)
      }
    }
  
    private def handleCommandResultMessage(m: CommandResultMessage): Unit = {
      messages.get(m.id) match {
        case Some(CommandMessage(_, deviceId, command)) =>
          devicesMap.get(deviceId) match {
            case Some(device) =>
              val stateChange = YeelightStateChange.fromCommandResult(command, m)
              stateChange.foreach(updateDevice(device, _))
            case None =>
              Logger.warn(s"CommandResultMessage $m for unknown device $deviceId")
          }
        case Some(otherMessage) => Logger.warn(s"CommandResultMessage $m for unknown message $otherMessage")
        case None => Logger.warn(s"CommandResultMessage $m for unknown command")
      }
    }
  
    private def handleNotificationMessage(m: NotificationMessage): Unit = {
      devicesMap.get(m.deviceId) match {
        case Some(device) =>
          updateDevice(device, YeelightStateChange.fromNotification(m))
        case None =>
          Logger.warn(s"Notification from unknown device ${m.deviceId}")
      }
    }
  
    private def addDevice(device: YeelightDeviceImpl, address: String, port: Int, change: YeelightStateChange): Unit = {
      device.update(change)
      devicesMap.put(device.deviceId, device)
      connectionAdapter ! ConnectDevice(device.deviceId, address, port)
      this.postAction = Some(DeviceAdded(device.deviceId))
    }
    
    private def updateDevice(device: YeelightDeviceImpl, change: YeelightStateChange): Unit = {
      device.update(change)
      this.postAction = Some(DeviceUpdated(device.deviceId))
    }
  }
  
  object YeelightServiceImplActor {
    def props: Props = Props(new YeelightServiceImplActor())
  }
}
