package com.wazxse5.yeelight.gui.data

import com.wazxse5.yeelight.api.valuetype.DeviceModel
import com.wazxse5.yeelight.core.YeelightKnownDevice
import play.api.libs.functional.syntax.{toFunctionalBuilderOps, unlift}
import play.api.libs.json.{Reads, Writes, __}

case class YeelightKnownDeviceGui(
  deviceId: String,
  model: String,
  firmwareVersion: String,
  supportedCommands: Seq[String],
  ip: String,
  port: Int,
  guiName: String,
) {

  def toKnownDevice:YeelightKnownDevice = YeelightKnownDevice(
    deviceId, DeviceModel.fromString(model).get, firmwareVersion, supportedCommands, ip, port
  )

}

object YeelightKnownDeviceGui {

  implicit val yeelightDeviceAppDataReads: Reads[YeelightKnownDeviceGui] = (
    (__ \ "deviceId").read[String] and
      (__ \ "model").read[String] and
      (__ \ "firmwareVersion").read[String] and
      (__ \ "supportedCommands").read[Seq[String]] and
      (__ \ "ip").read[String] and
      (__ \ "port").read[Int] and
      (__ \ "guiName").read[String]
    ) (YeelightKnownDeviceGui.apply _)

  implicit val yeelightDeviceAppDataWrites: Writes[YeelightKnownDeviceGui] = (
    (__ \ "deviceId").write[String] and
      (__ \ "model").write[String] and
      (__ \ "firmwareVersion").write[String] and
      (__ \ "supportedCommands").write[Seq[String]] and
      (__ \ "ip").write[String] and
      (__ \ "port").write[Int] and
      (__ \ "guiName").write[String]
    ) (unlift(YeelightKnownDeviceGui.unapply))
}
