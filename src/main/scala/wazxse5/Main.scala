package wazxse5

import wazxse5.command.{SetBrightness, SetPower, SetRgb, SetTemperature}
import wazxse5.model.YeelightService
import wazxse5.valuetype.Power

import scala.io.StdIn

object Main {

  def main(args: Array[String]): Unit = {

    val service = new YeelightService

    val device = service.deviceOf("192.168.0.101")

    device.performCommand(SetPower(Power.on))

    while (true) {
      val brightness = StdIn.readInt
      device.performCommand(SetBrightness(brightness))

      val temperature = StdIn.readInt
      device.performCommand(SetTemperature(temperature))

      val color = StdIn.readInt
      device.performCommand(SetRgb(color))
    }

    service.devices.foreach(d => println(s" internalId=${d.internalId.uid} id=${d.id} model=${d.model} location=${d.location}"))

  }
}
 