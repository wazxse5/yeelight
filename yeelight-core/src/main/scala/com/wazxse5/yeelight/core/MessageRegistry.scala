package com.wazxse5.yeelight.core

import com.wazxse5.yeelight.message.{CommandMessage, CommandResultMessage, YeelightMessage}

class MessageRegistry {
  private var commandMessages: Map[Int, CommandMessage] = Map.empty
  private var resultMessages: Map[Int, CommandResultMessage] = Map.empty
  private var otherMessages: Seq[YeelightMessage] = Seq.empty

  def add(message: YeelightMessage): Unit = {
    message match {
      case command: CommandMessage => commandMessages += command.id -> command
      case result: CommandResultMessage => resultMessages += result.id -> result
      case m => otherMessages = otherMessages :+ m
    }
  }

  def getCommand(id: Int): Option[CommandMessage] = commandMessages.get(id)

  def getCommand(result: CommandResultMessage): Option[CommandMessage] = commandMessages.get(result.id)

  def getResult(id: Int): Option[CommandResultMessage] = resultMessages.get(id)

  def getResult(command: CommandMessage): Option[CommandResultMessage] = resultMessages.get(command.id)

  def getCommandAndResult(id: Int): (Option[CommandMessage], Option[CommandResultMessage]) = {
    (getCommand(id), getResult(id))
  }
}
