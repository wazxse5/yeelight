package com.wazxse5.yeelight.core.connection

import akka.actor.ActorRef
import com.wazxse5.yeelight.api.command.{AdjustBrightness, GetProps, SetBrightness, SetHsv, SetPower, SetRgb, SetTemperature, Toggle}
import com.wazxse5.yeelight.core.YeelightActor
import com.wazxse5.yeelight.core.message.{CommandMessage, CommandResultMessage, ResultOk}
import com.wazxse5.yeelight.core.message.ServiceMessage.{ConnectDevice, ConnectionFailed, ConnectionSucceeded, Discover, SendCommandMessage, StartListening, StopListening}
import com.wazxse5.yeelight.core.util.Implicits.ReturnNone
import com.wazxse5.yeelight.core.util.Logger

class FakeConnectionAdapter(
  yeelightServiceActor: ActorRef,
  initialData: FakeCaData
) extends YeelightActor {

  private type Data = FakeCaData

  override def receive: Receive = receiveWithData(initialData)

  private def receiveWithData(data: Data): Receive = (message: Any) => {
    val newData = message match {
      case StartListening =>
        data.copy(isListening = true)
      case StopListening =>
        data.copy(isListening = false)
      case Discover =>
        val responseMessages = data.devices.view.mapValues(_.discoveryResponseMessage)
        responseMessages.foreach(yeelightServiceActor ! _).andReturn(data)
      case SendCommandMessage(commandMessage, _) =>
        handleCommandMessage(data, commandMessage)
      case ConnectDevice(deviceId, address, port) =>
        if (data.exists(deviceId, address, port)) {
          yeelightServiceActor ! ConnectionSucceeded(deviceId, address, port)
          data.withConnected(deviceId)
        } else {
          (yeelightServiceActor ! ConnectionFailed(deviceId)).andReturn(data)
        }
      case other =>
        yeelightServiceActor.forward(other).andReturn(data)
    }
    context.become(receiveWithData(newData))
  }

  private def handleCommandMessage(data: Data, message: CommandMessage): Data = {
    val deviceId = message.deviceId
    val (newData, commandResult) = message.command match {
      case AdjustBrightness(percent, _) => data.updated(deviceId, _.adjustBrightness(percent))
      case GetProps(propertyNames) => data.updated(deviceId, _.getProps(propertyNames))
      case SetBrightness(brightness, _, _) => data.updated(deviceId, _.setBrightness(brightness))
      case SetHsv(hue, saturation, _, _) => data.updated(deviceId, _.setHsv(hue, saturation))
      case SetPower(power, _, _) => data.updated(deviceId, _.setPower(power))
      case SetRgb(rgb, _, _) => data.updated(deviceId, _.setRgb(rgb))
      case SetTemperature(temperature, _, _) => data.updated(deviceId, _.setTemperature(temperature))
      case Toggle => data.updated(deviceId, _.toggle)
      case other => Logger.error(s"Unsupported command $other in FakeConnectionAdapter").andReturn(data -> ResultOk)
    }
    yeelightServiceActor ! CommandResultMessage(message.id, deviceId, commandResult)
    newData
  }

}

