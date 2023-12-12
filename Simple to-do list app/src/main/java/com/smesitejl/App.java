package com.smesitejl;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("app.fxml"));
        stage.setTitle("T0-D0");
        stage.setScene(new Scene(root,480, 720));
        stage.setMinWidth(480);
        stage.setMinHeight(720);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
