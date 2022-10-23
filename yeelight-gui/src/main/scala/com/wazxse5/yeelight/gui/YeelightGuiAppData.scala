package com.wazxse5.yeelight.gui

import com.wazxse5.yeelight.core.YeelightAppData
import com.wazxse5.yeelight.core.util.Logger
import play.api.libs.functional.syntax.{toFunctionalBuilderOps, unlift}
import play.api.libs.json._

import java.nio.charset.StandardCharsets
import java.nio.file.StandardOpenOption.{CREATE, TRUNCATE_EXISTING, WRITE}
import java.nio.file.{Files, Paths}
import java.util.{Map => JMap}
import scala.jdk.CollectionConverters._
import scala.util.Try


case class YeelightGuiAppData(
  appData: YeelightAppData,
  guiDeviceNames: Map[String, String],
) {
  def guiDeviceNamesJava: JMap[String, String] = guiDeviceNames.asJava
}

object YeelightGuiAppData {

  val empty: YeelightGuiAppData = new YeelightGuiAppData(YeelightAppData.empty, Map.empty)

  def apply(appData: YeelightAppData, guiDeviceNames: JMap[String, String]): YeelightGuiAppData =
    new YeelightGuiAppData(appData, guiDeviceNames.asScala.toMap)

  private val path = {
    val home = System.getProperty("user.home")
    Paths.get(s"$home/.yeelightData.json")
  }

  def read: YeelightGuiAppData = {
    Try {
      val dataString = Files.readString(path)
      Json.parse(dataString).as[YeelightGuiAppData]
    } getOrElse empty
  }

  def write(data: YeelightGuiAppData): Unit = {
    try {
      val dataString = Json.prettyPrint(Json.toJson(data))
      val dataBytes = dataString.getBytes(StandardCharsets.UTF_8)
      Files.write(path, dataBytes, CREATE, WRITE, TRUNCATE_EXISTING)
    } catch {
      case e: Exception =>
        Logger.error(s"Cannot save app data because of: ${e.getMessage}")
    }
  }

  implicit val yeelightGuiAppDataWrites: Writes[YeelightGuiAppData] = (
    (__ \ "appData").write[YeelightAppData] and
      (__ \ "guiDeviceNames").write[Map[String, String]]
    ) (unlift(YeelightGuiAppData.unapply))


  implicit val yeelightGuiAppDataReads: Reads[YeelightGuiAppData] = (json: JsValue) => {
    JsSuccess(YeelightGuiAppData(
      (json \ "appData").asOpt[YeelightAppData].getOrElse(YeelightAppData.empty),
      (json \ "guiDeviceNames").asOpt[Map[String, String]].getOrElse(Map.empty),
    ))
  }
}
