package com.wazxse5.yeelight.testkit

import com.wazxse5.yeelight.connection.ConnectionAdapter
import com.wazxse5.yeelight.core.YeelightServiceImpl

class YeelightServiceFake extends YeelightServiceImpl {

  override val connectionAdapter: ConnectionAdapter = FakeConnectionAdapter(this)

}
