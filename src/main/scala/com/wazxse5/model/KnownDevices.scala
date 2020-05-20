package com.wazxse5.model

import com.wazxse5.InternalId
import com.wazxse5.connection.NetworkLocation

class KnownDevices {

  private var knownDevices: Map[InternalId, KnownDevice] = Map.empty

  def find(internalId: InternalId): Option[KnownDevice] = knownDevices.get(internalId)

  def findByLocation(location: NetworkLocation): Option[KnownDevice] =
    knownDevices.find(pair => pair._2.deviceInfo.location.contains(location)).map(_._2)

  def get(internalId: InternalId): KnownDevice = knownDevices(internalId)

  def add(knownDevice: KnownDevice): Unit = knownDevices += knownDevice.internalId -> knownDevice

  def remove(internalId: InternalId): Unit = knownDevices -= internalId

  def all: Iterable[KnownDevice] = knownDevices.values
}
