package com.smesitejl;

import com.smesitejl.DataTransferLayer.DataTransferClass;
import com.smesitejl.controllers.Controller;
import com.smesitejl.controllers.TimerTaskController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class App extends Application {
    private final DataTransferClass dtc = new DataTransferClass(Controller.getTableRaws(), Controller.getHistoryTableRaws());
    private final List<TimerTaskController> timerControls = new ArrayList<>();
    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("app.fxml"));
        Scene scene = new Scene(root, 1280, 720);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("styles.css").toExternalForm());
        Image ico = new Image("icons/logo.png");
        stage.getIcons().add(ico);
        stage.setScene(scene);
        stage.setMinWidth(480);
        stage.setMinHeight(720);
        stage.show();

        stage.setOnCloseRequest(event -> {
            for (TimerTaskController timerControl : timerControls) {
                timerControl.cancel();
            }
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
