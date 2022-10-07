package com.wazxse5.yeelight.gui.data

import com.wazxse5.yeelight.core.util.Logger
import play.api.libs.json.Json

import java.io.IOException
import java.nio.charset.StandardCharsets
import java.nio.file.StandardOpenOption.{CREATE, WRITE}
import java.nio.file.{Files, Paths}
import java.util.{List => JList}
import scala.jdk.CollectionConverters._
import scala.util.Try


case class YeelightAppData(
  devices: Seq[YeelightKnownDeviceGui]
) {
  def devicesJava: JList[YeelightKnownDeviceGui] = devices.asJava
}

object YeelightAppData {

  val empty: YeelightAppData = YeelightAppData(Seq.empty)

  def apply(devices: JList[YeelightKnownDeviceGui]): YeelightAppData = YeelightAppData(devices.asScala.toSeq)

  private val path = {
    val home = System.getProperty("user.home")
    Paths.get(s"$home/.yeelightData.json")
  }

  def read: YeelightAppData = {
    Try {
      val dataString = Files.readString(path)
      val dataJson = Json.parse(dataString)
      val devices = (dataJson \ "devices").as[Seq[YeelightKnownDeviceGui]]
      YeelightAppData(devices)
    } getOrElse empty
  }

  def write(data: YeelightAppData): Unit = {
    try {
      val dataJson = Json.obj("devices" -> data.devices)
      val dataString = Json.prettyPrint(dataJson)
      val dataBytes = dataString.getBytes(StandardCharsets.UTF_8)
      Files.write(path, dataBytes, CREATE, WRITE)
    } catch {
      case e: IOException =>
        Logger.error(s"Cannot save app data because of: ${e.getMessage}")
    }
  }

}
