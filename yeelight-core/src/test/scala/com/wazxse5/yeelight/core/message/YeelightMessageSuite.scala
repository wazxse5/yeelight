package com.wazxse5.yeelight.core.message

import com.wazxse5.yeelight.api.valuetype.PropertyName
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import play.api.libs.json.{JsNumber, JsString, Json}

class YeelightMessageSuite extends AnyFunSuite with Matchers {

  private val deviceId = "0x000000000015243f"

  test("Parse DiscoveryResponseMessage from string") {
    val string =
      s"""|HTTP/1.1 200 OK
          |Cache-Control: max-age=3600
          |Date:
          |Ext:
          |Location: yeelight://192.168.1.239:55443
          |Server: POSIX UpnP/1.0 YGLC/1
          |id: $deviceId
          |model: color
          |fw_ver: 18
          |support: get_prop set_default set_power toggle
          |power: on
          |bright: 100
          |color_mode: 2
          |ct: 4000
          |rgb: 16711680
          |hue: 100
          |sat: 35
          |name: my_bulb
          |""".stripMargin

    val expectedMessage = DiscoveryResponseMessage(
      header = "HTTP/1.1 200 OK",
      cacheControl = "3600",
      address = "192.168.1.239",
      port = 55443,
      deviceId = deviceId,
      model = "color",
      firmwareVersion = "18",
      supportedCommands = Seq("get_prop", "set_default", "set_power", "toggle"),
      power = "on",
      brightness = "100",
      colorMode = "2",
      temperature = "4000",
      rgb = "16711680",
      hue = "100",
      saturation = "35",
      name = "my_bulb"
    )

    DiscoveryResponseMessage.fromString(string).get shouldBe expectedMessage
  }

  test("Parse CommandResultMessage from json") {
    val json1 = Json.obj("id" -> 1, "result" -> Seq("ok"))
    val expectedMessage1 = CommandResultMessage(1, deviceId, ResultOk)
    YeelightConnectedMessage.fromJson(json1, deviceId).get shouldBe expectedMessage1

    val json2 = Json.obj("id" -> 2, "result" -> Seq("on", "100"))
    val expectedMessage2 = CommandResultMessage(2, deviceId, ResultGetProps(Seq("on", "100")))
    YeelightConnectedMessage.fromJson(json2, deviceId).get shouldBe expectedMessage2

    val json3 = Json.obj(
      "id" -> 3,
      "error" -> Json.obj(
        "code" -> -1,
        "message" -> "unsupported method"
      )
    )
    val expectedMessage3 = CommandResultMessage(3, deviceId, ResultError(-1, "unsupported method"))
    YeelightConnectedMessage.fromJson(json3, deviceId).get shouldBe expectedMessage3
  }

  test("Parse NotificationMessage from json") {
    val json = Json.obj(
      "method" -> "props",
      "params" -> Json.obj(
        "bright" -> 100,
        "hue" -> 100,
        "power" -> "on",
        "rgb" -> 255,
        "sat" -> 100,
        "ct" -> 4500
      )
    )

    val expectedMessage = NotificationMessage(
      deviceId,
      Map(
        PropertyName.brightness -> JsNumber(100),
        PropertyName.hue -> JsNumber(100),
        PropertyName.power -> JsString("on"),
        PropertyName.rgb -> JsNumber(255),
        PropertyName.saturation -> JsNumber(100),
        PropertyName.temperature -> JsNumber(4500),
      )
    )

    YeelightConnectedMessage.fromJson(json, deviceId).get shouldBe expectedMessage
  }

}
