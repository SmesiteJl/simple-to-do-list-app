package com.smesitejl.controller;
import com.smesitejl.context.ApplicationContext;
import com.smesitejl.entitys.HistoryTableRaw;
import com.smesitejl.entitys.TaskTableRaw;

import com.smesitejl.DataKeeper;
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
        applicationContext.getTableMapperService().doTaskTableMapping(table,
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
        applicationContext.getDataTransferService().downloadCurrentTasks(table, historyTable, progressBar,currentProgressText);
        applicationContext.getContentDisplayService().displayTaskTable(table);
        applicationContext.getProgressProcessingService().updateProgress(progressBar,currentProgressText);
    }

    private void downloadHistory() {
        applicationContext.getDataTransferService().downloadHistory(historyTable);
        applicationContext.getContentDisplayService().displayHistoryTable(historyTable);
    }

    private void mainMapper(){
        currentProgressText.setText("0%");
        addTableRaw.setOnAction(actionEvent -> {
                    TaskTableRaw raw = new TaskTableRaw(table,
                            historyTable,
                            progressBar,
                            currentProgressText);
                    DataKeeper.addTaskTableRaw(raw);
                    applicationContext.getContentDisplayService().displayTaskTable(table);
                    applicationContext.getProgressProcessingService().updateProgress(progressBar,currentProgressText);
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

    public void addHistoryTableRaw(){

    }
    public void removeHistoryTableRaw(){

    }

    public void displayProgress(Double currProgressValue){
        progressBar.setProgress(currProgressValue);
        currentProgressText.setText(String.format("%.0f", currProgressValue * 100) + "%");
    }


}
