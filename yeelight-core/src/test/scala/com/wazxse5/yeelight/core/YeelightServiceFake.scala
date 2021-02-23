package com.wazxse5.yeelight.core

import com.wazxse5.yeelight.connection.{ConnectionAdapter, FakeConnectionAdapter}

class YeelightServiceFake extends YeelightServiceImpl {

  override val connectionAdapter: ConnectionAdapter = FakeConnectionAdapter(this)

}
