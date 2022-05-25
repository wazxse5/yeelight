package com.wazxse5.yeelight.core

import akka.actor.{Actor, Props}
import com.wazxse5.yeelight.api.{YeelightEvent, YeelightEventListener}

class YeelightEventListenerExecutor(listener: YeelightEventListener) extends Actor {
  override def receive: Receive = {
    case event: YeelightEvent => listener.onAction(event)
  }
}

object YeelightEventListenerExecutor {
  
  def props(listener: YeelightEventListener): Props =
    Props(new YeelightEventListenerExecutor(listener))
}
