package com.wazxse5.cli.performer

import com.wazxse5.cli.CliCommand
import com.wazxse5.cli.CliCommands.app
import com.wazxse5.cli.exception.InvalidCommandException
import com.wazxse5.core.YeelightService

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
