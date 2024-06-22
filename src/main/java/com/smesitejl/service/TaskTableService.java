package com.smesitejl.service;

import com.smesitejl.entitys.HistoryTableRaw;
import javafx.scene.control.TableView;

public class TaskTableService {
    private TableView<HistoryTableRaw> historyTable;

    public TaskTableService (TableView<HistoryTableRaw> historyTable){
        this.historyTable = historyTable;
    }
    private void addHistoryTableRaw(String text, String time, String date){

    }

    private void addHistoryTableRaw(String text, String time){

    }

    private void removeHistoryTableRaw(){

    }
}
