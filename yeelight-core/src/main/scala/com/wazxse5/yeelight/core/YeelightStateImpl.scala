package com.wazxse5.yeelight.core

import javafx.beans.property.{SimpleBooleanProperty, SimpleIntegerProperty}

class YeelightStateImpl extends YeelightState {
  
  val isConnectedProperty = new SimpleBooleanProperty()
  
  val brightnessProperty = new SimpleIntegerProperty()

  val temperatureProperty = new SimpleIntegerProperty()

}
