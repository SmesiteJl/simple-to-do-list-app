package com.smesitejl.repository;

import com.smesitejl.entitys.HistoryTableRow;
import com.smesitejl.entitys.TaskTableRow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;

public class DataKeeper {
    private static DataKeeper instance;

    @Getter
    private  ObservableList<TaskTableRow> taskTaskTableRows = FXCollections.observableArrayList();
    @Getter
    private  ObservableList<HistoryTableRow> historyTableRows = FXCollections.observableArrayList();

    public static DataKeeper getInstance() {
        if (instance == null) {
            instance = new DataKeeper();
        }
        return instance;
    }

    private DataKeeper(){
    }
    public  void addTaskTableRow(TaskTableRow row){
        taskTaskTableRows.add(row);
    }
    public  void addHistoryTableRow(HistoryTableRow row){
        historyTableRows.add(row);
    }
    public  void removeTaskTableRow(TaskTableRow row){
        taskTaskTableRows.remove(row);
    }
    public  void removeHistoryTableRow(HistoryTableRow row){
        historyTableRows.remove(row);
    }
}
