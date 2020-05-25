package com.wazxse5.api.valuetype

case class TcpPort(value: Int) extends Parameter[Int] {
  override def paramName: String = TcpPort.paramName

  override def toJson: JsonValueType[_] = JsonIntValueType(value)

  override def isValid: Boolean = {
    0 < value && value <= 65535
  }

}

object TcpPort {
  val paramName: String = "port"
}
