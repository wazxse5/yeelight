package com.wazxse5.yeelight.core

import akka.actor.{Actor, ActorSystem}

trait YeelightActor extends Actor {
  implicit val system: ActorSystem = context.system
}
