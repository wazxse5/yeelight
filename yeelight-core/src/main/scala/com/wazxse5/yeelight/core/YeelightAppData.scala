package com.wazxse5.yeelight.core

import com.wazxse5.yeelight.api.valuetype._
import com.wazxse5.yeelight.core.connection.{FakeCaData, FakeCaDevice}
import play.api.libs.functional.syntax.{toFunctionalBuilderOps, unlift}
import play.api.libs.json._


case class YeelightAppData(
  devices: Seq[YeelightKnownDevice],
  fakeConnectionMode: Boolean,
  fakeConnectionAdapterData: FakeCaData,
) {
  def normalMode: Boolean = !fakeConnectionMode
}

object YeelightAppData {

  val empty: YeelightAppData = YeelightAppData(Seq.empty, fakeConnectionMode = false, FakeCaData.empty)

  implicit val fakeCaDeviceWrites: Writes[FakeCaDevice] = (
    (__ \ "deviceId").write[String] and
      (__ \ "model").write[DeviceModel] and
      (__ \ "firmwareVersion").write[String] and
      (__ \ "supportedCommands").write[Seq[String]] and
      (__ \ "ip").write[String] and
      (__ \ "port").write[Int] and
      (__ \ "brightness").write[Brightness] and
      (__ \ "hue").write[Hue] and
      (__ \ "power").write[Power] and
      (__ \ "rgb").write[Rgb] and
      (__ \ "saturation").write[Saturation] and
      (__ \ "temperature").write[Temperature]
    ) (unlift(FakeCaDevice.unapply))

  implicit val fakeCaDataWrites: Writes[FakeCaData] = (
    (__ \ "isListening").write[Boolean] and
      (__ \ "devices").write[Map[String, FakeCaDevice]] and
      (__ \ "connectedDevicesIds").write[Set[String]]
    ) (unlift(FakeCaData.unapply))

  implicit val yeelightKnownDeviceWrites: Writes[YeelightKnownDevice] = (
    (__ \ "deviceId").write[String] and
      (__ \ "model").write[DeviceModel] and
      (__ \ "firmwareVersion").write[String] and
      (__ \ "supportedCommands").write[Seq[String]] and
      (__ \ "ip").write[String] and
      (__ \ "port").write[Int]
    ) (unlift(YeelightKnownDevice.unapply))

  implicit val yeelightAppDataWrites: Writes[YeelightAppData] = (o: YeelightAppData) => {
    val devices = Option.when(o.devices.nonEmpty)("devices" -> Json.toJson(o.devices))
    val fakeConnectionMode = Option.when(o.fakeConnectionMode)("fakeConnectionMode" -> Json.toJson(true))
    val fakeConnectionAdapterData = Option.when(o.fakeConnectionMode)("fakeConnectionAdapterData" -> Json.toJson(o.fakeConnectionAdapterData))
    val fieldsToWrite = Seq(devices, fakeConnectionMode, fakeConnectionAdapterData).flatten
    JsObject(fieldsToWrite)
  }


  implicit val fakeCaDeviceReads: Reads[FakeCaDevice] = (
    (__ \ "deviceId").read[String] and
      (__ \ "model").read[DeviceModel] and
      (__ \ "firmwareVersion").read[String] and
      (__ \ "supportedCommands").read[Seq[String]] and
      (__ \ "ip").read[String] and
      (__ \ "port").read[Int] and
      (__ \ "brightness").read[Brightness] and
      (__ \ "hue").read[Hue] and
      (__ \ "power").read[Power] and
      (__ \ "rgb").read[Rgb] and
      (__ \ "saturation").read[Saturation] and
      (__ \ "temperature").read[Temperature]
    ) (FakeCaDevice.apply _)

  implicit val fakeCaDataReads: Reads[FakeCaData] = (
    (__ \ "isListening").read[Boolean] and
      (__ \ "devices").read[Map[String, FakeCaDevice]] and
      (__ \ "connectedDevicesIds").read[Set[String]]
    ) (FakeCaData.apply _)

  implicit val yeelightKnownDeviceReads: Reads[YeelightKnownDevice] = (
    (__ \ "deviceId").read[String] and
      (__ \ "model").read[String].map(DeviceModel.fromString) and
      (__ \ "firmwareVersion").read[String] and
      (__ \ "supportedCommands").read[Seq[String]] and
      (__ \ "ip").read[String] and
      (__ \ "port").read[Int]
    ) (YeelightKnownDevice.apply _)

  implicit val yeelightAppDataReads: Reads[YeelightAppData] = (json: JsValue) => {
    JsSuccess(YeelightAppData(
      (json \ "devices").asOpt[Seq[YeelightKnownDevice]].getOrElse(Seq.empty),
      (json \ "fakeConnectionMode").asOpt[Boolean].getOrElse(false),
      (json \ "fakeConnectionAdapterData").asOpt[FakeCaData].getOrElse(FakeCaData.empty)
    ))
  }

}
