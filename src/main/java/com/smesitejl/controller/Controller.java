package com.smesitejl.controller;
import com.smesitejl.context.ApplicationContext;
import com.smesitejl.entitys.HistoryTableRow;
import com.smesitejl.entitys.TaskTableRow;


import com.smesitejl.repository.DataKeeper;
import com.smesitejl.repository.StyleProvider;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.fxml.FXML;

import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    private static Controller instance;
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
    private TableColumn<TaskTableRow, TextField> timeColoumn;

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
    private TableColumn<HistoryTableRow, TextField> historyTableDateColoumn;

    @FXML
    private TableColumn<HistoryTableRow, Button> historyTableDelColoumn;

    @FXML
    private TableColumn<HistoryTableRow, TextField> historyTableTaskColoumn;

    @FXML
    private TableColumn<HistoryTableRow, TextField> historyTableTimeColoumn;

    @FXML
    private Button startDayButton;

    @FXML
    private TableColumn<TaskTableRow, Button> toHistoryColoumn;

    @FXML
    private Button endDayButton;

    @FXML
    private Button simpleViewButton;

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
                    addTaskTablerow();
                    displayTaskTable();
                    applicationContext.getProgressProcessingService().updateProgress();
                }
        );

        //"plus" animations
        StyleProvider.getInstance().getPlusButtonStyle(addTableRow);

        //DayTime buttons mapping
        applicationContext.getDayTimerService().createDayTimer(startDayButton, endDayButton,dayTimer);

        //simple view button logic
        simpleViewButton.setOnAction(actionEvent6 -> {
            //TODO: simple view
        });
        simpleViewButton.setTooltip(new Tooltip("Go to simple view"));

    }

    public void addHistoryTablerow(String text, String time, String date){
        applicationContext.getHistoryTableService().addHistoryTableRow(text, time, date);
        displayHistoryTable();
    }
    public void addHistoryTablerow(String text, String time){
        applicationContext.getHistoryTableService().addHistoryTableRow(text, time);
        displayHistoryTable();
    }
    public void removeHistoryTablerow(HistoryTableRow row){
        applicationContext.getHistoryTableService().removeHistoryTableRow(row);
        displayHistoryTable();

    }


    public void displayHistoryTable(){
        historyTable.setItems(getReverseList());
    }

    public void addTaskTablerow(){
        applicationContext.getTaskTableService().addTaskTablerow();
    }
    public void addTaskTablerow(Boolean to, String text, String time){
        applicationContext.getTaskTableService().addTaskTablerow(to, text,time);
    }

    public void removeTaskTablerow(TaskTableRow row) {
        applicationContext.getTaskTableService().removeTaskTableRow(row);
    }

    public void displayTaskTable(){
        taskTable.setItems(DataKeeper.getInstance().getTaskTaskTableRows());
    }

    public void updateProgress(){
        applicationContext.getProgressProcessingService().updateProgress();
    }

    public void displayProgress(Double currProgressValue){
        progressBar.setProgress(currProgressValue);
        currentProgressText.setText(String.format("%.0f", currProgressValue * 100) + "%");
    }

    private ObservableList<HistoryTableRow> getReverseList(){
        ObservableList<HistoryTableRow> reverseHistoryList = FXCollections.observableArrayList();
        for(int i = DataKeeper.getInstance().getHistoryTableRows().size()-1; i >= 0; i--){
            reverseHistoryList.add(DataKeeper.getInstance().getHistoryTableRows().get(i));
        }
        return reverseHistoryList;
    }

}
