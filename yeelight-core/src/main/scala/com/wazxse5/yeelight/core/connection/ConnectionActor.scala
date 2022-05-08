package com.wazxse5.yeelight.core.connection

import akka.actor.Actor
import akka.actor.ActorSystem

trait ConnectionActor extends Actor {
  implicit val system: ActorSystem = context.system
}