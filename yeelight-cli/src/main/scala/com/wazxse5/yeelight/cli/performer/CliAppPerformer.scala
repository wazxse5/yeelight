package com.wazxse5.yeelight.cli.performer

import com.wazxse5.yeelight.cli.CliCommand
import com.wazxse5.yeelight.cli.CliCommands.app
import com.wazxse5.yeelight.cli.exception.InvalidCommandException
import com.wazxse5.yeelight.core.YeelightService

object CliAppPerformer {
  def perform(command: CliCommand)(implicit yeelightService: YeelightService): Unit = {
    command.pop match {
      case app.exit =>
        yeelightService.exit
        System.exit(0)
      case _ => throw InvalidCommandException(command.mkString)
    }
  }
}
