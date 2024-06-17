package com.smesitejl;

import com.smesitejl.service.DataTransferService.DataTransferImpl;
import com.smesitejl.controller.MainController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


import java.util.Objects;

public class App extends Application {
    private final DataTransferImpl dtc = new DataTransferImpl(MainController.getTableRaws(), MainController.getHistoryTableRaws());

    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("app.fxml")));
        Scene scene = new Scene(root, 1280, 720);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getClassLoader().getResource("styles.css")).toExternalForm());
        Image ico = new Image("icons/logo.png");
        stage.getIcons().add(ico);
        stage.setScene(scene);
        stage.setMinWidth(480);
        stage.setMinHeight(720);
        stage.show();

        stage.setOnCloseRequest(event -> {
            dtc.unloadHistory();
            dtc.unloadCurrentTasks();
            Platform.exit();
            System.exit(0);
        });
    }

    public static void main(String[] args) {
        launch();
    }
}
