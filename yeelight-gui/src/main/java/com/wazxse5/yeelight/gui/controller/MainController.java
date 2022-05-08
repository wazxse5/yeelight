package com.wazxse5.yeelight.gui.controller;

import com.wazxse5.yeelight.core.YeelightService;
import com.wazxse5.yeelight.core.command.Toggle$;
import javafx.fxml.FXML;

public class MainController {
    private YeelightService yeelightService;

    public void setYeelightService(YeelightService yeelightService) {
        this.yeelightService = yeelightService;
    }

    @FXML public void search() {
        yeelightService.search();
    }

    @FXML public void toggle() {
        yeelightService.devicesJava().forEach(yeelightDevice -> yeelightDevice.performCommand(Toggle$.MODULE$));
    }

}
