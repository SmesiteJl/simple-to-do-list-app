package com.smesitejl.service;

import com.smesitejl.DataKeeper;
import com.smesitejl.controller.Controller;
import com.smesitejl.entitys.HistoryTableRaw;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HistoryTableService {
    public void addHistoryTableRaw(String text, String time, String date){
        HistoryTableRaw raw = new HistoryTableRaw();
        raw.getText().setText(text);
        //time label text
        raw.getTime().setText(time);
        //date label text
        raw.getDate().setText(date);
        //delete button onAction and onMouseEntered/Exited
        raw.getDelete().setOnAction(actionEvent ->{
            Controller.getInstance().removeHistoryTableRaw(raw);
        });
        raw.getDelete().setOnMouseEntered(action -> raw.getDelete().setStyle("-fx-background-image: url(icons/hisBin.gif)"));
        raw.getDelete().setOnMouseExited(action -> raw.getDelete().setStyle("-fx-background-image: url(icons/hisBin.png)"));
        DataKeeper.getInstance().addHistoryTableRaw(raw);
    }

    public void addHistoryTableRaw(String text, String time){
        HistoryTableRaw raw = new HistoryTableRaw();
        //time label text
        raw.getText().setText(text);
        //time label text
        raw.getTime().setText(time);
        //date auto filling
        raw.getDate().setText(new SimpleDateFormat("dd.MM.yy HH:mm").format(new Date()));
        //delete button onAction and onMouseEntered/Exited
        raw.getDelete().setOnAction(actionEvent ->{
            Controller.getInstance().removeHistoryTableRaw(raw);
        });
        raw.getDelete().setOnMouseEntered(action -> raw.getDelete().setStyle("-fx-background-image: url(icons/hisBin.gif)"));
        raw.getDelete().setOnMouseExited(action -> raw.getDelete().setStyle("-fx-background-image: url(icons/hisBin.png)"));
        DataKeeper.getInstance().addHistoryTableRaw(raw);
    }

    public void removeHistoryTableRaw(HistoryTableRaw raw){
        DataKeeper.getInstance().removeHistoryTableRaw(raw);
        Controller.getInstance().displayHistoryTable();
    }
}
