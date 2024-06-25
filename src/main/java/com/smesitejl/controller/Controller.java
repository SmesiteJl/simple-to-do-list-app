package com.smesitejl.controller;
import com.smesitejl.context.ApplicationContext;
import com.smesitejl.entitys.mainstageentitys.HistoryTableRow;
import com.smesitejl.entitys.mainstageentitys.TaskTableRow;


import com.smesitejl.entitys.widgetentitys.WidgetTableRow;
import com.smesitejl.repository.DataKeeper;
import com.smesitejl.repository.StyleProvider;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.Setter;

import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    private static Controller instance;

    @FXML
    private SplitPane splitPane;

    @FXML
    private AnchorPane ancor;

    @FXML
    private AnchorPane historyAncor;

    @FXML
    private AnchorPane taskAncor;

    @FXML
    private Button addTableRow;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private TableView<TaskTableRow> taskTable;

    @FXML
    private TableColumn<TaskTableRow, CheckBox> checkColoumn;

    @FXML
    private TableColumn<TaskTableRow, TextField> textColoumn;

    @FXML
    private TableColumn<TaskTableRow, Label> timeColoumn;

    @FXML
    private TableColumn<TaskTableRow, Button> startupColoumn;

    @FXML
    private TableColumn<TaskTableRow, Button> delColoumn;

    @FXML
    private TextField currentProgressText;

    @FXML
    private Label dayTimer;

    @FXML
    private TableView<HistoryTableRow> historyTable;

    @FXML
    private TableColumn<HistoryTableRow, Label> historyTableDateColoumn;

    @FXML
    private TableColumn<HistoryTableRow, Button> historyTableDelColoumn;

    @FXML
    private TableColumn<HistoryTableRow, Label> historyTableTaskColoumn;

    @FXML
    private TableColumn<HistoryTableRow, Label> historyTableTimeColoumn;

    @FXML
    private Button startDayButton;

    @FXML
    private TableColumn<TaskTableRow, Button> toHistoryColoumn;

    @FXML
    private Button endDayButton;

    @FXML
    private Button simpleViewButton;

    @FXML
    private Button collapseHistory;

    @FXML
    private Button expandHistory;

    @Setter
    private Stage widgetStage;
    @Setter
    private Stage mainStage;

    private final DoubleProperty progress = new SimpleDoubleProperty(0);

    private final ApplicationContext applicationContext = ApplicationContext.getInstance();




    public static synchronized Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    private Controller(){
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        progressBar.progressProperty().bind(progress);
        StyleProvider.getInstance().getExpandButtonStyle(expandHistory);
        expandHistory.setVisible(false);
        expandHistory.setOnAction(actionEvent2 -> {
            splitPane.lookup(".split-pane-divider").setVisible(true);
            historyAncor.setMinWidth(235);
            historyAncor.setMaxWidth(Double.MAX_VALUE);
            expandHistory.setVisible(false);
        });
        StyleProvider.getInstance().getCollapseButtonStyle(collapseHistory);
        collapseHistory.setOnAction(actionEvent -> {
            splitPane.lookup(".split-pane-divider").setVisible(false);
            historyAncor.setMinWidth(0);
            historyAncor.setMaxWidth(0);
            expandHistory.setVisible(true);
        });

        splitPane.setDividerPositions(200.0 / 1280.0);
        applicationContext.getTableMapperService().doTaskTableMapping(taskTable,
                    toHistoryColoumn,
                    checkColoumn,
                    textColoumn,
                    timeColoumn,
                    startupColoumn,
                    delColoumn
                    );
            applicationContext.getTableMapperService().doHistoryTableMapping(historyTable,
                    historyTableDelColoumn,
                    historyTableTaskColoumn,
                    historyTableTimeColoumn,
                    historyTableDateColoumn);
            mainMapper();
            downloadHistory();
            downloadCurrentTasks();
        }

    private void downloadCurrentTasks() {
        applicationContext.getDataTransferService().downloadCurrentTasks();
        displayTaskTable();
        applicationContext.getProgressProcessingService().updateProgress();
    }

    private void downloadHistory() {
        applicationContext.getDataTransferService().downloadHistory();
        displayHistoryTable();
    }

    private void mainMapper(){
        currentProgressText.setText("0%");
        addTableRow.setOnAction(actionEvent -> {
                    addTaskTableRow();
                    displayTaskTable();
                    applicationContext.getProgressProcessingService().updateProgress();
                }
        );

        //"plus" animations
        StyleProvider.getInstance().getPlusButtonStyle(addTableRow);

        //DayTime buttons mapping
        applicationContext.getDayTimerService().createDayTimer(startDayButton, endDayButton,dayTimer);

        //simple view button logic
        StyleProvider.getInstance().getSimpleViewButtonStyle(simpleViewButton);
        simpleViewButton.setOnAction(actionEvent6 -> {
            ObservableList<WidgetTableRow> widgetTableRows = FXCollections.observableArrayList();
            ObservableList<TaskTableRow> taskTableRows = DataKeeper.getInstance().getTaskTaskTableRows();
            for (int i = 0; i < taskTableRows.size(); i++) {
                if(!taskTableRows.get(i).getText().getText().trim().isEmpty()){
                WidgetTableRow row = new WidgetTableRow(taskTableRows.get(i).getText().getText(),
                        taskTableRows.get(i).getTime().getText());
                row.getTimerTaskControllerService().setSeconds(secondsCounter(taskTableRows.get(i).getTime().getText()));
                row.getTimerTaskControllerService().setPause(taskTableRows.get(i).getTimerTaskControllerService().isPaused());
                widgetTableRows.add(row);
                }
            }
            WidgetController.getInstance().addWidgetTaskRows(widgetTableRows);
            if(!dayTimer.getText().equals("Start your day now!")){
            WidgetController.getInstance().setWidgetDayTimer(secondsCounter(dayTimer.getText()), applicationContext.getDayTimerService().getIsTimerPaused());
            }
            else{
                WidgetController.getInstance().setDayTimerText("Start your day now!");
            }
            mainStage.hide();
            widgetStage.show();
        });
        simpleViewButton.setTooltip(new Tooltip("Go to simple view"));

    }

    public void addHistoryTableRow(String text, String time, String date){
        applicationContext.getHistoryTableService().addHistoryTableRow(text, time, date);
        displayHistoryTable();
    }
    public void addHistoryTableRow(String text, String time){
        applicationContext.getHistoryTableService().addHistoryTableRow(text, time);
        displayHistoryTable();
    }
    public void removeHistoryTableRow(HistoryTableRow row){
        applicationContext.getHistoryTableService().removeHistoryTableRow(row);
        displayHistoryTable();

    }


    public void displayHistoryTable(){
        historyTable.setItems(getReverseList());
    }

    public void addTaskTableRow(){
        applicationContext.getTaskTableService().addTaskTableRow();
    }
    public void addTaskTableRow(Boolean to, String text, String time){
        applicationContext.getTaskTableService().addTaskTableRow(to, text,time);
    }

    public void removeTaskTableRow(TaskTableRow row) {
        applicationContext.getTaskTableService().removeTaskTableRow(row);
    }

    public void displayTaskTable(){
        taskTable.setItems(DataKeeper.getInstance().getTaskTaskTableRows());
    }

    public void updateProgress(){
        applicationContext.getProgressProcessingService().updateProgress();
    }

    public void displayProgress(Double currProgressValue, Double prevProgressValue){
        progress.set(prevProgressValue);
        animateProgress(progress, currProgressValue);
        currentProgressText.setText(String.format("%.0f", currProgressValue * 100) + "%");
    }

    private  void animateProgress(DoubleProperty progressProperty, double newValue){
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0), new KeyValue(progressProperty, progressProperty.get())),
                new KeyFrame(Duration.seconds(1), new KeyValue(progressProperty, newValue))
        );
        timeline.play();
    }

    private ObservableList<HistoryTableRow> getReverseList(){
        ObservableList<HistoryTableRow> reverseHistoryList = FXCollections.observableArrayList();
        for(int i = DataKeeper.getInstance().getHistoryTableRows().size()-1; i >= 0; i--){
            reverseHistoryList.add(DataKeeper.getInstance().getHistoryTableRows().get(i));
        }
        return reverseHistoryList;
    }

    private long secondsCounter(String time){
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date reference;
        try {
            reference = dateFormat.parse("00:00:00");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Date date;
        try {
            date = dateFormat.parse(time);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return (date.getTime() - reference.getTime()) / 1000L;
    }

}
