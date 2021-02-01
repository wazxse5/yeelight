package com.wazxse5.yeelight.core

import com.wazxse5.yeelight.valuetype.IpAddress

class KnownDevices {

  private var knownDevices: Map[String, DeviceInfo] = Map.empty

  def findById(deviceId: String): Option[DeviceInfo] = knownDevices.get(deviceId)

  def findByIp(ipAddress: IpAddress): Option[DeviceInfo] =
    knownDevices.find(pair => pair._2.ipAddress.contains(ipAddress)).map(_._2)

  def get(deviceId: String): DeviceInfo = knownDevices(deviceId)

  def contains(deviceId: String): Boolean = knownDevices.contains(deviceId)

  def contains(ipAddress: IpAddress): Boolean = findByIp(ipAddress).isDefined

  def add(knownDevice: DeviceInfo): Unit = knownDevices += knownDevice.deviceId -> knownDevice

  def remove(deviceId: String): Unit = knownDevices -= deviceId

  def all: Iterable[DeviceInfo] = knownDevices.values

  def update(deviceId: String, change: DeviceInfoChange): Unit = {
    if (knownDevices.contains(deviceId)) {
      knownDevices += deviceId -> get(deviceId).update(change)
    }
  }

  def update(deviceId: String, isConnected: Boolean): Unit = knownDevices.get(deviceId) match {
    case Some(value) => knownDevices += deviceId -> value.copy(isConnected = isConnected)
    case None =>
  }

}
