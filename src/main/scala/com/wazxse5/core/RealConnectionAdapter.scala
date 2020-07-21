package com.wazxse5.core
import akka.actor.{ActorRef, ActorSystem}
import com.wazxse5.api.InternalId
import com.wazxse5.api.message.{CommandMessage, YeelightMessage}
import com.wazxse5.api.model.IYeelightService
import com.wazxse5.core.connection.Connector.Send
import com.wazxse5.core.connection.{Connector, Discoverer, Listener, NetworkLocation}

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.language.postfixOps
import scala.util.{Failure, Success, Try}

class RealConnectionAdapter(val service: IYeelightService) extends ConnectionAdapter {

  private implicit val actorSystem: ActorSystem = ActorSystem("yeelight-actor-system")
  private val discoverer: ActorRef = actorSystem.actorOf(Discoverer.props(this))
  private val listener: ActorRef = actorSystem.actorOf(Listener.props(this))
  private var connectors: Map[InternalId, ActorRef] = Map.empty

  override def search(): Unit = discoverer ! Discoverer.Search

  override def startListening(): Unit = listener ! Listener.Start

  override def stopListening(): Unit = listener ! Listener.Stop

  override def connect(internalId: InternalId, location: NetworkLocation): Unit = {
    val connector = actorSystem.actorOf(Connector.props(location, internalId, this))
    this.connectors += internalId -> connector
  }

  override def isConnected(internalId: InternalId): Boolean = connectors.contains(internalId)

  override def send(message: CommandMessage): Unit = {
    connectors.get(message.deviceId) foreach (_ ! Send(message))
  }

  override def handleMessage(message: YeelightMessage): Unit = service.handleMessage(message)

  override def exit: Int = {
    Try(Await.result(actorSystem.terminate(), 5 seconds)) match {
      case Success(_) => 0
      case Failure(_) => -1
    }
  }
}
