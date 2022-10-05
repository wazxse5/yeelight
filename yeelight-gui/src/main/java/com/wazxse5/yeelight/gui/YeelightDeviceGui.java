package com.wazxse5.yeelight.gui;

import com.wazxse5.yeelight.api.YeelightDevice;
import com.wazxse5.yeelight.api.YeelightState;
import com.wazxse5.yeelight.api.command.YeelightCommand;
import com.wazxse5.yeelight.api.valuetype.DeviceModel;
import com.wazxse5.yeelight.gui.data.YeelightDeviceAppData;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class YeelightDeviceGui {
    private final YeelightDevice yeelightDevice;
    private final YeelightStateGui yeelightStateGui;

    public final StringProperty guiNameProperty;

    public YeelightDeviceGui(YeelightDevice yeelightDevice, YeelightDeviceAppData initialAppData) {
        this.yeelightDevice = yeelightDevice;
        this.yeelightStateGui = new YeelightStateGui(yeelightDevice.state());

        String guiName = initialAppData != null ? initialAppData.guiName() : yeelightDevice.deviceId();
        this.guiNameProperty = new SimpleStringProperty(guiName);
    }

    public String deviceId() {
        return yeelightDevice.deviceId();
    }

    public DeviceModel model() {
        return yeelightDevice.model();
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
