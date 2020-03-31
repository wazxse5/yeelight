package wazxse5

import wazxse5.model.YeelightService

import scala.io.StdIn

object Main {

  def main(args: Array[String]): Unit = {

    val service = new YeelightService

    StdIn.readLine()

    service.search()

    StdIn.readLine()

    service.devices.foreach(d => println(s"${d.model} id=${d.id} location=${d.location}"))
  }
}
 