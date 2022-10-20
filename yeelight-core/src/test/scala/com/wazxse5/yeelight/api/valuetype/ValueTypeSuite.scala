package com.wazxse5.yeelight.api.valuetype

import com.wazxse5.yeelight.api.exception.InvalidValueTypeValueException
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.{MatchResult, Matcher}
import org.scalatest.matchers.should.Matchers
import play.api.libs.json._

class ValueTypeSuite extends AnyFunSuite with Matchers {

  test("Brightness validity") {
    noException should be thrownBy Brightness(1)
    noException should be thrownBy Brightness(50)
    noException should be thrownBy Brightness(100)

    an[InvalidValueTypeValueException] should be thrownBy Brightness(-10)
    an[InvalidValueTypeValueException] should be thrownBy Brightness(0)
    an[InvalidValueTypeValueException] should be thrownBy Brightness(101)

    the[InvalidValueTypeValueException] thrownBy Brightness.fromString("9999") should
      have message "Invalid value for Brightness [9999]"
  }

  test("DeviceModel validity") {
    noException should be thrownBy DeviceModel("mono")
    noException should be thrownBy DeviceModel("color")
    noException should be thrownBy DeviceModel("stripe")
    noException should be thrownBy DeviceModel("ceiling")
    noException should be thrownBy DeviceModel("desklamp")
    noException should be thrownBy DeviceModel("bslamp")

    an[InvalidValueTypeValueException] should be thrownBy DeviceModel("rgb")
    an[InvalidValueTypeValueException] should be thrownBy DeviceModel("test")

    the[InvalidValueTypeValueException] thrownBy DeviceModel.fromJsValue(JsArray(Seq.empty)) should
      have message "Invalid value for DeviceModel [JsArray]"
  }

  test("Duration validity") {
    noException should be thrownBy Duration(30)
    noException should be thrownBy Duration(500)
    noException should be thrownBy Duration(10000)

    an[InvalidValueTypeValueException] should be thrownBy Duration(-20)
    an[InvalidValueTypeValueException] should be thrownBy Duration(0)
    an[InvalidValueTypeValueException] should be thrownBy Duration(29)

    the[InvalidValueTypeValueException] thrownBy Duration.fromString("x") should
      have message "Invalid value for Duration [x]"
  }

  test("Effect validity") {
    noException should be thrownBy Effect("smooth")
    noException should be thrownBy Effect("sudden")

    an[InvalidValueTypeValueException] should be thrownBy Effect("light")
    an[InvalidValueTypeValueException] should be thrownBy Effect("1")

    the[InvalidValueTypeValueException] thrownBy Effect.fromJsValue(JsString("x")) should
      have message "Invalid value for Effect [x]"
  }

  test("Hue validity") {
    noException should be thrownBy Hue(0)
    noException should be thrownBy Hue(5)
    noException should be thrownBy Hue(50)
    noException should be thrownBy Hue(359)

    an[InvalidValueTypeValueException] should be thrownBy Hue(-100)
    an[InvalidValueTypeValueException] should be thrownBy Hue(360)
    an[InvalidValueTypeValueException] should be thrownBy Hue(2000)
    an[InvalidValueTypeValueException] should be thrownBy Hue(9999)

    the[InvalidValueTypeValueException] thrownBy Hue.fromJsValue(JsString("xx")) should
      have message "Invalid value for Hue [JsString]"
  }

  test("Percent validity") {
    noException should be thrownBy Percent(-100)
    noException should be thrownBy Percent(-99)
    noException should be thrownBy Percent(-1)
    noException should be thrownBy Percent(0)
    noException should be thrownBy Percent(20)
    noException should be thrownBy Percent(99)
    noException should be thrownBy Percent(100)

    an[InvalidValueTypeValueException] should be thrownBy Percent(-1000)
    an[InvalidValueTypeValueException] should be thrownBy Percent(-101)
    an[InvalidValueTypeValueException] should be thrownBy Percent(101)
    an[InvalidValueTypeValueException] should be thrownBy Percent(2000)

    the[InvalidValueTypeValueException] thrownBy Percent.fromJsValue(JsString("17")) should
      have message "Invalid value for Percent [JsString]"
  }

  test("Power validity") {
    noException should be thrownBy Power("on")
    noException should be thrownBy Power("off")

    an[InvalidValueTypeValueException] should be thrownBy Power("yes")
    an[InvalidValueTypeValueException] should be thrownBy Power("no")

    the[InvalidValueTypeValueException] thrownBy Power.fromJsValue(JsTrue) should
      have message "Invalid value for Power [JsTrue]"
  }

  test("PropertyName validity") {
    noException should be thrownBy PropertyName("ct")
    noException should be thrownBy PropertyName("bg_bright")
    noException should be thrownBy PropertyName("power")

    an[InvalidValueTypeValueException] should be thrownBy PropertyName("effect")
    an[InvalidValueTypeValueException] should be thrownBy PropertyName("test")

    the[InvalidValueTypeValueException] thrownBy PropertyName.fromJsValue(JsNull) should
      have message "Invalid value for PropertyName [JsNull]"
  }

  test("Rgb validity") {
    noException should be thrownBy Rgb(1)
    noException should be thrownBy Rgb(55265)
    noException should be thrownBy Rgb(16777215)

    an[InvalidValueTypeValueException] should be thrownBy Rgb(-17)
    an[InvalidValueTypeValueException] should be thrownBy Rgb(0)
    an[InvalidValueTypeValueException] should be thrownBy Rgb(16777216)

    the[InvalidValueTypeValueException] thrownBy Rgb.fromJsValue(JsNumber(-44)) should
      have message "Invalid value for Rgb [-44]"
  }

  test("Rgb calculation the value of red, green and blue components") {
    Rgb(70, 140, 210) should haveColors(70, 140, 210)
    Rgb(211, 12, 78) should haveColors(211, 12, 78)
    Rgb(255, 255, 255) should haveColors(255, 255, 255)
    Rgb(0, 0, 5) should haveColors(0, 0, 5)
    Rgb(43, 0, 1) should haveColors(43, 0, 1)

    Rgb(0x279E53) should haveColors(39, 158, 83)
    Rgb(0x5680C7) should haveColors(86, 128, 199)
    Rgb(0xD80B4F) should haveColors(216, 11, 79)
    Rgb(0xFFFFFF) should haveColors(255, 255, 255)
    Rgb(0x0A0003) should haveColors(10, 0, 3)
  }

  test("Saturation validity") {
    noException should be thrownBy Saturation(0)
    noException should be thrownBy Saturation(1)
    noException should be thrownBy Saturation(87)
    noException should be thrownBy Saturation(100)

    an[InvalidValueTypeValueException] should be thrownBy Saturation(-18)
    an[InvalidValueTypeValueException] should be thrownBy Saturation(101)
    an[InvalidValueTypeValueException] should be thrownBy Saturation(999)

    the[InvalidValueTypeValueException] thrownBy Saturation.fromJsValue(JsNumber(888)) should
      have message "Invalid value for Saturation [888]"
  }

  test("Temperature validity") {
    noException should be thrownBy Temperature(1700)
    noException should be thrownBy Temperature(4000)
    noException should be thrownBy Temperature(6500)

    an[InvalidValueTypeValueException] should be thrownBy Temperature(-1)
    an[InvalidValueTypeValueException] should be thrownBy Temperature(0)
    an[InvalidValueTypeValueException] should be thrownBy Temperature(1699)
    an[InvalidValueTypeValueException] should be thrownBy Temperature(6501)

    the[InvalidValueTypeValueException] thrownBy Temperature.fromJsValue(JsNumber(997)) should
      have message "Invalid value for Temperature [997]"
  }

  private def haveColors(r: Int, g: Int, b: Int): Matcher[Rgb] = (left: Rgb) => MatchResult(
    left.red == r && left.green == g && left.blue == b,
    f"Color components do not match.\nExpected: [$r%3s, $g%3s, $b%3s]\nActual:   [${left.red}%3s, ${left.green}%3s, ${left.blue}%3s]",
    "Color components matches"
  )

}
