package com.wazxse5.connection

import com.wazxse5.core.{InternalId, YeelightService}
import com.wazxse5.message.{CommandMessage, Message}

trait ConnectionAdapter {

  def service: YeelightService

  def search(): Unit

  def startListening(): Unit

  def stopListening(): Unit

  def connect(internalId: InternalId, location: NetworkLocation): Unit

  def isConnected(internalId: InternalId): Boolean

  def send(message: CommandMessage): Unit

  def handleMessage(message: Message): Unit

  def exit: Int
}
