package com.wazxse5

import com.wazxse5.cli.CLI
import com.wazxse5.core.MYeelightService

import scala.io.StdIn

object Main {

  def main(args: Array[String]): Unit = {

    val service = new MYeelightService

    val cli = new CLI(service)

    while (true) {
      val input = StdIn.readLine
      cli.perform(input)
    }

  }
}
 