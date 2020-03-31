package wazxse5.connection

import akka.actor.{Actor, ActorSystem}
import com.typesafe.scalalogging.StrictLogging

trait YeelightActor extends Actor with StrictLogging {
  implicit val system: ActorSystem = context.system
}
