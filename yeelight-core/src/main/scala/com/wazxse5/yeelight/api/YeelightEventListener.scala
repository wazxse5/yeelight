package com.wazxse5.yeelight.api

trait YeelightEventListener {
  
  def onAction(event: YeelightEvent): Unit
  
}


sealed trait YeelightEvent {
  def deviceId: String
}

case class DeviceAdded(deviceId: String) extends YeelightEvent

case class DeviceUpdated(deviceId: String) extends YeelightEvent

case class DeviceRemoved(deviceId: String) extends YeelightEvent
  

