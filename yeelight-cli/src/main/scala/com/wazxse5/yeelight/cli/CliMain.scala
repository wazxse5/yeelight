package com.wazxse5.yeelight.cli

import com.wazxse5.yeelight.core.MYeelightService

import scala.io.StdIn

object CliMain {

  def main(args: Array[String]): Unit = {

    val service = new MYeelightService
    val cli = new CliApp(service)

    while (true) {
      val input = StdIn.readLine()
      cli.perform(input)
    }

  }
}
 