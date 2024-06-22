package com.smesitejl;

import com.smesitejl.entitys.HistoryTableRaw;
import com.smesitejl.entitys.TaskTableRaw;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;

public class DataKeeper {
    private static DataKeeper instance;

    @Getter
    private  ObservableList<TaskTableRaw> taskTaskTableRaws = FXCollections.observableArrayList();
    @Getter
    private  ObservableList<HistoryTableRaw> historyTableRaws = FXCollections.observableArrayList();

    public static DataKeeper getInstance() {
        if (instance == null) {
            instance = new DataKeeper();
        }
        return instance;
    }

    private DataKeeper(){
        ;
    }
    public  void addTaskTableRaw(TaskTableRaw raw){
        taskTaskTableRaws.add(raw);
    }
    public  void addHistoryTableRaw(HistoryTableRaw raw){
        historyTableRaws.add(raw);
    }
    public  void removeTaskTableRaw(TaskTableRaw raw){
        taskTaskTableRaws.remove(raw);
    }
    public  void removeHistoryTableRaw(HistoryTableRaw raw){
        historyTableRaws.remove(raw);
    }
}
