package com.wazxse5.yeelight.valuetype

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import play.api.libs.json.{JsNumber, JsString}

class ValueTypesValidityTest extends AnyFunSuite with Matchers {

  test(s"${ActiveMode.name} validity") {
    ActiveMode.fromString("0") shouldBe Some(ActiveModeDaylight)
    ActiveMode.fromString("1") shouldBe Some(ActiveModeMoonlight)
    ActiveMode.fromString("17") shouldBe None
    ActiveMode.fromString("test") shouldBe None
    ActiveMode.fromJsValue(JsNumber(1)) shouldBe Some(ActiveModeMoonlight)
    ActiveMode.fromJsValue(JsString("0")) shouldBe None
  }

  test(s"${Brightness.name} validity") {
    Brightness(-30).isValid shouldBe false
    Brightness(0).isValid shouldBe false
    Brightness(1).isValid shouldBe true
    Brightness(55).isValid shouldBe true
    Brightness(100).isValid shouldBe true
    Brightness(101).isValid shouldBe false
    Brightness(9999).isValid shouldBe false
    Brightness.fromString("50") shouldBe Some(Brightness(50))
    Brightness.fromString("120") shouldBe None
    Brightness.fromJsValue(JsString("10")) shouldBe None
    Brightness.fromJsValue(JsNumber(12)) shouldBe Some(Brightness(12))
  }

  test(s"${ColorMode.name} validity") {
    ColorMode.fromString("1") shouldBe Some(ColorModeRgb)
    ColorMode.fromString("2") shouldBe Some(ColorModeTemperature)
    ColorMode.fromString("3") shouldBe Some(ColorModeHsv)
    ColorMode.fromString("17") shouldBe None
    ColorMode.fromString("test") shouldBe None
    ColorMode.fromJsValue(JsNumber(2)) shouldBe Some(ColorModeTemperature)
    ColorMode.fromJsValue(JsString("0")) shouldBe None
  }

  test(s"${DeviceModel.name} validity") {
    DeviceModel.fromString("mono") shouldBe Some(DeviceModelMono)
    DeviceModel.fromString("color") shouldBe Some(DeviceModelColor)
    DeviceModel.fromString("stripe") shouldBe Some(DeviceModelStripe)
    DeviceModel.fromString("ceiling") shouldBe Some(DeviceModelCeiling)
    DeviceModel.fromString("desklamp") shouldBe Some(DeviceModelDeskLamp)
    DeviceModel.fromString("bslamp") shouldBe Some(DeviceModelBsLamp)
    DeviceModel.fromJsValue(JsNumber(2)) shouldBe None
    DeviceModel.fromJsValue(JsString("stripe")) shouldBe Some(DeviceModelStripe)
  }

  test(s"${Duration.name} validity") {
    Duration(-10).isValid shouldBe false
    Duration(1).isValid shouldBe false
    Duration(30).isValid shouldBe true
    Duration(8888).isValid shouldBe true
    Duration.fromString("29") shouldBe None
    Duration.fromString("2000") shouldBe Some(Duration(2000))
    Duration.fromJsValue(JsString("1000")) shouldBe None
    Duration.fromJsValue(JsNumber(500)) shouldBe Some(Duration(500))
  }

  test(s"${Effect.name} validity") {
    Effect.fromString("sudden") shouldBe Some(EffectSudden)
    Effect.fromString("smooth") shouldBe Some(EffectSmooth)
    Effect.fromString("0") shouldBe None
    Effect.fromString("test") shouldBe None
    Effect.fromJsValue(JsNumber(1)) shouldBe None
    Effect.fromJsValue(JsString("smooth")) shouldBe Some(EffectSmooth)
  }

  test(s"${FlowAction.name} validity") {
    FlowAction.fromString("0") shouldBe Some(FlowActionRecover)
    FlowAction.fromString("1") shouldBe Some(FlowActionStay)
    FlowAction.fromString("2") shouldBe Some(FlowActionTurnOff)
    FlowAction.fromString("stay") shouldBe None
    FlowAction.fromJsValue(JsNumber(1)) shouldBe Some(FlowActionStay)
    FlowAction.fromJsValue(JsString("0")) shouldBe None
  }

  test(s"${FlowBlockMode.name} validity") {
    FlowBlockMode.fromString("1") shouldBe Some(FlowBlockModeRgb)
    FlowBlockMode.fromString("2") shouldBe Some(FlowBlockModeTemperature)
    FlowBlockMode.fromString("7") shouldBe Some(FlowBlockModeSleep)
    FlowBlockMode.fromString("sleep") shouldBe None
    FlowBlockMode.fromJsValue(JsNumber(1)) shouldBe Some(FlowBlockModeRgb)
    FlowBlockMode.fromJsValue(JsString("7")) shouldBe None
  }

  test(s"${FlowCount.name} validity") {
    FlowCount(0).isValid shouldBe true
    FlowCount(-20).isValid shouldBe false
    FlowCount(5000).isValid shouldBe true
    FlowCount.fromString("10") shouldBe Some(FlowCount(10))
    FlowCount.fromString("infinite") shouldBe None
    FlowCount.fromJsValue(JsNumber(20)) shouldBe Some(FlowCount(20))
    FlowCount.fromJsValue(JsString("999")) shouldBe None
  }

  test(s"${FlowExpression.name} validity") {
    FlowExpression(Seq(FlowBlock.rgb(5555, 255, 50))).isValid shouldBe true
    FlowExpression(Seq(FlowBlock.temperature(5000, 4000, 80))).isValid shouldBe true
    FlowExpression(Seq(FlowBlock.sleep(7777))).isValid shouldBe true
    FlowExpression(Seq(FlowBlock(Duration(600), FlowBlockMode.rgb, -1, Brightness(99)))).isValid shouldBe false
    FlowExpression.fromString("1000,2,2700,100") shouldBe Some(FlowExpression(Seq(
      FlowBlock(Duration(1000), FlowBlockModeTemperature, 2700, Brightness(100))
    )))
    FlowExpression.fromString("2300,1,65000,70,5000,2,3000,80,4400,7,0,10") shouldBe Some(FlowExpression(Seq(
      FlowBlock(Duration(2300), FlowBlockModeRgb, 65000, Brightness(70)),
      FlowBlock(Duration(5000), FlowBlockModeTemperature, 3000, Brightness(80)),
      FlowBlock(Duration(4400), FlowBlockModeSleep, 0, Brightness(10))
    )))
    FlowExpression.fromJsValue(JsString("10000,1,100000,100")) shouldBe Some(FlowExpression(Seq(
      FlowBlock(Duration(10000), FlowBlockModeRgb, 100000, Brightness(100))
    )))
    FlowExpression.fromString("1000, 2, 2700, 100") shouldBe None
    FlowExpression.fromString("0,1,255,30") shouldBe None
    FlowExpression.fromString("500,0,255,30") shouldBe None
    FlowExpression.fromString("1000,2,45000,90") shouldBe None
    FlowExpression.fromString("8000,2,3000,120") shouldBe None
    FlowExpression.fromString("1000,2,2700,100,500,1,255,10,5000") shouldBe None
  }

  test(s"${FlowPower.name} validity") {
    FlowPower.fromString("0") shouldBe Some(FlowOff)
    FlowPower.fromString("1") shouldBe Some(FlowOn)
    FlowPower.fromString("2") shouldBe None
    FlowPower.fromString("on") shouldBe None
    FlowPower.fromJsValue(JsNumber(1)) shouldBe Some(FlowOn)
    FlowPower.fromJsValue(JsString("off")) shouldBe None
  }

  test(s"${Hue.name} validity") {
    Hue(-30).isValid shouldBe false
    Hue(0).isValid shouldBe true
    Hue(55).isValid shouldBe true
    Hue(359).isValid shouldBe true
    Hue(360).isValid shouldBe false
    Hue(99999).isValid shouldBe false
    Hue.fromString("50") shouldBe Some(Hue(50))
    Hue.fromString("1200") shouldBe None
    Hue.fromJsValue(JsString("10")) shouldBe None
    Hue.fromJsValue(JsNumber(12)) shouldBe Some(Hue(12))
  }

  test(s"${IpAddress.name} validity") {
    IpAddress("192.168.0.14").isValid shouldBe true
    IpAddress("180.48.11.254").isValid shouldBe true
    IpAddress("192.168.0.1445").isValid shouldBe false
    IpAddress("092.168.0.144").isValid shouldBe false
    IpAddress("localhost").isValid shouldBe false
    IpAddress("256.234.0.14").isValid shouldBe false
    IpAddress.fromString("100.100.100.100") shouldBe Some(IpAddress("100.100.100.100"))
    IpAddress.fromString("100.100.100.500") shouldBe None
    IpAddress.fromJsValue(JsString("1.1.1.1")) shouldBe Some(IpAddress("1.1.1.1"))
  }

  test(s"${MusicPower.name} validity") {
    MusicPower.fromString("0") shouldBe Some(MusicOff)
    MusicPower.fromString("1") shouldBe Some(MusicOn)
    MusicPower.fromString("2") shouldBe None
    MusicPower.fromString("on") shouldBe None
    MusicPower.fromJsValue(JsNumber(1)) shouldBe Some(MusicOn)
    MusicPower.fromJsValue(JsString("off")) shouldBe None
  }

  test(s"${Name.name} validity") {
    Name("").isValid shouldBe true
    Name("test").isValid shouldBe true
    Name("any").isValid shouldBe true
  }

  test(s"${Percent.name} validity") {
    Percent(-101).isValid shouldBe false
    Percent(-100).isValid shouldBe true
    Percent(0).isValid shouldBe true
    Percent(100).isValid shouldBe true
    Percent(101).isValid shouldBe false
    Percent(200).isValid shouldBe false
    Percent.fromString("50") shouldBe Some(Percent(50))
    Percent.fromString("-80") shouldBe Some(Percent(-80))
    Percent.fromJsValue(JsString("80")) shouldBe None
    Percent.fromJsValue(JsNumber(12)) shouldBe Some(Percent(12))
  }

  test(s"${Port.name} validity") {
    Port(-100).isValid shouldBe false
    Port(0).isValid shouldBe true
    Port(7000).isValid shouldBe true
    Port(65535).isValid shouldBe true
    Port(65536).isValid shouldBe false
    Port.fromString("8909") shouldBe Some(Port(8909))
    Port.fromString("165532") shouldBe None
    Port.fromJsValue(JsString("80")) shouldBe None
    Port.fromJsValue(JsNumber(1222)) shouldBe Some(Port(1222))
  }

  test(s"${Power.name} validity") {
    Power.fromString("on") shouldBe Some(PowerOn)
    Power.fromString("off") shouldBe Some(PowerOff)
    Power.fromString("0") shouldBe None
    Power.fromString("1") shouldBe None
    Power.fromJsValue(JsNumber(1)) shouldBe None
    Power.fromJsValue(JsString("off")) shouldBe Some(PowerOff)
  }

  test(s"${PowerMode.name} validity") {
    PowerMode.fromString("0") shouldBe Some(PowerModeNormal)
    PowerMode.fromString("1") shouldBe Some(PowerModeTemperature)
    PowerMode.fromString("2") shouldBe Some(PowerModeRgb)
    PowerMode.fromString("3") shouldBe Some(PowerModeHsv)
    PowerMode.fromString("4") shouldBe Some(PowerModeFlow)
    PowerMode.fromString("5") shouldBe Some(PowerModeNight)
    PowerMode.fromString("normal") shouldBe None
    PowerMode.fromString("rgb") shouldBe None
    PowerMode.fromJsValue(JsString("5")) shouldBe None
    PowerMode.fromJsValue(JsNumber(1)) shouldBe Some(PowerModeTemperature)
  }

  test(s"${PropertyName.name} validity") {
    PropertyName(Power.propFgName).isValid shouldBe true
    PropertyName(Brightness.propBgName).isValid shouldBe true
    PropertyName(ColorMode.propBgName).isValid shouldBe true
    PropertyName(TimerValue.propFgName).isValid shouldBe true
  }

  test(s"${Rgb.name} validity") {
    Rgb(-30).isValid shouldBe false
    Rgb(0).isValid shouldBe false
    Rgb(1).isValid shouldBe true
    Rgb(3000).isValid shouldBe true
    Rgb(65539).isValid shouldBe true
    Rgb(16777215).isValid shouldBe true
    Rgb(16777216).isValid shouldBe false
    Rgb.fromString("50") shouldBe Some(Rgb(50))
    Rgb.fromString("red") shouldBe Some(Rgb.red)
    Rgb.fromJsValue(JsString("lime")) shouldBe Some(Rgb.lime)
    Rgb.fromJsValue(JsString("Blue")) shouldBe None
    Rgb.fromJsValue(JsNumber(45000)) shouldBe Some(Rgb(45000))
    Rgb.fromJsValue(JsNumber(0)) shouldBe None
  }

  test(s"${Saturation.name} validity") {
    Saturation(-30).isValid shouldBe false
    Saturation(0).isValid shouldBe true
    Saturation(1).isValid shouldBe true
    Saturation(55).isValid shouldBe true
    Saturation(100).isValid shouldBe true
    Saturation(101).isValid shouldBe false
    Saturation(9999).isValid shouldBe false
    Saturation.fromString("50") shouldBe Some(Saturation(50))
    Saturation.fromString("120") shouldBe None
    Saturation.fromJsValue(JsString("10")) shouldBe None
    Saturation.fromJsValue(JsNumber(12)) shouldBe Some(Saturation(12))
  }

  test(s"${SceneClass.name} validity") {
    SceneClass.fromString("color") shouldBe Some(SceneClassRgb)
    SceneClass.fromString("hsv") shouldBe Some(SceneClassHsv)
    SceneClass.fromString("ct") shouldBe Some(SceneClassTemperature)
    SceneClass.fromString("cf") shouldBe Some(SceneClassFlow)
    SceneClass.fromString("auto_delay_off") shouldBe Some(SceneClassDelayOff)
    SceneClass.fromJsValue(JsNumber(2)) shouldBe None
    SceneClass.fromJsValue(JsString("ct")) shouldBe Some(SceneClassTemperature)
  }

  test(s"${Temperature.name} validity") {
    Temperature(-30).isValid shouldBe false
    Temperature(1699).isValid shouldBe false
    Temperature(1700).isValid shouldBe true
    Temperature(5000).isValid shouldBe true
    Temperature(6500).isValid shouldBe true
    Temperature(6501).isValid shouldBe false
    Temperature(9999).isValid shouldBe false
    Temperature.fromString("4000") shouldBe Some(Temperature(4000))
    Temperature.fromString("120") shouldBe None
    Temperature.fromJsValue(JsString("10")) shouldBe None
    Temperature.fromJsValue(JsString("4444")) shouldBe None
    Temperature.fromJsValue(JsNumber(6234)) shouldBe Some(Temperature(6234))
  }

  test(s"${TimerType.name} validity") {
    TimerType.default.isValid shouldBe true
    TimerType.fromString("0") shouldBe Some(TimerType.default)
    TimerType.fromString("1") shouldBe None
    TimerType.fromJsValue(JsString("default")) shouldBe None
    TimerType.fromJsValue(JsNumber(0)) shouldBe Some(TimerType.default)
  }

  test(s"${TimerValue.name} validity") {
    TimerValue(-30).isValid shouldBe false
    TimerValue(0).isValid shouldBe true
    TimerValue(25).isValid shouldBe true
    TimerValue(59).isValid shouldBe true
    TimerValue(60).isValid shouldBe true
    TimerValue(61).isValid shouldBe false
    TimerValue(9999).isValid shouldBe false
    TimerValue.fromString("4") shouldBe Some(TimerValue(4))
    TimerValue.fromString("120") shouldBe None
    TimerValue.fromJsValue(JsString("10")) shouldBe None
    TimerValue.fromJsValue(JsString("4444")) shouldBe None
    TimerValue.fromJsValue(JsNumber(23)) shouldBe Some(TimerValue(23))
  }

}
