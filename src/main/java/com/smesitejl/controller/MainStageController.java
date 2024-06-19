package com.smesitejl.controller;

import com.google.gson.*;

import com.smesitejl.service.*;
import com.smesitejl.entitys.HistoryTableRaw;
import com.smesitejl.entitys.TaskTableRaw;

import com.smesitejl.DataKeeper;
import javafx.scene.control.*;
import javafx.fxml.FXML;


import java.io.*;

public class MainStageController {
    @FXML
    private Button addTableRaw;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private TableView<TaskTableRaw> table;

    @FXML
    private TableColumn<TaskTableRaw, CheckBox> checkColoumn;

    @FXML
    private TableColumn<TaskTableRaw, TextField> textColoumn;

    @FXML
    private TableColumn<TaskTableRaw, TextField> timeColoumn;

    @FXML
    private TableColumn<TaskTableRaw, Button> startupColoumn;

    @FXML
    private TableColumn<TaskTableRaw, Button> delColoumn;

    @FXML
    private TextField currentProgressText;

    @FXML
    private Label dayTimer;

    @FXML
    private TableView<HistoryTableRaw> historyTable;

    @FXML
    private TableColumn<HistoryTableRaw, TextField> historyTableDateColoumn;

    @FXML
    private TableColumn<HistoryTableRaw, Button> historyTableDelColoumn;

    @FXML
    private TableColumn<HistoryTableRaw, TextField> historyTableTaskColoumn;

    @FXML
    private TableColumn<HistoryTableRaw, TextField> historyTableTimeColoumn;

    @FXML
    private Button startDayButton;

    @FXML
    private TableColumn<TaskTableRaw, Button> toHistoryColoumn;

    @FXML
    private Button endDayButton;

    @FXML
    private Button simpleViewButton;



    private enum timerStatus {
        NOT_STARTED,
        IN_PROGRESS,
        PAUSED;
    }

    private final ContentDisplayService contentDisplayService = new ContentDisplayService();
    private final DataTransferService dataTransferService = new DataTransferService();
    private final TableMapperService tableMapperService = new TableMapperService();
    private final ProgressProcessingService progressProcessingService = new ProgressProcessingService();
    DayTimerService dayTimerService = new DayTimerService();

    @FXML
    void initialize() {
            tableMapperService.doTaskTableMapping(table,
                    toHistoryColoumn,
                    checkColoumn,
                    textColoumn,
                    timeColoumn,
                    startupColoumn,
                    delColoumn
                    );
            tableMapperService.doHistoryTableMapping(historyTable,
                    historyTableDelColoumn,
                    historyTableTaskColoumn,
                    historyTableTimeColoumn,
                    historyTableDateColoumn);
            mainMapper();
            downloadHistory();
            downloadCurrentTasks();
        }

    private void downloadCurrentTasks() {
        dataTransferService.downloadCurrentTasks(table, historyTable, progressBar,currentProgressText);
        contentDisplayService.displayTaskTable(table);
        progressProcessingService.updateProgress(progressBar,currentProgressText);
    }

    private void downloadHistory() {
        dataTransferService.downloadHistory(historyTable);
        contentDisplayService.displayHistoryTable(historyTable);
    }

    private void mainMapper(){
        currentProgressText.setText("0%");
        addTableRaw.setOnAction(actionEvent -> {
                    TaskTableRaw raw = new TaskTableRaw(table,
                            historyTable,
                            progressBar,
                            currentProgressText);
                    DataKeeper.addTaskTableRaw(raw);
                    contentDisplayService.displayTaskTable(table);
                    progressProcessingService.updateProgress(progressBar,currentProgressText);
                }
        );

        //"plus" animations
        addTableRaw.setOnMouseEntered(action4 -> addTableRaw.setStyle("-fx-background-image: url(icons/plus.gif);"));
        addTableRaw.setOnMouseExited(action5 -> addTableRaw.setStyle("-fx-background-image: url(icons/plus.png);"));

        //DayTime buttons mapping
        dayTimerService.createDayTimer(startDayButton, endDayButton,dayTimer);

        //simple view button logic
        simpleViewButton.setOnAction(actionEvent6 -> {
            //TODO: simple view
        });
        simpleViewButton.setTooltip(new Tooltip("Go to simple view"));

    }
}
