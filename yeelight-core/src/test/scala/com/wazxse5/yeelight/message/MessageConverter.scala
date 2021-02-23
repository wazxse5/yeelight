package com.wazxse5.yeelight.message

import com.wazxse5.yeelight.core.DeviceInfo

object MessageConverter {
  def deviceInfoToDiscoveryResponse(deviceInfo: DeviceInfo): DiscoveryResponseMessage = {
    DiscoveryResponseMessage(
      "HTTP/1.1 200 OK",
      "3600",
      "",
      "",
      s"${deviceInfo.ipAddress.get.strValue}:${deviceInfo.port.get.strValue}",
      "POSIX UPnP/1.0 YGLC/1",
      deviceInfo.deviceId,
      deviceInfo.model.get.strValue,
      deviceInfo.firmwareVersion.get,
      deviceInfo.supportedCommands.get,
      deviceInfo.power.get.strValue,
      deviceInfo.brightness.get.strValue,
      deviceInfo.colorMode.get.strValue,
      deviceInfo.temperature.get.strValue,
      deviceInfo.rgb.get.strValue,
      deviceInfo.hue.get.strValue,
      deviceInfo.saturation.get.strValue,
      deviceInfo.name.get.strValue
    )
  }
}
