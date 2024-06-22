package com.smesitejl.controller;
import com.smesitejl.context.ApplicationContext;
import com.smesitejl.entitys.HistoryTableRaw;
import com.smesitejl.entitys.TaskTableRaw;

import com.smesitejl.DataKeeper;
import com.smesitejl.service.HistoryTableService;
import com.smesitejl.service.TaskTableService;
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
    private Button addTableRaw;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private TableView<TaskTableRaw> taskTable;

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
        addTableRaw.setOnAction(actionEvent -> {
                    addTaskTableRaw();
                    displayTaskTable();
                    applicationContext.getProgressProcessingService().updateProgress();
                }
        );

        //"plus" animations
        addTableRaw.setOnMouseEntered(action4 -> addTableRaw.setStyle("-fx-background-image: url(icons/plus.gif);"));
        addTableRaw.setOnMouseExited(action5 -> addTableRaw.setStyle("-fx-background-image: url(icons/plus.png);"));

        //DayTime buttons mapping
        applicationContext.getDayTimerService().createDayTimer(startDayButton, endDayButton,dayTimer);

        //simple view button logic
        simpleViewButton.setOnAction(actionEvent6 -> {
            //TODO: simple view
        });
        simpleViewButton.setTooltip(new Tooltip("Go to simple view"));

    }

    public void addHistoryTableRaw(String text, String time, String date){
        applicationContext.getHistoryTableService().addHistoryTableRaw(text, time, date);
        displayHistoryTable();
    }
    public void addHistoryTableRaw(String text, String time){
        applicationContext.getHistoryTableService().addHistoryTableRaw(text, time);
        displayHistoryTable();
    }
    public void removeHistoryTableRaw(HistoryTableRaw raw){
        applicationContext.getHistoryTableService().removeHistoryTableRaw(raw);
        displayHistoryTable();

    }


    public void displayHistoryTable(){
        historyTable.setItems(getReverseList());
    }

    public void addTaskTableRaw(){
        applicationContext.getTaskTableService().addTaskTableRaw();
    }
    public void addTaskTableRaw(Boolean to, String text, String time){
        applicationContext.getTaskTableService().addTaskTableRaw(to, text,time);
    }

    public void removeTaskTableRaw(TaskTableRaw raw) {
        applicationContext.getTaskTableService().removeTaskTableRaw(raw);
    }

    public void displayTaskTable(){
        taskTable.setItems(DataKeeper.getInstance().getTaskTaskTableRaws());
    }

    public void updateProgress(){
        applicationContext.getProgressProcessingService().updateProgress();
    }

    public void displayProgress(Double currProgressValue){
        progressBar.setProgress(currProgressValue);
        currentProgressText.setText(String.format("%.0f", currProgressValue * 100) + "%");
    }

    private ObservableList<HistoryTableRaw> getReverseList(){
        ObservableList<HistoryTableRaw> reverseHistoryList = FXCollections.observableArrayList();
        for(int i = DataKeeper.getInstance().getHistoryTableRaws().size()-1; i >= 0; i--){
            reverseHistoryList.add(DataKeeper.getInstance().getHistoryTableRaws().get(i));
        }
        return reverseHistoryList;
    }

}
