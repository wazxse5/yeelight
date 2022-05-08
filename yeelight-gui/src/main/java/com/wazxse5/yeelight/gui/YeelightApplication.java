package com.wazxse5.yeelight.gui;

import com.wazxse5.yeelight.core.Logger;
import com.wazxse5.yeelight.core.YeelightService;
import com.wazxse5.yeelight.core.YeelightServiceImpl;
import com.wazxse5.yeelight.gui.controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class YeelightApplication extends Application {

    private final YeelightService yeelightService = new YeelightServiceImpl();

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/fxml/Main.fxml"));
            Parent parent = fxmlLoader.load();
            MainController mainController = fxmlLoader.getController();
            mainController.setYeelightService(yeelightService);

            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.setTitle("Yeelight");
            stage.show();
        } catch (IOException e) {
            Logger.error("Cannot load Main.fxml file: " + e.getMessage());
        }
    }

    @Override
    public void stop() {
        yeelightService.exit();
    }
}