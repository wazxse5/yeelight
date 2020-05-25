package com.wazxse5

import com.wazxse5.api.model.YeelightService
import com.wazxse5.cli.CLI

import scala.io.StdIn

object Main {

  def main(args: Array[String]): Unit = {

    val service = new YeelightService

    val cli = new CLI(service)
    cli.perform("deviceof 192.168.0.101 prawa")
    cli.perform("deviceof 192.168.0.207 lewa")

    while (true) {
      val input = StdIn.readLine
      cli.perform(input)
    }

  }
}
 