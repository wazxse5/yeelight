package com.wazxse5.yeelight.gui.data

import com.wazxse5.yeelight.api.valuetype.DeviceModel
import play.api.libs.functional.syntax.{toFunctionalBuilderOps, unlift}
import play.api.libs.json.{Reads, Writes, __}

case class YeelightDeviceAppData(
  deviceId: String,
  model: String,
  guiName: String,
  ip: String,
  port: Int
) {
  def deviceModel: DeviceModel = DeviceModel.fromString(model).get
}

object YeelightDeviceAppData {

  implicit val yeelightDeviceAppDataReads: Reads[YeelightDeviceAppData] = (
    (__ \ "deviceId").read[String] and
      (__ \ "model").read[String] and
      (__ \ "guiName").read[String] and
      (__ \ "ip").read[String] and
      (__ \ "port").read[Int]
    ) (YeelightDeviceAppData.apply _)

  implicit val yeelightDeviceAppDataWrites: Writes[YeelightDeviceAppData] = (
    (__ \ "deviceId").write[String] and
      (__ \ "model").write[String] and
      (__ \ "guiName").write[String] and
      (__ \ "ip").write[String] and
      (__ \ "port").write[Int]
  )(unlift(YeelightDeviceAppData.unapply))
}
