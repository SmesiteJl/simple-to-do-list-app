package com.smesitejl.service.mainstageservice;

import com.smesitejl.entitys.mainstageentitys.HistoryTableRow;
import com.smesitejl.repository.DataKeeper;
import com.smesitejl.controller.Controller;
import com.smesitejl.repository.StyleProvider;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HistoryTableService {
    public void addHistoryTableRow(String text, String time, String date){
        HistoryTableRow row = getDefaultHistoryRow();
        row.getText().setText(text);
        row.getTime().setText(time);
        row.getDate().setText(date);
        DataKeeper.getInstance().addHistoryTableRow(row);

    }

    public void addHistoryTableRow(String text, String time){
        HistoryTableRow row = getDefaultHistoryRow();
        row.getText().setText(text);
        row.getTime().setText(time);
        DataKeeper.getInstance().addHistoryTableRow(row);
    }

    public void removeHistoryTableRow(HistoryTableRow row){
        DataKeeper.getInstance().removeHistoryTableRow(row);
        Controller.getInstance().displayHistoryTable();
    }

    private HistoryTableRow getDefaultHistoryRow(){
        HistoryTableRow row = new HistoryTableRow();
        row.getDate().setText(new SimpleDateFormat("dd.MM.yy HH:mm").format(new Date()));
        //delete button onAction and onMouseEntered/Exited
        row.getDelete().setOnAction(actionEvent -> Controller.getInstance().removeHistoryTableRow(row));
        StyleProvider.getInstance().getHistoryDelButtonStyle(row.getDelete());
        return row;
    }
}
