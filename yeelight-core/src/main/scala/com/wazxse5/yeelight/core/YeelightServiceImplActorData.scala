package com.wazxse5.yeelight.core

import akka.actor.ActorRef
import com.wazxse5.yeelight.api.{DeviceAdded, DeviceUpdated, YeelightEvent}
import com.wazxse5.yeelight.core.message.CommandMessage

case class YeelightServiceImplActorData(
  connectionAdapter: ActorRef,
  devicesMap: Map[String, YeelightDeviceImpl] = Map.empty,
  messageRegistry: Map[Int, CommandMessage] = Map.empty,
  eventListeners: Set[ActorRef] = Set.empty,
  events: Set[YeelightEvent] = Set.empty
) {

  def withoutEvents: YeelightServiceImplActorData =
    copy(events = Set.empty)

  def withoutMessage(id: Int): YeelightServiceImplActorData =
    copy(messageRegistry = messageRegistry - id)

  def withDeviceUpdated(deviceId: String, change: YeelightStateChange): YeelightServiceImplActorData = {
    if (change.containsChanges) {
      devicesMap(deviceId).update(change)
      copy(events = events + DeviceUpdated(deviceId))
    } else this
  }

  def withDeviceAdded(device: YeelightDeviceImpl, change: YeelightStateChange): YeelightServiceImplActorData = {
    device.update(change)
    copy(
      devicesMap = devicesMap + (device.deviceId -> device),
      events = events + DeviceAdded(device.deviceId)
    )
  }

  def withEventListener(listener: ActorRef): YeelightServiceImplActorData =
    copy(eventListeners = eventListeners + listener)

  def withMessage(message: CommandMessage): YeelightServiceImplActorData =
    copy(messageRegistry = messageRegistry + (message.id -> message))

}
