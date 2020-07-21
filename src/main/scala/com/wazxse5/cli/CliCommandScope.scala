package com.wazxse5.cli

sealed trait CliCommandScope

case object App extends CliCommandScope

case object Service extends CliCommandScope

case object Device extends CliCommandScope

case object AllDevices extends CliCommandScope
