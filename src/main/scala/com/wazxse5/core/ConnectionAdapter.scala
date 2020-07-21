package com.wazxse5.core

import com.wazxse5.api.InternalId
import com.wazxse5.api.message.{CommandMessage, YeelightMessage}
import com.wazxse5.api.model.IYeelightService
import com.wazxse5.core.connection.NetworkLocation

trait ConnectionAdapter {

  def service: IYeelightService

  def search(): Unit

  def startListening(): Unit

  def stopListening(): Unit

  def connect(internalId: InternalId, location: NetworkLocation): Unit

  def isConnected(internalId: InternalId): Boolean

  def send(message: CommandMessage): Unit

  def handleMessage(message: YeelightMessage): Unit

  def exit: Int
}
