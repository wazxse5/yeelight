package com.wazxse5.yeelight.core

import akka.actor.ActorSystem
import akka.pattern.ask
import com.wazxse5.yeelight.api._
import com.wazxse5.yeelight.api.command.YeelightCommand
import com.wazxse5.yeelight.core.connection.{ConnectionAdapter, FakeConnectionAdapter}
import com.wazxse5.yeelight.core.message.ServiceMessage._
import com.wazxse5.yeelight.core.message._
import com.wazxse5.yeelight.core.util.Logger

import scala.concurrent.Await
import scala.concurrent.duration.DurationInt
import scala.util.{Failure, Success, Try}

class YeelightServiceImpl(appData: YeelightAppData) extends YeelightService {

  private implicit val actorSystem: ActorSystem = ActorSystem("yeelight-actor-system")

  private val serviceActor = {
    val knownDevices = if (appData.normalMode) appData.devices else Seq.empty
    actorSystem.actorOf(YeelightServiceImplActor.props(this, knownDevices.map(d => d.deviceId -> d).toMap))
  }
  private val connectionActor = {
    if (appData.normalMode) actorSystem.actorOf(ConnectionAdapter.props(serviceActor))
    else actorSystem.actorOf(FakeConnectionAdapter.props(serviceActor, appData.fakeConnectionAdapterData))
  }

  override def devices: Map[String, YeelightDevice] = {
    val future = (serviceActor ? GetDevices) (1.seconds).mapTo[GetDevicesResponse]
    Await.result(future, 1.seconds).devicesMap
  }

  override def start(): Unit = {
    Logger.info("Starting YeelightService")
    serviceActor ! StartYeelightService(connectionActor)
  }

  override def search(): Unit = {
    Logger.info("Searching for new devices")
    connectionActor ! Discover
  }

  override def startListening(): Unit = {
    Logger.info("Start listening")
    connectionActor ! StartListening
  }

  override def stopListening(): Unit = {
    Logger.info("Stop listening")
    connectionActor ! StopListening
  }

  override def performCommand(deviceId: String, command: YeelightCommand): Unit = {
    Logger.info(s"Performing for $deviceId command $command")
    val message = CommandMessage(deviceId, command)
    serviceActor ! SendCommandMessage(message)
  }

  override def addEventListener(listener: YeelightEventListener): Unit = {
    serviceActor ! AddEventListener(listener)
  }

  override def getAppData: YeelightAppData = {
    val oldKnownDevices = appData.devices.map(d => d.deviceId -> d).toMap
    val newKnownDevices = devices.view.mapValues(YeelightKnownDevice.fromYeelightDevice).toMap
    YeelightAppData(
      (oldKnownDevices ++ newKnownDevices).values.toSeq,
      appData.fakeConnectionMode,
      appData.fakeConnectionAdapterData
    )
  }

  override def exit(): Unit = {
    val exit = Try(Await.result(actorSystem.terminate(), 5.seconds)) match {
      case Success(_) => 0
      case Failure(_) => -1
    }
    Logger.info(s"Exit with code $exit")
  }
}
