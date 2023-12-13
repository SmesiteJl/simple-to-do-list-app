package com.smesitejl;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("app.fxml"));
        Scene scene = new Scene(root, 640, 720);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("styles.css").toExternalForm());
        Image ico = new Image("icons/logo.png");
        stage.getIcons().add(ico);
        stage.setScene(scene);
        stage.setMinWidth(480);
        stage.setMinHeight(720);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
