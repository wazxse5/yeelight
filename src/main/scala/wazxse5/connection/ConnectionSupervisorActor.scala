package wazxse5.connection

import akka.actor.{ActorRef, Props}
import wazxse5.connection.ConnectionSupervisorActor._
import wazxse5.message.CommandMessage
import wazxse5.model.YeelightService

class ConnectionSupervisorActor(service: YeelightService) extends YeelightActor {
  private val discoverer: ActorRef = context.actorOf(DiscovererActor.props(self))
  private val listener: ActorRef = context.actorOf(ListenerActor.props(self))

  private def receiveReady(implicit connectors: Map[String, ActorRef]): Receive = {
    case PerformSearch => discoverer ! DiscovererActor.Search
    case StartListening => listener ! ListenerActor.Start
    case StopListening => listener ! ListenerActor.Stop

    case Connect(id, location) => connect(id, location)
    case Send(id, message) => send(id, message)

    case DiscovererActor.NewDiscovery(discoveryMessage) => service.handleMessage(discoveryMessage)
    case ListenerActor.NewAdvertisement(advertisementMessage) => service.handleMessage(advertisementMessage)
    case _ => logger.warn("Not implemented")
  }

  private def connect(id: String, location: NetworkLocation)(implicit connectors: Map[String, ActorRef]): Unit = {
    if (!connectors.contains(id)) {
      val connector = context.actorOf(ConnectorActor.props(self, location))
      context.become(receiveReady(connectors + (id -> connector)))
    }
  }

  private def send(id: String, message: CommandMessage)(implicit connectors: Map[String, ActorRef]): Unit = {
    if (connectors.contains(id)) connectors(id) ! ConnectorActor.Send(message)
    else logger.warn("No such device")
  }

  //

  override def receive: Receive = {
    case DiscovererActor.Ready => context.become(becomeReady(discovererReady = true))
    case ListenerActor.Ready => context.become(becomeReady(listenerReady = true))
    case _ => logger.warn(s"${this.getClass.getSimpleName} is NOT ready")
  }

  private def becomeReady(discovererReady: Boolean = false, listenerReady: Boolean = false): Receive = {
    case DiscovererActor.Ready if listenerReady => becomeFullyReady()
    case ListenerActor.Ready if discovererReady => becomeFullyReady()
    case _ => logger.warn(s"${this.getClass.getSimpleName} is NOT ready")
  }

  private def becomeFullyReady(): Unit = {
    logger.info(s"${this.getClass.getSimpleName} is ready")
    context.become(receiveReady(Map.empty))
  }

}

object ConnectionSupervisorActor {
  final object Ready
  final object PerformSearch
  final object StartListening
  final object StopListening
  final case class Connect(id: String, location: NetworkLocation)
  final case class Send(id: String, message: CommandMessage)
  final case class Disconnect(id: String, remove: Boolean = false)

  def props(service: YeelightService): Props = Props(new ConnectionSupervisorActor(service))
}

