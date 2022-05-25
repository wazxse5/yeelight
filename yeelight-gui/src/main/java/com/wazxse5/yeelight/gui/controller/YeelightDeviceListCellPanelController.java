package com.wazxse5.yeelight.gui.controller;

import com.wazxse5.yeelight.api.command.Toggle$;
import com.wazxse5.yeelight.gui.YeelightDeviceGui;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class YeelightDeviceListCellPanelController {
    private YeelightDeviceGui device;
    private static final String styleButtonOn = "{ -fx-font-weight: bold; -fx-background-color: #f0e443; -fx-background-radius: 20; }";
    private static final String styleButtonOff = "{ -fx-background-color: #ffffff; -fx-background-radius: 20; }";

    @FXML private Label lDeviceName;
    @FXML private Button bToggle;


    public void setDevice(YeelightDeviceGui device) {
        this.device = device;
        this.lDeviceName.textProperty().bind(device.guiNameProperty);
        this.bToggle.disableProperty().bind(device.state().isConnectedProperty().not());
        this.bToggle.styleProperty().bind(
            Bindings.when(device.state().isOnProperty())
                .then(styleButtonOn)
                .otherwise(styleButtonOff)
        );
    }

    public void toggle() {
        device.performCommand(Toggle$.MODULE$);
    }

}
