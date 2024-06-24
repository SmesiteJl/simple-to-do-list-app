package com.smesitejl.service;


import com.smesitejl.entitys.HistoryTableRow;
import com.smesitejl.entitys.TaskTableRow;
import javafx.beans.binding.Bindings;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.naming.Binding;

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
        toHistoryColoumn.setPrefWidth(35);
        checkColoumn.setPrefWidth(35);
        timeColoumn.setPrefWidth(75);
        startupColoumn.setPrefWidth(35);
        delColoumn.setPrefWidth(60);
        double size = toHistoryColoumn.getPrefWidth()
                + checkColoumn.getPrefWidth()
                + timeColoumn.getPrefWidth()
                + startupColoumn.getPrefWidth()
                + delColoumn.getPrefWidth();
        textColoumn.prefWidthProperty().bind(table.widthProperty().subtract(size));
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
        historyTableDelColoumn.setPrefWidth(20);
        historyTableDateColoumn.setPrefWidth(70);
        historyTableTimeColoumn.setPrefWidth(45);
        double size = historyTableDelColoumn.getPrefWidth()
                + historyTableDateColoumn.getPrefWidth()
                + historyTableTimeColoumn.getPrefWidth();
        historyTableTaskColoumn.prefWidthProperty().bind(historyTable.widthProperty().subtract(size));
    }
}
