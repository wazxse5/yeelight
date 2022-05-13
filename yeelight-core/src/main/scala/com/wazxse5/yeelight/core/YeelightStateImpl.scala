package com.wazxse5.yeelight.core

import javafx.beans.property.{SimpleBooleanProperty, SimpleIntegerProperty, SimpleStringProperty}

class YeelightStateImpl extends YeelightState {
  
  val isConnectedProperty = new SimpleBooleanProperty()
  
  val powerProperty = new SimpleStringProperty()
  
  val brightnessProperty = new SimpleIntegerProperty()

  val temperatureProperty = new SimpleIntegerProperty()

}
