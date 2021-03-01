package com.wazxse5.yeelight.core

import com.wazxse5.yeelight.command._
import com.wazxse5.yeelight.testkit.YeelightServiceFake
import com.wazxse5.yeelight.valuetype._
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class YeelightServiceTest extends AnyFunSuite with Matchers {

  test("discovery") {
    val service = new YeelightServiceFake

    service.devices.isEmpty shouldBe true

    service.search()

    service.devices.size shouldBe 2
  }

  test("set temperature") {
    val service = new YeelightServiceFake
    service.search()

    val devices = service.devices
    val device1 = devices.find(_.deviceId == "device1").get
    val device2 = devices.find(_.deviceId == "device2").get

    device1.state.temperature.value shouldBe Some(Temperature(3000))
    device2.state.temperature.value shouldBe Some(Temperature(4000))

    device1.performCommand(SetTemperature(3700))
    device2.performCommand(SetTemperature(5000))

    device1.state.temperature.value shouldBe Some(Temperature(3700))
    device2.state.temperature.value shouldBe Some(Temperature(5000))
  }

  test("set brightness") {
    val service = new YeelightServiceFake
    service.search()
    val device1 = service.devices.find(_.deviceId == "device1").get

    device1.performCommand(SetBrightness(20))
    device1.state.brightness.value shouldBe Some(Brightness(20))
    device1.performCommand(SetBrightness(90))
    device1.state.brightness.value shouldBe Some(Brightness(90))
  }

  test("set multiple properties") {
    val service = new YeelightServiceFake
    service.search()
    val device2 = service.devices.find(_.deviceId == "device2").get

    device2.performCommand(SetName("urządzenie"))
    device2.performCommand(SetPower(Power.on))
    device2.performCommand(SetHsv(Hue(200), Saturation(10)))

    device2.state.name.value shouldBe Some(Name("urządzenie"))
    device2.state.power.value shouldBe Some(Power.on)
    device2.state.hue.value shouldBe Some(Hue(200))
    device2.state.saturation.value shouldBe Some(Saturation(10))
  }

  test("adjust multiple properties") {
    val service = new YeelightServiceFake
    service.search()
    val device2 = service.devices.find(_.deviceId == "device2").get

    device2.state.brightness.value shouldBe Some(Brightness(20))
    device2.state.temperature.value shouldBe Some(Temperature(4000))

    device2.performCommand(AdjustBrightness(25))
    device2.performCommand(AdjustTemperature(10))

    device2.state.brightness.value shouldBe Some(Brightness(45))
    device2.state.temperature.value shouldBe Some(Temperature(4400))
  }

  test("set power and toggle") {
    val service = new YeelightServiceFake
    service.search()
    val device1 = service.devices.find(_.deviceId == "device1").get

    device1.performCommand(SetPower(Power.off))
    device1.state.power.value shouldBe Some(Power.off)
    device1.state.colorMode.value shouldBe Some(ColorMode.temperature)

    device1.performCommand(SetPower(Power.on, Effect.smooth, Duration(500), PowerMode.rgb))
    device1.state.power.value shouldBe Some(Power.on)
    device1.state.colorMode.value shouldBe Some(ColorMode.rgb)


    device1.performCommand(Toggle)
    device1.state.power.value shouldBe Some(Power.off)

    device1.performCommand(Toggle)
    device1.state.power.value shouldBe Some(Power.on)
  }
}
