package com.wazxse5.cli

object CliCommands {
  object app {
    val exit = "exit"
    val _commands = Seq(exit)
  }

  object service {
    val deviceOf = "deviceof"
    val devices = "devices"
    val discover = "discover"
    val listen = "listen"
    val listenOn = "on"
    val listenOff = "off"
    val _commands = Seq(deviceOf, devices, discover, listen)
  }

  val allDevices_ = "all"

  object device {

    object increase {
      val brightness = valueTypes.brightness
      val temperature = valueTypes.temperature
      val _commands = Seq(brightness, temperature)
    }

    object decrease {
      val brightness = valueTypes.brightness
      val temperature = valueTypes.temperature
      val _commands = Seq(brightness, temperature)
    }

    object get {
      val alias = valueTypes.alias
      val brightness = valueTypes.brightness
      val color = valueTypes.color
      val colorMode = valueTypes.colorMode
      val hue = valueTypes.hue
      val ip = valueTypes.ip
      val music = valueTypes.music
      val name = valueTypes.name
      val port = valueTypes.port
      val power = valueTypes.power
      val saturation = valueTypes.saturation
      val temperature = valueTypes.temperature
      val timer = valueTypes.timer
      val _commands = Seq(alias, brightness, color, colorMode, hue, ip, music, name, port, power, saturation, temperature, timer)
    }

    object set {
      val alias = valueTypes.alias
      val brightness = valueTypes.brightness
      val color = valueTypes.color
      val colorMode = valueTypes.colorMode
      val hsv = "hsv"
      val ip = valueTypes.ip
      val music = valueTypes.music
      val name = valueTypes.name
      val port = valueTypes.port
      val power = valueTypes.power
      val temperature = valueTypes.temperature
      val timer = valueTypes.timer
      val on = "on"
      val off = "off"
      val _commands = Seq(alias, brightness, color, colorMode, hsv, ip, music, name, port, power, temperature, timer, on, off)
    }

    val decrease_ = "decrease"
    val increase_ = "increase"
    val get_ = "get"
    val set_ = "set"
    val refresh = "refresh"
    val state = "state"
    val toggle = "toggle"

    val _commands = Seq(decrease_, increase_, get_, set_, set.on, set.off, refresh, state, toggle)
    val _keywords: Seq[String] = increase._commands :++ decrease._commands :++ get._commands :++ set._commands
  }

  object valueTypes {
    val alias = "alias"
    val brightness = "brightness"
    val color = "color"
    val colorMode = "color mode"
    val hue = "hue"
    val ip = "ip"
    val model = "model"
    val music = "music"
    val name = "name"
    val port = "port"
    val power = "power"
    val saturation = "saturation"
    val temperature = "temperature"
    val timer = "timer"
  }

  object message {
    val emptyCommand = "emptyCommand command"
    val unknownCommand = "unknown command"
    val unsupportedCommand = "unsupported command"
    val unknownDevice = "unknown device"
    val aliasReservedKeyword = "alias is reserved keyword"
    val aliasTaken = "alias is taken"
  }

  val _keywords: Seq[String] = app._commands :++ service._commands :++ device._commands :++ device._keywords

}
