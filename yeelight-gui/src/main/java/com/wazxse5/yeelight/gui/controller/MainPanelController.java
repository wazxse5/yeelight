package com.wazxse5.yeelight.gui.controller;

import com.wazxse5.yeelight.api.YeelightService;
import com.wazxse5.yeelight.core.util.Logger;
import com.wazxse5.yeelight.gui.YeelightDeviceGui;
import com.wazxse5.yeelight.gui.YeelightGuiAppData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class MainPanelController {

    YeelightGuiAppData initialAppData;
    YeelightService yeelightService;
    ObservableList<YeelightDeviceGui> devicesList = FXCollections.observableArrayList();

    @FXML ListView<YeelightDeviceGui> lvDevices;
    @FXML AnchorPane devicePane;

    public void initialize(YeelightService yeelightService, YeelightGuiAppData initialAppData) {
        this.yeelightService = yeelightService;
        this.initialAppData = initialAppData;
        this.lvDevices.setItems(devicesList);
        this.lvDevices.setCellFactory(new YeelightDeviceGuiCellFactory());
        this.yeelightService.addEventListener(new GuiYeelightEventListener(this));
        this.yeelightService.start();
    }

    public Map<String, String> getDevicesGuiNames() {
        return devicesList.stream()
            .filter(d -> !Objects.equals(d.deviceId(), d.guiName()))
            .collect(Collectors.toMap(YeelightDeviceGui::deviceId, YeelightDeviceGui::guiName));
    }

    static class YeelightDeviceGuiCellFactory implements Callback<ListView<YeelightDeviceGui>, ListCell<YeelightDeviceGui>> {
        @Override
        public ListCell<YeelightDeviceGui> call(ListView<YeelightDeviceGui> param) {
            return new ListCell<>() {
                @Override
                public void updateItem(YeelightDeviceGui device, boolean empty) {
                    super.updateItem(device, empty);
                    if (empty || device == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/fxml/YeelightDeviceListCellPanel.fxml"));
                            HBox hBox = fxmlLoader.load();
                            YeelightDeviceListCellPanelController mainController = fxmlLoader.getController();
                            mainController.setDevice(device);
                            setGraphic(hBox);
                        } catch (IOException e) {
                            Logger.error("Cannot load YeelightDeviceListCellPanel.fxml file: " + e.getMessage());
                            setText(device.deviceId());
                        }

                    }
                }
            };
        }
    }

    @FXML public void search() {
        yeelightService.search();
    }

}
