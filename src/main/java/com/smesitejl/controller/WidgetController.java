package com.smesitejl.controller;

import com.smesitejl.context.ApplicationContext;
import com.smesitejl.entitys.widgetentitys.WidgetTableRow;
import com.smesitejl.service.mainstageservice.TimerTaskControllerService;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.Setter;

import java.net.URL;
import java.util.ResourceBundle;

public class WidgetController implements Initializable {

    private static WidgetController instance;

    @FXML
    private Label widgetDayTimer;

    @FXML
    private Button goToMainStageButton;

    @FXML
    private TableView<WidgetTableRow> widgetTable;

    @FXML
    private TableColumn<WidgetTableRow, Label> widgetTaskColumn;

    @FXML
    private TableColumn<WidgetTableRow, Label> widgetTimeColumn;

    @Setter
    private Stage widgetStage;
    @Setter
    private Stage mainStage;
    @FXML
    private AnchorPane header;

    private double xOffset = 0;
    private double yOffset = 0;
    private final ApplicationContext applicationContext = ApplicationContext.getInstance();

    public static synchronized WidgetController getInstance() {
        if (instance == null) {
            instance = new WidgetController();
        }
        return instance;
    }
    private WidgetController(){
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        applicationContext.getWidgetTableMappingService().doWidgetTableMapping(widgetTable, widgetTaskColumn,widgetTimeColumn);
        header.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        header.setOnMouseDragged(event -> {
            widgetStage.setX(event.getScreenX() - xOffset);
            widgetStage.setY(event.getScreenY() - yOffset);
        });
        goToMainStageButton.setOnAction(actionEvent -> {
            widgetStage.hide();
            mainStage.show();
        });
    }
    public void addWidgetTaskRows(ObservableList<WidgetTableRow> rows){
        widgetTable.setItems(rows);
    }
    public void setWidgetDayTimer(Long seconds, Boolean isPaused){
        TimerTaskControllerService timerTaskControllerService = new TimerTaskControllerService().createTimer(widgetDayTimer);
        timerTaskControllerService.setSeconds(seconds);
        timerTaskControllerService.setPause(isPaused);
    }
    public void setDayTimerText(String text){
        widgetDayTimer.setText(text);
    }

}
