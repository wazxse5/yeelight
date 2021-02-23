package com.wazxse5.yeelight.core

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

class YeelightServiceTest extends AnyFunSuite with Matchers {

  test("discovery") {
    val service = new YeelightServiceFake

    service.devices.isEmpty shouldBe true

    service.search()

    service.devices.size shouldBe 2
  }

}
