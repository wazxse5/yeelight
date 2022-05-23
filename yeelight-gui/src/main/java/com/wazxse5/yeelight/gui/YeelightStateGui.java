package com.wazxse5.yeelight.gui;

import com.wazxse5.yeelight.api.YeelightState;
import javafx.beans.property.*;

public class YeelightStateGui {

    private final SimpleBooleanProperty _isConnectedProperty;
    private final SimpleStringProperty _powerProperty;
    private final SimpleIntegerProperty _brightnessProperty;
    private final SimpleIntegerProperty _temperatureProperty;

    public YeelightStateGui(YeelightState yeelightState) {
        _isConnectedProperty = new SimpleBooleanProperty(yeelightState.isConnected());
        _powerProperty = new SimpleStringProperty(yeelightState.power().value());
        _brightnessProperty = new SimpleIntegerProperty(yeelightState.brightness().value());
        _temperatureProperty = new SimpleIntegerProperty(yeelightState.temperature().value());
    }

    public ReadOnlyBooleanProperty isConnectedProperty() {
        return _isConnectedProperty;
    }

    public ReadOnlyStringProperty powerProperty() {
        return _powerProperty;
    }

    public ReadOnlyIntegerProperty brightnessProperty() {
        return _brightnessProperty;
    }

    public ReadOnlyIntegerProperty temperatureProperty() {
        return _temperatureProperty;
    }

    public void update(YeelightState newState) {
        _isConnectedProperty.set(newState.isConnected());
        _powerProperty.set(newState.power().value());
        _brightnessProperty.set(newState.brightness().value());
        _temperatureProperty.set(newState.temperature().value());
    }
}
