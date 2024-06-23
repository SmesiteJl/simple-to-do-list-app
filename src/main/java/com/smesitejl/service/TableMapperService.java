package com.smesitejl.service;


import com.smesitejl.entitys.HistoryTableRow;
import com.smesitejl.entitys.TaskTableRow;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class TableMapperService {
    public void doTaskTableMapping(TableView<TaskTableRow> table,
                                   TableColumn<TaskTableRow, Button> toHistoryColoumn,
                                   TableColumn<TaskTableRow, CheckBox> checkColoumn,
                                   TableColumn<TaskTableRow, TextField> textColoumn,
                                   TableColumn<TaskTableRow, TextField> timeColoumn,
                                   TableColumn<TaskTableRow, Button> startupColoumn,
                                   TableColumn<TaskTableRow, Button> delColoumn){
        table.setSelectionModel(null);
        toHistoryColoumn.setCellValueFactory(new PropertyValueFactory<>("history"));
        checkColoumn.setCellValueFactory(new PropertyValueFactory<>("to"));
        textColoumn.setCellValueFactory(new PropertyValueFactory<>("text"));
        timeColoumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        startupColoumn.setCellValueFactory(new PropertyValueFactory<>("startup"));
        delColoumn.setCellValueFactory(new PropertyValueFactory<>("del"));
        //columns wight
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_NEXT_COLUMN);
        timeColoumn.setPrefWidth(75);
        toHistoryColoumn.setPrefWidth(35);
        checkColoumn.setPrefWidth(35);
        startupColoumn.setPrefWidth(35);
        delColoumn.setPrefWidth(35);
        //TODO: make text column dynamic widht 
        //textColoumn.setMinWidth(100);
        //textColoumn.prefWidthProperty().bind(table.widthProperty().divide(1.35)); //16/24
        //toHistoryColoumn.prefWidthProperty().bind(table.widthProperty().divide(24)); //1/24
        //checkColoumn.prefWidthProperty().bind(table.widthProperty().divide(24)); //1/24

        //timeColoumn.prefWidthProperty().bind((table.widthProperty().divide(12))); //4/24
        //startupColoumn.prefWidthProperty().bind(table.widthProperty().divide(12)); //1/24
        //delColoumn.prefWidthProperty().bind(table.widthProperty().divide(24)); //1/24
        //TODO: count wight of coloumns
    }
    public void doHistoryTableMapping(TableView<HistoryTableRow> historyTable,
                                      TableColumn<HistoryTableRow, Button> historyTableDelColoumn,
                                      TableColumn<HistoryTableRow, TextField> historyTableTaskColoumn,
                                      TableColumn<HistoryTableRow, TextField> historyTableTimeColoumn,
                                      TableColumn<HistoryTableRow, TextField> historyTableDateColoumn){
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
