package com.smesitejl;

import com.smesitejl.controller.Controller;
import com.smesitejl.controller.WidgetController;
import com.smesitejl.service.mainstageservice.DataTransferService;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.util.Objects;

public class App extends Application {
    private final DataTransferService dtc = new DataTransferService();

    @Override
    public void start(Stage stage) throws Exception{
        /*main window*/
        long openRequestTime =  System.currentTimeMillis();
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("app.fxml")));
        Controller controller = Controller.getInstance();
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root, 1280, 720);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getClassLoader().getResource("styles.css")).toExternalForm());
        Controller.getInstance().setMainStage(stage);
        WidgetController.getInstance().setMainStage(stage);
        stage.getIcons().add(new Image("icons/logo.png"));
        stage.initStyle(StageStyle.UNIFIED);
        stage.setScene(scene);
        stage.setMinWidth(600);
        stage.setMinHeight(400);
        stage.setOnCloseRequest(event -> {
            dtc.unloadHistory();
            dtc.unloadCurrentTasks();
            Platform.exit();
            System.exit(0);
        });
        stage.show();

        /*widget*/
        FXMLLoader widgetLoader = new FXMLLoader(Objects.requireNonNull(getClass().getClassLoader().getResource("widget.fxml")));
        WidgetController widgetController = WidgetController.getInstance();
        widgetLoader.setController(widgetController);
        AnchorPane page = widgetLoader.load();
        Stage widgetStage = new Stage();
        Scene widgetScene = new Scene(page);
        widgetScene.getStylesheets().add(Objects.requireNonNull(getClass().getClassLoader().getResource("widgetStyles.css")).toExternalForm());
        widgetScene.setFill(Color.TRANSPARENT);
        widgetStage.setScene(widgetScene);
        widgetStage.getIcons().add(new Image("icons/logo.png"));
        widgetStage.initStyle(StageStyle.TRANSPARENT);
        widgetStage.setAlwaysOnTop(true);
        WidgetController.getInstance().setWidgetStage(widgetStage);
        Controller.getInstance().setWidgetStage(widgetStage);
        System.out.println("Application started for: " + (System.currentTimeMillis() - openRequestTime) + " millis");
    }

    public static void main(String[] args) {
        launch();
    }
}
