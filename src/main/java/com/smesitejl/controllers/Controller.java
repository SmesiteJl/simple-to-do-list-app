package com.smesitejl.controllers;

import com.google.gson.*;

import com.smesitejl.controllers.controllerEntitys.HistoryTableRaw;
import com.smesitejl.controllers.controllerEntitys.TableRaw;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Getter;
import lombok.SneakyThrows;


import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Controller {
    @FXML
    private Button addCheckBox;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private TableView<TableRaw> table;

    @FXML
    private TableColumn<TableRaw, CheckBox> checkColoumn;

    @FXML
    private TableColumn<TableRaw, TextField> textColoumn;

    @FXML
    private TableColumn<TableRaw, TextField> timeColoumn;

    @FXML
    private TableColumn<TableRaw, Button> startupColoumn;

    @FXML
    private TableColumn<TableRaw, Button> delColoumn;

    @FXML
    private TextField percent;

    @FXML
    private TextField dayTimer;

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
    private TableColumn<TableRaw, Button> toHistoryColoumn;

    @FXML
    private Button endDayButton;

    @FXML
    private Button simpleViewButton;




    private Double checkBoxCounter = 0.;
    @Getter
    private static final ObservableList<TableRaw> tableRaws = FXCollections.observableArrayList();
    @Getter
    private static final ObservableList<HistoryTableRaw> historyTableRaws = FXCollections.observableArrayList();


    @FXML
    void initialize() {
        tableMapping();
        historyTableMapping();
        mainMapper();
        downloadHistory();
        downloadCurrentTasks();
        }

    private void downloadCurrentTasks() {
        try (FileReader reader = new FileReader("src/main/resources/data/currentTasks.json")) {
            JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();
            for (JsonElement elem : jsonArray) {
                checkBoxCounter+=1;
                TableRaw raw = new TableRaw(historyButtonFactory(), checkBoxFactory(), textFieldFactory(), timerTextFieldFactory(), startupButtonFactory(),delButtonFactory());
                raw.getTo().setSelected(elem.getAsJsonObject().get("to").getAsBoolean());
                raw.getText().setText(elem.getAsJsonObject().get("text").getAsString());
                raw.getTime().setText(elem.getAsJsonObject().get("time").getAsString());
                rawElementsMapping(raw);
                tableRaws.add(raw);
                updateTable();
                updateProgress();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void downloadHistory() {
        readHistory();
        writeHistory();
    }

    private void writeHistory() {
        ObservableList<HistoryTableRaw> reverseHistoryList = FXCollections.observableArrayList();
        for(int i = historyTableRaws.size()-1; i >= 0; i--){
            reverseHistoryList.add(historyTableRaws.get(i));
        }
        historyTable.setItems(reverseHistoryList);
    }

    private void historyTableMapping(){
        historyTable.setSelectionModel(null);
        historyTableTaskColoumn.setCellValueFactory(new PropertyValueFactory<>("text"));
        historyTableTimeColoumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        historyTableDateColoumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        historyTableDelColoumn.setCellValueFactory(new PropertyValueFactory<>("delete"));
    }


    //reading history from json and adding to table
    private void readHistory(){
        try (FileReader reader = new FileReader("src/main/resources/data/history.json")) {
            JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();
            for (JsonElement elem : jsonArray) {
                HistoryTableRaw raw = new HistoryTableRaw(historyTextFieldFactory(), historyTextFieldFactory(), historyTextFieldFactory(), new Button());
                raw.getText().setText(elem.getAsJsonObject().get("text").getAsString());
                raw.getTime().setText(elem.getAsJsonObject().get("time").getAsString());
                raw.getDate().setText(elem.getAsJsonObject().get("day").getAsString());
                raw.getDelete().setOnAction(actionEvent7 -> {
                    historyTableRaws.remove(raw);
                    updateHistoryTable();

                    writeHistory();
                });
                historyTableRaws.add(raw);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    private void tableMapping(){
        table.setSelectionModel(null);
        //columns mapping
        toHistoryColoumn.setCellValueFactory(new PropertyValueFactory<>("history"));
        checkColoumn.setCellValueFactory(new PropertyValueFactory<>("to"));
        textColoumn.setCellValueFactory(new PropertyValueFactory<>("text"));
        timeColoumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        startupColoumn.setCellValueFactory(new PropertyValueFactory<>("startup"));
        delColoumn.setCellValueFactory(new PropertyValueFactory<>("del"));
        //columns wight
        //toHistoryColoumn.prefWidthProperty().bind(table.widthProperty().divide(10));
        //checkColoumn.prefWidthProperty().bind(table.widthProperty().divide(10));
        //textColoumn.prefWidthProperty().bind(table.widthProperty().divide(10));
        //timeColoumn.prefWidthProperty().bind((table.widthProperty().divide(10)));
        //startupColoumn.prefWidthProperty().bind(table.widthProperty().divide(10));
        //delColoumn.prefWidthProperty().bind(table.widthProperty().divide(10));
        //TODO: count wight of coloumns

    }
    private void rawElementsMapping(TableRaw raw){
        //timer mapping
        long seconds = 0;
        if(!raw.getTime().getText().isEmpty()) {
             seconds = secondsCounter(raw.getTime().getText());
        }
        TimerTaskController timerTaskController = createTimer(raw.getTime());
        timerTaskController.setSeconds(seconds);
        timerTaskController.setPause(true);
        //history button logic
        raw.getHistory().setOnAction(actionEvent8 -> {
            if (raw.getTo().isSelected()) {
                HistoryTableRaw historyTableRaw = new HistoryTableRaw(historyTextFieldFactory(), historyTextFieldFactory(), historyTextFieldFactory(), new Button());
                historyTableRaw.getText().setText(getUnStrikethroughText(raw.getText().getText()));
                historyTableRaw.getTime().setText(raw.getTime().getText());
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy HH:mm:ss");
                historyTableRaw.getDate().setText(dateFormat.format(new Date()));
                historyTableRaw.getDelete().setOnAction(actionEvent9 -> {
                    historyTableRaws.remove(historyTableRaw);
                    updateHistoryTable();

                    writeHistory();
                });
                historyTableRaws.add(historyTableRaw);
                updateHistoryTable();
                writeHistory();

            }
        });

        //del button logic
        raw.getDel().setOnAction(actionEvent1 -> {
            timerTaskController.cancel();
            tableRaws.remove(raw);
            updateTable();
            checkBoxCounter -=1;
            updateProgress();
        });
        //check button logic
        raw.getTo().setOnAction(actionEvent2 -> {
            updateProgress();
            String text = raw.getText().getText().trim();
            if(raw.getTo().isSelected() && !raw.getText().getText().trim().isEmpty()) {
                timerTaskController.setPause(true);
                raw.getStartup().setText("Start");
                raw.getText().setText(getStrikethroughText(text));
            }
            else if (!raw.getTo().isSelected() && !raw.getText().getText().trim().isEmpty()){
                raw.getText().setText(getUnStrikethroughText(text));
            }
            else if(raw.getTo().isSelected() && raw.getText().getText().trim().isEmpty()){
                raw.getText().setText(raw.getText().getText().trim());
                raw.getTo().setSelected(false);
                updateProgress();
            }
        });
        //check box tooltips
        raw.getTo().setOnMouseEntered(action -> {
            if(raw.getText().getText().isEmpty()){
                raw.getTo().setTooltip(new Tooltip("You cannot perform task without definition"));
            }
            else {
                raw.getTo().setTooltip(new Tooltip("Keep it up"));
            }


        });
        //del button animation
        raw.getDel().setOnMouseEntered(action -> raw.getDel().setStyle("-fx-background-image: url(icons/bin.gif)"));
        raw.getDel().setOnMouseExited(action -> raw.getDel().setStyle("-fx-background-image: url(icons/bin.png)"));

        //startup button logic
        raw.getStartup().setText("Start");
        raw.getStartup().setOnAction(actionEvent3 -> {
            if(!raw.getText().getText().isEmpty() && !raw.getTo().isSelected() && raw.getStartup().getText().equals("Start")) {
                timerTaskController.setPause(false);
                raw.getStartup().setText("Pause");
            }
            else if(raw.getStartup().getText().equals("Pause")){
                raw.getStartup().setText("Start");
                timerTaskController.setPause(true);
            }
        });
    }

    @SneakyThrows
    private long secondsCounter(String time){
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date reference = dateFormat.parse("00:00:00");
        Date date = dateFormat.parse(time);
        long seconds = (date.getTime() - reference.getTime()) / 1000L;
        return seconds;
    }

    private String getUnStrikethroughText(String text) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            if(text.charAt(i) != '̵'){
                sb.append(text.charAt(i));
            }
        }
        return String.valueOf(sb);
    }

    private String getStrikethroughText(String text) {
        StringBuilder sb = new StringBuilder();
        sb.append("̵");
        for (int i = 0; i < text.length(); i++) {
            sb.append(text.charAt(i)).append("̵");
        }
        return String.valueOf(sb);
    }

    private void mainMapper(){
        percent.setText("0%");
        addCheckBox.setOnAction(actionEvent -> {
                    checkBoxCounter += 1;
                    TableRaw raw = new TableRaw(historyButtonFactory(), checkBoxFactory(), textFieldFactory(), timerTextFieldFactory(), startupButtonFactory(),delButtonFactory());
                    rawElementsMapping(raw);
                    tableRaws.add(raw);
                    updateTable();
                    updateProgress();
                }
        );

        //"plus" animations
        addCheckBox.setOnMouseEntered(action4 -> addCheckBox.setStyle("-fx-background-image: url(icons/plus.gif);"));
        addCheckBox.setOnMouseExited(action5 -> addCheckBox.setStyle("-fx-background-image: url(icons/plus.png);"));

        //DayTime buttons mapping
        TimerTaskController timerTaskController = createTimer(dayTimer);
        timerTaskController.setPause(true);
        startDayButton.setOnAction(actionEvent5 -> {
            if(startDayButton.getText().equals("Start day")){
            timerTaskController.setPause(false);
            startDayButton.setText("Pause day");
            }
            else if (startDayButton.getText().equals("Pause day")){
                timerTaskController.setPause(true);
                startDayButton.setText("Start day");
            }

        });
        endDayButton.setOnAction(actionEvent6 -> {
            timerTaskController.setPause(true);
            dayTimer.setText("00:00:00");
            timerTaskController.setSeconds(0);
            startDayButton.setText("Start day");
            //TODO: сделать вылетающее окно "Сегодня вы проработали HH:MM:SS. Потрясающе!"
        });

        simpleViewButton.setOnAction(actionEvent6 -> {
            //TODO: simple view
        });
    }
    private TimerTaskController createTimer(TextField timerLabel){
        Timer timer = new Timer();
        TimerTaskController timerTaskController = new TimerTaskController();

        TimerTask timerTask = new TimerTask() {
            private int seconds = 0;
            @Override
            public void run() {
                if (!timerTaskController.isPaused()) {
                    timerTaskController.setSeconds(timerTaskController.getSeconds() + 1);
                    long seconds = timerTaskController.getSeconds();
                    long hours = seconds / 3600;
                    long minutes = (seconds % 3600) / 60;
                    long secs = seconds % 60;
                    String timeString = String.format("%02d:%02d:%02d", hours, minutes, secs);
                    Platform.runLater(() -> timerLabel.setText(timeString));
                }
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
        timerTaskController.setTimer(timer);
        timerTaskController.setTimerTask(timerTask);

        return timerTaskController;
    }


    private Button historyButtonFactory(){
        return new Button();
    }
    private CheckBox checkBoxFactory(){
        return new CheckBox();
    }
    private Button delButtonFactory(){
        return new Button();
    }
    private Button startupButtonFactory(){
        return new Button();
    }
    private TextField timerTextFieldFactory(){
        TextField textField = new TextField();
        textField.setPromptText("00:00:00");
        textField.setEditable(false);
        return textField;
    }

    private TextField textFieldFactory(){
        TextField textField = new TextField();
        textField.setPromptText("New task...");
        return textField;
    }
    private void updateProgress(){
        List<CheckBox> checkBoxList = new ArrayList<>();
        for (int i = 0; i < checkBoxCounter; i++) {
            checkBoxList.add(checkColoumn.getCellData(i));
        }
        Double currProgress = (double) checkBoxList.stream().filter(CheckBox::isSelected).count();
        double currProgressValue = currProgress / checkBoxCounter;
        if(currProgress.equals(0.) && checkBoxCounter.equals(0.)){
            currProgressValue = 0.;
        }
        progressBar.setProgress(currProgressValue);
        percent.setText(String.format("%.0f", currProgressValue * 100) + "%");
    }
    private TextField historyTextFieldFactory(){
        TextField textField = new TextField();
        textField.setEditable(false);
        return textField;
    }

    private void updateTable(){
        table.setItems(tableRaws);
    }
    private void updateHistoryTable(){
        historyTable.setItems(historyTableRaws);
    }
}

