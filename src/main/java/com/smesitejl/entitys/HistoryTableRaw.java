package com.smesitejl.entitys;

import com.smesitejl.service.ContentDisplayService;
import com.smesitejl.DataKeeper;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import lombok.Getter;

import java.text.SimpleDateFormat;
import java.util.Date;


public class HistoryTableRaw {
    @Getter
    private Label text;
    @Getter
    private Label time;
    @Getter
    private Label date;
    @Getter
    private Button delete;
    private final ContentDisplayService contentDisplayService = new ContentDisplayService();


    //constrictor for reading from json
    public HistoryTableRaw(String text, String time, String date, TableView<HistoryTableRaw> historyTable){
        //time label text
        this.text = new Label();
        this.text.setText(text);
        //time label text
        this.time = new Label();
        this.time.setText(time);
        //date label text
        this.date = new Label();
        this.date.setText(date);
        //delete button onAction and onMouseEntered/Exited
        this.delete = new Button();
        this.delete.setOnAction(actionEvent ->{
            DataKeeper.removeHistoryTableRaw(this);
            contentDisplayService.displayHistoryTable(historyTable);
        });
        this.delete.setOnMouseEntered(action -> this.delete.setStyle("-fx-background-image: url(icons/hisBin.gif)"));
        this.delete.setOnMouseExited(action -> this.delete.setStyle("-fx-background-image: url(icons/hisBin.png)"));
    }
    //constructor for creating a new raw in history table by button
    public HistoryTableRaw(String text, String time, TableView<HistoryTableRaw> historyTable){
        //time label text
        this.text = new Label();
        this.text.setText(text);
        //time label text
        this.time = new Label();
        this.time.setText(time);
        //date auto filling
        this.date = new Label();
        this.date.setText(new SimpleDateFormat("dd.MM.yy HH:mm").format(new Date()));
        //delete button onAction and onMouseEntered/Exited
        this.delete = new Button();
        this.delete.setOnAction(actionEvent ->{
            DataKeeper.removeHistoryTableRaw(this);
            contentDisplayService.displayHistoryTable(historyTable);
        });
        this.delete.setOnMouseEntered(action -> this.delete.setStyle("-fx-background-image: url(icons/hisBin.gif)"));
        this.delete.setOnMouseExited(action -> this.delete.setStyle("-fx-background-image: url(icons/hisBin.png)"));

    }
}
