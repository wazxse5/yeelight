package wazxse5

import wazxse5.command.{SetBrightness, SetPower, SetTemperature}
import wazxse5.connection.NetworkLocation
import wazxse5.model.YeelightService
import wazxse5.parameter.PPower.ON

object Main {

  def main(args: Array[String]): Unit = {

    val service = new YeelightService

    val device = service.deviceOf(NetworkLocation("192.168.0.101", 55443))

    device.performCommand(SetPower(ON))
    device.performCommand(SetBrightness(90))
    device.performCommand(SetTemperature(6400))

    service.devices.foreach(d => println(s" internalId=${d.internalId.uid} id=${d.id} model=${d.model} location=${d.location}"))

  }
}
 