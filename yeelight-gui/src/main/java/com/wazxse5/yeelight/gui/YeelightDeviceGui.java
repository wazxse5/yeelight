package com.wazxse5.yeelight.gui;

import com.wazxse5.yeelight.api.YeelightDevice;
import com.wazxse5.yeelight.api.YeelightState;
import com.wazxse5.yeelight.api.command.YeelightCommand;
import com.wazxse5.yeelight.api.valuetype.DeviceModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.List;

public class YeelightDeviceGui {
    private final YeelightDevice yeelightDevice;
    private final YeelightStateGui yeelightStateGui;

    public final StringProperty guiNameProperty;

    public YeelightDeviceGui(YeelightDevice yeelightDevice) {
        this.yeelightDevice = yeelightDevice;
        this.yeelightStateGui = new YeelightStateGui(yeelightDevice.state());
        this.guiNameProperty = new SimpleStringProperty(yeelightDevice.deviceId());
    }

    public String deviceId() {
        return yeelightDevice.deviceId();
    }

    public DeviceModel model() {
        return yeelightDevice.model();
    }

    public String firmwareVersion() {
        return yeelightDevice.firmwareVersion();
    }

    public List<String> supportedCommands() {
        return yeelightDevice.supportedCommandsJava();
    }

    public YeelightStateGui state() {
        return yeelightStateGui;
    }

    public void performCommand(YeelightCommand command) {
        yeelightDevice.performCommand(command);
    }

    public void refresh() {
        YeelightState newState = yeelightDevice.state();
        this.yeelightStateGui.update(newState);
    }


}
