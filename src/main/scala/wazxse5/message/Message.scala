package wazxse5.message

trait Message

trait YeelightMessage extends Message {
  def isValid: Boolean

  def text: String
}

trait DeviceMessage extends YeelightMessage {
  val header: String
  val cacheControl: Int
  val location: String
  val server: String
  val deviceId: String
  val model: String
  val firmwareVersion: String
  val supportedCommands: Set[String]
  val power: String
  val brightness: Int
  val colorMode: Int
  val temperature: Int
  val rgb: Int
  val hue: Int
  val saturation: Int
  val name: String

  def headerCode: Int

  def locationAddress: String = location.split(':')(0)

  def locationPort: Int = location.split(':')(1).toInt
}



sealed trait ControlMessage extends Message

final case class DeviceConnected(deviceId: String) extends ControlMessage

final case class DeviceDisconnected(deviceId: String) extends ControlMessage
