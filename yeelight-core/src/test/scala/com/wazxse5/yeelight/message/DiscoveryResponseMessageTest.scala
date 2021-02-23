package com.wazxse5.yeelight.message

import com.wazxse5.yeelight.message.DiscoveryResponseMessage.{defaultHeader, defaultServer}
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class DiscoveryResponseMessageTest extends AnyFunSuite with Matchers {

  test("calculate from and to text") {
    val message = DiscoveryResponseMessage(
      defaultHeader,
      "3600",
      "",
      "",
      "192.168.1.239:55443",
      defaultServer,
      "0x000000000015243f",
      "color",
      "18",
      Seq("get_prop", "set_default", "set_power", "toggle", "set_bright", "start_cf", "stop_cf"),
      "on",
      "100",
      "2",
      "4000",
      "16711680",
      "100",
      "35",
      "my_bulb"
    )

    val messageFromString = DiscoveryResponseMessage.fromString(message.text)

    messageFromString.isEmpty shouldBe false
    messageFromString.get shouldBe message
  }
}
