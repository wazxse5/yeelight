package com.wazxse5.yeelight.connection

import akka.actor.{ActorRef, ActorSystem}
import com.wazxse5.yeelight.connection.Connector.Send
import com.wazxse5.yeelight.core.YeelightService
import com.wazxse5.yeelight.message.{CommandMessage, Message}
import com.wazxse5.yeelight.valuetype.{IpAddress, Port}

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.language.postfixOps
import scala.util.{Failure, Success, Try}

class RealConnectionAdapter(val service: YeelightService) extends ConnectionAdapter {

  private implicit val actorSystem: ActorSystem = ActorSystem("yeelight-actor-system")
  private val discoverer: ActorRef = actorSystem.actorOf(Discoverer.props(this))
  private val listener: ActorRef = actorSystem.actorOf(Listener.props(this))
  private var connectors: Map[String, ActorRef] = Map.empty

  override def search(): Unit = discoverer ! Discoverer.Search

  override def startListening(): Unit = listener ! Listener.Start

  override def stopListening(): Unit = listener ! Listener.Stop

  override def connect(deviceId: String, address: IpAddress, port: Port): Unit = {
    val connector = actorSystem.actorOf(Connector.props(deviceId, address, port, this))
    this.connectors += deviceId -> connector
  }

  override def isConnected(deviceId: String): Boolean = connectors.contains(deviceId)

  override def send(message: CommandMessage): Unit = {
    connectors.get(message.deviceId) foreach (_ ! Send(message))
  }

  override def handleMessage(message: Message): Unit = service.handleMessage(message)

  override def exit: Int = {
    Try(Await.result(actorSystem.terminate(), 5 seconds)) match {
      case Success(_) => 0
      case Failure(_) => -1
    }
  }
}
