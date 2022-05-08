package com.wazxse5.yeelight.core.connection

import akka.actor.{ActorRef, ActorSystem}
import com.wazxse5.yeelight.core.YeelightServiceImpl
import com.wazxse5.yeelight.core.connection.Connector.Send
import com.wazxse5.yeelight.core.message.{CommandMessage, Message}

import scala.concurrent.Await
import scala.concurrent.duration.DurationInt
import scala.util.{Failure, Success, Try}

class ConnectionAdapter(yeelightServiceImpl: YeelightServiceImpl) {
  
  private implicit val actorSystem: ActorSystem = ActorSystem("yeelight-actor-system")
  
  private val discoverer: ActorRef = actorSystem.actorOf(Discoverer.props(this))
  private val listener: ActorRef = actorSystem.actorOf(Listener.props(this))
  private var connectors: Map[String, ActorRef] = Map.empty
  
  def search(): Unit = discoverer ! Discoverer.Search
  
  def startListening(): Unit = listener ! Listener.Start
  
  def stopListening(): Unit = listener ! Listener.Stop
  
  def connect(deviceId: String, address: String, port: Int): Unit = {
    val connector = actorSystem.actorOf(Connector.props(deviceId, address, port, this))
    this.connectors += deviceId -> connector
  }
  
  def send(message: CommandMessage): Unit = {
    connectors.get(message.deviceId) foreach (_ ! Send(message))
  }
  
  def handleMessage(message: Message): Unit = yeelightServiceImpl.handleMessage(message)
  
  def exit: Int = {
    Try(Await.result(actorSystem.terminate(), 5.seconds)) match {
      case Success(_) => 0
      case Failure(_) => -1
    }
  }
}
