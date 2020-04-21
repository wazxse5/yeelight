package wazxse5

import wazxse5.command.{SetBrightness, SetTemperature}
import wazxse5.connection.NetworkLocation
import wazxse5.model.YeelightService

import scala.io.StdIn

object Main {

  def main(args: Array[String]): Unit = {

    val service = new YeelightService

    val device = service.deviceOf(NetworkLocation("192.168.0.101", 55443))

    while (true) {
      print("brightness: ")
      val brightness = StdIn.readInt
      device.performCommand(SetBrightness(brightness))

      print("temperature: ")
      val temperature = StdIn.readInt
      device.performCommand(SetTemperature(temperature))
    }

    service.devices.foreach(d => println(s" internalId=${d.internalId.uid} id=${d.id} model=${d.model} location=${d.location}"))

  }
}
 