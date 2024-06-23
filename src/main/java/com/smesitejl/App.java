package com.smesitejl;

import com.smesitejl.controller.Controller;
import com.smesitejl.service.DataTransferService;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


import java.util.Objects;

public class App extends Application {
    private final DataTransferService dtc = new DataTransferService();

    @Override
    public void start(Stage stage) throws Exception{
        long openRequestTime =  System.currentTimeMillis();
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("app.fxml")));
        Controller controller = Controller.getInstance();
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root, 1280, 720);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getClassLoader().getResource("styles.css")).toExternalForm());
        Image ico = new Image("icons/logo.png");
        stage.getIcons().add(ico);
        stage.setScene(scene);
        stage.setMinWidth(480);
        stage.setMinHeight(720);
        stage.setOnCloseRequest(event -> {
            dtc.unloadHistory();
            dtc.unloadCurrentTasks();
            Platform.exit();
            System.exit(0);
        });
        stage.show();
        long showTime =  System.currentTimeMillis();
        System.out.println("Application started for: " + (showTime - openRequestTime) + " millis");
    }

    public static void main(String[] args) {
        launch();
    }
}
