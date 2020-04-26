package wazxse5

import wazxse5.cli.CLI
import wazxse5.model.YeelightService

import scala.io.StdIn

object Main {

  def main(args: Array[String]): Unit = {

    val service = new YeelightService

    val cli = new CLI(service)

    while (true) {
      val input = StdIn.readLine
      cli.perform(input)
    }

  }
}
 