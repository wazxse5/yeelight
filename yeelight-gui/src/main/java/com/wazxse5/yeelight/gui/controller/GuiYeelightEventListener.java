package com.wazxse5.yeelight.gui.controller;

import com.wazxse5.yeelight.api.*;
import com.wazxse5.yeelight.core.util.Logger;
import com.wazxse5.yeelight.gui.YeelightDeviceGui;
import com.wazxse5.yeelight.gui.data.YeelightKnownDeviceGui;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

class GuiYeelightEventListener implements YeelightEventListener {
    private final MainPanelController mainController;

    public GuiYeelightEventListener(MainPanelController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void onAction(YeelightEvent event) {
        if (event instanceof DeviceAdded) {
            if (mainController.devicesList.stream().noneMatch(p -> p.deviceId().equals(event.deviceId()))) {
                YeelightDevice addedDevice = mainController.yeelightService.devicesJava().get(event.deviceId());
                YeelightKnownDeviceGui deviceAppData = mainController.initialAppData.devicesJava().stream().filter(d -> d.deviceId().equals(event.deviceId())).findAny().orElse(null);
                YeelightDeviceGui addedDeviceGui = new YeelightDeviceGui(addedDevice, deviceAppData);

                AnchorPane devicePane = loadDevicePane(addedDeviceGui);
                devicePane.visibleProperty().bind(mainController.lvDevices.getSelectionModel().selectedItemProperty().isEqualTo(addedDeviceGui));

                Platform.runLater(() -> {
                    mainController.devicesList.add(addedDeviceGui);
                    mainController.devicePane.getChildren().add(devicePane);
                });
            }
        } else if (event instanceof DeviceUpdated) {
            Platform.runLater(() -> mainController.devicesList
                .filtered(device -> device.deviceId().equals(event.deviceId()))
                .forEach(YeelightDeviceGui::refresh));
        }
    }

    private AnchorPane loadDevicePane(YeelightDeviceGui addedDevice) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/fxml/YeelightDeviceDetailsPanel.fxml"));
            AnchorPane pane = fxmlLoader.load();
            YeelightDeviceDetailsPanelController detailsPanelController = fxmlLoader.getController();
            detailsPanelController.setDevice(addedDevice);

            AnchorPane.setLeftAnchor(pane, 0.0);
            AnchorPane.setRightAnchor(pane, 0.0);
            AnchorPane.setBottomAnchor(pane, 50.0);

            return pane;
        } catch (IOException e) {
            Logger.error("Cannot load YeelightDeviceDetailsPanel.fxml file: " + e.getMessage());
            return new AnchorPane();
        }
    }
}
