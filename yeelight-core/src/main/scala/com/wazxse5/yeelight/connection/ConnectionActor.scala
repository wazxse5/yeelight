package com.wazxse5.yeelight.connection

import akka.actor.{Actor, ActorSystem}
import com.typesafe.scalalogging.StrictLogging

trait ConnectionActor extends Actor with StrictLogging {
  implicit val system: ActorSystem = context.system
}
