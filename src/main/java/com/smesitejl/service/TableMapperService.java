package com.smesitejl.service;


import com.smesitejl.entitys.HistoryTableRaw;
import com.smesitejl.entitys.TaskTableRaw;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class TableMapperService {
    public void doTaskTableMapping(TableView<TaskTableRaw> table,
                                   TableColumn<TaskTableRaw, Button> toHistoryColoumn,
                                   TableColumn<TaskTableRaw, CheckBox> checkColoumn,
                                   TableColumn<TaskTableRaw, TextField> textColoumn,
                                   TableColumn<TaskTableRaw, TextField> timeColoumn,
                                   TableColumn<TaskTableRaw, Button> startupColoumn,
                                   TableColumn<TaskTableRaw, Button> delColoumn){
        table.setSelectionModel(null);
        toHistoryColoumn.setCellValueFactory(new PropertyValueFactory<>("history"));
        checkColoumn.setCellValueFactory(new PropertyValueFactory<>("to"));
        textColoumn.setCellValueFactory(new PropertyValueFactory<>("text"));
        timeColoumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        startupColoumn.setCellValueFactory(new PropertyValueFactory<>("startup"));
        delColoumn.setCellValueFactory(new PropertyValueFactory<>("del"));
        //columns wight
        toHistoryColoumn.prefWidthProperty().bind(table.widthProperty().divide(24)); //1/24
        checkColoumn.prefWidthProperty().bind(table.widthProperty().divide(24)); //1/24
        textColoumn.prefWidthProperty().bind(table.widthProperty().divide(1.5)); //16/24
        timeColoumn.prefWidthProperty().bind((table.widthProperty().divide(12))); //4/24
        startupColoumn.prefWidthProperty().bind(table.widthProperty().divide(12)); //1/24
        delColoumn.prefWidthProperty().bind(table.widthProperty().divide(24)); //1/24
        //TODO: count wight of coloumns
    }
    public void doHistoryTableMapping(TableView<HistoryTableRaw> historyTable,
                                      TableColumn<HistoryTableRaw, Button> historyTableDelColoumn,
                                      TableColumn<HistoryTableRaw, TextField> historyTableTaskColoumn,
                                      TableColumn<HistoryTableRaw, TextField> historyTableTimeColoumn,
                                      TableColumn<HistoryTableRaw, TextField> historyTableDateColoumn){
        historyTable.setSelectionModel(null);
        historyTableTaskColoumn.setCellValueFactory(new PropertyValueFactory<>("text"));
        historyTableTimeColoumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        historyTableDateColoumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        historyTableDelColoumn.setCellValueFactory(new PropertyValueFactory<>("delete"));

        //columns wight
        historyTableTaskColoumn.prefWidthProperty().bind(historyTable.widthProperty().divide(2));
        historyTableTimeColoumn.prefWidthProperty().bind(historyTable.widthProperty().divide(8));
        historyTableDateColoumn.prefWidthProperty().bind(historyTable.widthProperty().divide(4));
        historyTableDelColoumn.prefWidthProperty().bind((historyTable.widthProperty().divide(8)));
    }
}
