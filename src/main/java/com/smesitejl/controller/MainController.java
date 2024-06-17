package com.smesitejl.controller;

import com.google.gson.*;

import com.smesitejl.service.PathProviderService;
import com.smesitejl.entitys.HistoryTableRaw;
import com.smesitejl.entitys.TableRaw;

import com.smesitejl.service.TimerTaskControllerService;
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

public class MainController {
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
    private TableColumn<TableRaw, Button> toHistoryColoumn;

    @FXML
    private Button endDayButton;

    @FXML
    private Button simpleViewButton;



    private enum timerStatus {
        NOT_STARTED,
        IN_PROGRESS,
        PAUSED;
    }
    private timerStatus daySessionStatus = timerStatus.NOT_STARTED;
    @Getter
    private static final ObservableList<TableRaw> tableRaws = FXCollections.observableArrayList();
    @Getter
    private static final ObservableList<HistoryTableRaw> historyTableRaws = FXCollections.observableArrayList();
    private final PathProviderService path = new PathProviderService();
    private final String timeFormat = "00:00:00";


    @FXML
    void initialize() {
            tableMapping();
            historyTableMapping();
            mainMapper();
            downloadHistory();
            downloadCurrentTasks();
        }

    private void downloadCurrentTasks() {
        try (BufferedReader reader = new BufferedReader(new FileReader(path.getTasksTablePath()))) {
            JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();
            for (JsonElement elem : jsonArray) {
                TableRaw raw = new TableRaw(historyButtonFactory(), checkBoxFactory(), textFieldFactory(), timerLabelFactory(), startupButtonFactory(),delButtonFactory());
                raw.getTo().setSelected(elem.getAsJsonObject().get("to").getAsBoolean());
                raw.getText().setText(elem.getAsJsonObject().get("text").getAsString());
                if(elem.getAsJsonObject().get("time").getAsString().isEmpty()){
                    raw.getTime().setText(timeFormat);
                }
                else{
                    raw.getTime().setText(elem.getAsJsonObject().get("time").getAsString());
                }
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

        //columns wight
        historyTableTaskColoumn.prefWidthProperty().bind(historyTable.widthProperty().divide(2));
        historyTableTimeColoumn.prefWidthProperty().bind(historyTable.widthProperty().divide(8));
        historyTableDateColoumn.prefWidthProperty().bind(historyTable.widthProperty().divide(4));
        historyTableDelColoumn.prefWidthProperty().bind((historyTable.widthProperty().divide(8)));
    }


    //reading history from json and adding to table

    private void readHistory(){
        try (BufferedReader reader = new BufferedReader(new FileReader(path.getHistoryTablePath()))) {
            JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();
            for (JsonElement elem : jsonArray) {
                HistoryTableRaw historyTableRaw = new HistoryTableRaw(historyLabelFactory(), historyLabelFactory(), historyLabelFactory(), new Button());
                historyTableRaw.getText().setText(elem.getAsJsonObject().get("text").getAsString());
                historyTableRaw.getTime().setText(elem.getAsJsonObject().get("time").getAsString());
                historyTableRaw.getDate().setText(elem.getAsJsonObject().get("day").getAsString());
                historyTableRaw.getDelete().setOnAction(actionEvent7 -> {
                    historyTableRaws.remove(historyTableRaw);
                    updateHistoryTable();
                    writeHistory();
                });
                historyTableRaw.getDelete().setOnMouseEntered(action -> historyTableRaw.getDelete().setStyle("-fx-background-image: url(icons/hisBin.gif)"));
                historyTableRaw.getDelete().setOnMouseExited(action -> historyTableRaw.getDelete().setStyle("-fx-background-image: url(icons/hisBin.png)"));
                historyTableRaws.add(historyTableRaw);
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
        toHistoryColoumn.prefWidthProperty().bind(table.widthProperty().divide(24)); //1/24
        checkColoumn.prefWidthProperty().bind(table.widthProperty().divide(24)); //1/24
        textColoumn.prefWidthProperty().bind(table.widthProperty().divide(1.5)); //16/24
        timeColoumn.prefWidthProperty().bind((table.widthProperty().divide(12))); //4/24
        startupColoumn.prefWidthProperty().bind(table.widthProperty().divide(12)); //1/24
        delColoumn.prefWidthProperty().bind(table.widthProperty().divide(24)); //1/24
        //TODO: count wight of coloumns

    }
    private void rawElementsMapping(TableRaw raw){
        //timer mapping
        long seconds = 0;
        if(!raw.getTime().getText().isEmpty()) {
             seconds = secondsCounter(raw.getTime().getText());
        }
        TimerTaskControllerService timerTaskControllerService = createTimer(raw.getTime());
        timerTaskControllerService.setSeconds(seconds);
        timerTaskControllerService.setPause(true);
        //history button logic
        raw.getHistory().setOnAction(actionEvent8 -> {
            if (raw.getTo().isSelected()) {
                HistoryTableRaw historyTableRaw = new HistoryTableRaw(historyLabelFactory(), historyLabelFactory(), historyLabelFactory(), new Button());
                historyTableRaw.getText().setText(getUnStrikethroughText(raw.getText().getText()));
                historyTableRaw.getTime().setText(raw.getTime().getText());
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy HH:mm");
                historyTableRaw.getDate().setText(dateFormat.format(new Date()));
                historyTableRaw.getDelete().setOnAction(actionEvent9 -> {
                    historyTableRaws.remove(historyTableRaw);
                    updateHistoryTable();
                    writeHistory();
                });
                historyTableRaw.getDelete().setOnMouseEntered(action -> historyTableRaw.getDelete().setStyle("-fx-background-image: url(icons/hisBin.gif)"));
                historyTableRaw.getDelete().setOnMouseExited(action -> historyTableRaw.getDelete().setStyle("-fx-background-image: url(icons/hisBin.png)"));
                historyTableRaws.add(historyTableRaw);
                updateHistoryTable();
                writeHistory();

            }
        });
        //textField logic
        raw.getText().setOnKeyTyped(action -> {
            updateProgress();
        });

        //del button logic
        raw.getDel().setOnAction(actionEvent1 -> {
            timerTaskControllerService.cancel();
            tableRaws.remove(raw);
            updateTable();
            updateProgress();
        });
        //check button logic
        raw.getTo().setOnAction(actionEvent2 -> {
            updateProgress();
            String text = raw.getText().getText().trim();
            if(raw.getTo().isSelected() && !raw.getText().getText().trim().isEmpty()) {
                timerTaskControllerService.setPause(true);
                raw.getStartup().setStyle("-fx-background-image: url(icons/play.png)");
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
        //toHistory button animation
        raw.getHistory().setOnMouseEntered(action -> raw.getHistory().setStyle("-fx-background-image: url(icons/save.gif)"));
        raw.getHistory().setOnMouseExited(action -> raw.getHistory().setStyle("-fx-background-image: url(icons/save.png)"));
        //del button animation
        raw.getDel().setOnMouseEntered(action -> raw.getDel().setStyle("-fx-background-image: url(icons/bin.gif)"));
        raw.getDel().setOnMouseExited(action -> raw.getDel().setStyle("-fx-background-image: url(icons/bin.png)"));

        //startup button logic
        raw.getStartup().setText("Start");
        raw.getStartup().setStyle("-fx-background-image: url(icons/play.png)");
        raw.getStartup().setOnAction(actionEvent3 -> {
            if(!raw.getText().getText().isEmpty() && !raw.getTo().isSelected() && raw.getStartup().getText().equals("Start")) {
                timerTaskControllerService.setPause(false);
                raw.getStartup().setStyle("-fx-background-image: url(icons/pause.png)");
                raw.getStartup().setOnMouseEntered(action -> raw.getStartup().setStyle("-fx-background-image: url(icons/pause.gif)"));
                raw.getStartup().setOnMouseExited(action -> raw.getStartup().setStyle("-fx-background-image: url(icons/pause.png)"));
                raw.getStartup().setText("Pause");
            }
            else if(raw.getStartup().getText().equals("Pause")){
                raw.getStartup().setStyle("-fx-background-image: url(icons/play.png)");
                raw.getStartup().setOnMouseEntered(action -> raw.getStartup().setStyle("-fx-background-image: url(icons/play.gif)"));
                raw.getStartup().setOnMouseExited(action -> raw.getStartup().setStyle("-fx-background-image: url(icons/play.png)"));
                raw.getStartup().setText("Start");
                timerTaskControllerService.setPause(true);
            }
        });
        raw.getStartup().setOnMouseEntered(action -> raw.getStartup().setStyle("-fx-background-image: url(icons/play.gif)"));
        raw.getStartup().setOnMouseExited(action -> raw.getStartup().setStyle("-fx-background-image: url(icons/play.png)"));
    }

    @SneakyThrows
    private long secondsCounter(String time){
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date reference = dateFormat.parse(timeFormat);
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
                    TableRaw raw = new TableRaw(historyButtonFactory(), checkBoxFactory(), textFieldFactory(), timerLabelFactory(), startupButtonFactory(),delButtonFactory());
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
        startDayButton.setOnMouseEntered(action5 -> startDayButton.setStyle("-fx-background-image: url(icons/sunrise.gif);"));
        startDayButton.setOnMouseExited(action6 -> startDayButton.setStyle("-fx-background-image: url(icons/sunrise.png);"));
        TimerTaskControllerService timerTaskControllerService = createTimer(dayTimer);
        timerTaskControllerService.setPause(true);
        startDayButton.setOnAction(actionEvent5 -> {
            if(startDayButton.getText().equals("Start day")){
            daySessionStatus = timerStatus.IN_PROGRESS;
            timerTaskControllerService.setPause(false);
            startDayButton.setText("Pause day");
            startDayButton.setStyle("-fx-background-image: url(icons/pauseSun.gif);");
            startDayButton.setOnMouseEntered(action5 -> startDayButton.setStyle("-fx-background-image: url(icons/pauseSun.gif);"));
            startDayButton.setOnMouseExited(action6 -> startDayButton.setStyle("-fx-background-image: url(icons/pauseSun.gif);"));

            }
            else if (startDayButton.getText().equals("Pause day")){
                daySessionStatus = timerStatus.PAUSED;
                startDayButton.setStyle("-fx-background-image: url(icons/playSun.png);");
                startDayButton.setOnMouseEntered(action5 -> startDayButton.setStyle("-fx-background-image: url(icons/playSun.png);"));
                startDayButton.setOnMouseExited(action6 -> startDayButton.setStyle("-fx-background-image: url(icons/playSun.png);"));
                timerTaskControllerService.setPause(true);
                startDayButton.setText("Start day");
            }

        });


        endDayButton.setOnAction(actionEvent6 -> {
            timerTaskControllerService.setPause(true);
            dayTimer.setText("Start your day now!");
            timerTaskControllerService.setSeconds(0);
            startDayButton.setText("Start day");
            daySessionStatus = timerStatus.NOT_STARTED;
            startDayButton.setStyle("-fx-background-image: url(icons/sunrise.png);");
            startDayButton.setOnMouseEntered(action5 -> startDayButton.setStyle("-fx-background-image: url(icons/sunrise.gif);"));
            startDayButton.setOnMouseExited(action6 -> startDayButton.setStyle("-fx-background-image: url(icons/sunrise.png);"));
            //TODO: сделать вылетающее окно "Сегодня вы проработали HH:MM:SS. Потрясающе!"

        });
        endDayButton.setOnMouseEntered(action4 -> endDayButton.setStyle("-fx-background-image: url(icons/sunset.gif);"));
        endDayButton.setOnMouseExited(action5 -> endDayButton.setStyle("-fx-background-image: url(icons/sunset.png);"));

        //simple view button logic
        simpleViewButton.setOnAction(actionEvent6 -> {
            //TODO: simple view
        });
        simpleViewButton.setTooltip(new Tooltip("Go to simple view"));

    }
    private TimerTaskControllerService createTimer(Label timerLabel){
        Timer timer = new Timer();
        TimerTaskControllerService timerTaskControllerService = new TimerTaskControllerService();
        TimerTask timerTask = new TimerTask() {
            private int seconds = 0;
            @Override
            public void run() {
                if (!timerTaskControllerService.isPaused()) {
                    timerTaskControllerService.setSeconds(timerTaskControllerService.getSeconds() + 1);
                    long seconds = timerTaskControllerService.getSeconds();
                    long hours = seconds / 3600;
                    long minutes = (seconds % 3600) / 60;
                    long secs = seconds % 60;
                    String timeString = String.format("%02d:%02d:%02d", hours, minutes, secs);
                    Platform.runLater(() -> timerLabel.setText(timeString));
                }
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
        timerTaskControllerService.setTimer(timer);
        timerTaskControllerService.setTimerTask(timerTask);

        return timerTaskControllerService;
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
    private Label timerLabelFactory(){
        Label label= new Label();
        label.setText(timeFormat);
        return label;
    }
    private TextField textFieldFactory(){
        TextField textField = new TextField();
        textField.setPromptText("New task...");
        return textField;
    }
    private void updateProgress(){
        Double selectedNotEmpty = 0.;
        Double notEmptyTasks = 0.;
        for (int i = 0; i < tableRaws.size(); i++) {
            TableRaw raw = tableRaws.get(i);
            if(raw.getTo().isSelected() && !raw.getText().getText().isEmpty()){
                selectedNotEmpty+=1;
            }
            if(!raw.getText().getText().isEmpty()){
                notEmptyTasks +=1;

            }
        }

        Double currProgressValue = selectedNotEmpty / notEmptyTasks;
        if (currProgressValue.isNaN()){
            currProgressValue = 0.;
        }
        progressBar.setProgress(currProgressValue);
        percent.setText(String.format("%.0f", currProgressValue * 100) + "%");
    }
    private Label historyLabelFactory(){
        return new Label();
    }

    private void updateTable(){
        table.setItems(tableRaws);
    }
    private void updateHistoryTable(){
        historyTable.setItems(historyTableRaws);
    }
}

