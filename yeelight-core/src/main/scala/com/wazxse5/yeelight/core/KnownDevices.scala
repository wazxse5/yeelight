package com.wazxse5.yeelight.core

import com.wazxse5.yeelight.connection.NetworkLocation

class KnownDevices {

  private var knownDevices: Map[InternalId, DeviceInfo] = Map.empty

  def find(internalId: InternalId): Option[DeviceInfo] = knownDevices.get(internalId)

  def findByLocation(location: NetworkLocation): Option[DeviceInfo] =
    knownDevices.find(pair => pair._2.location.contains(location)).map(_._2)

  def get(internalId: InternalId): DeviceInfo = knownDevices(internalId)

  def contains(internalId: InternalId): Boolean = knownDevices.contains(internalId)

  def contains(location: NetworkLocation): Boolean = findByLocation(location).isDefined

  def add(knownDevice: DeviceInfo): Unit = knownDevices += knownDevice.internalId -> knownDevice

  def remove(internalId: InternalId): Unit = knownDevices -= internalId

  def all: Iterable[DeviceInfo] = knownDevices.values

  def update(internalId: InternalId, stateUpdate: PropsUpdate): Unit = {
    if (knownDevices.contains(internalId)) {
      knownDevices += internalId -> get(internalId).withStateUpdate(stateUpdate)
    }
  }

  def update(internalId: InternalId, deviceInfo: DeviceInfo): Unit = knownDevices.get(internalId) match {
    case Some(value) => knownDevices += internalId -> value.update(deviceInfo)
    case None =>
  }

  def update(internalId: InternalId, isConnected: Boolean): Unit = knownDevices.get(internalId) match {
    case Some(value) => knownDevices += internalId -> value.copy(isConnected = isConnected)
    case None =>
  }

}
