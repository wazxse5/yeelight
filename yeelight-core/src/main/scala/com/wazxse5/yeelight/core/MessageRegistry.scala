package com.wazxse5.yeelight.core

import com.wazxse5.yeelight.message.{CommandMessage, CommandResultMessage}

class MessageRegistry {
  private var commandMessages: Map[Int, CommandMessage] = Map.empty
  private var resultMessages: Map[Int, CommandResultMessage] = Map.empty

  def put(commandMessage: CommandMessage): Unit = {
    commandMessages += commandMessage.id -> commandMessage
  }

  def put(resultMessage: CommandResultMessage): Unit = {
    resultMessages += resultMessage.id -> resultMessage
  }

  def getCommand(id: Int): Option[CommandMessage] = commandMessages.get(id)

  def getCommand(result: CommandResultMessage): Option[CommandMessage] = commandMessages.get(result.id)

  def getResult(id: Int): Option[CommandResultMessage] = resultMessages.get(id)

  def getResult(command: CommandMessage): Option[CommandResultMessage] = resultMessages.get(command.id)

  def getCommandAndResult(id: Int): (Option[CommandMessage], Option[CommandResultMessage]) = {
    (getCommand(id), getResult(id))
  }
}
