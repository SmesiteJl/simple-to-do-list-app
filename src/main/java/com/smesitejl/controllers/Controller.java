package com.smesitejl.controllers;

import com.smesitejl.controllers.controllerEntitys.TableRaw;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;


import java.util.ArrayList;
import java.util.List;

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
    private TableColumn<TableRaw, Button> delColoumn;
    @FXML
    private TextField percent;

    private Double checkBoxCounter = 0.;
    private final ObservableList<TableRaw> tableRaws = FXCollections.observableArrayList();


    @FXML
    void initialize() {
        tableMapping();
        percent.setText("0%");
        addCheckBox.setOnAction(actionEvent -> {
           checkBoxCounter += 1;
           TableRaw raw = new TableRaw(checkBoxFactory(), textFieldFactory(),buttonFactory());
          rawElementsMapping(raw);
           tableRaws.add(raw);
           table.setItems(tableRaws);
           updateProgress();
                    }
                );
        //"plus" animations
        addCheckBox.setOnMouseEntered(action4 -> addCheckBox.setStyle("-fx-background-image: url(icons/plus.gif);"));
        addCheckBox.setOnMouseExited(action5 -> addCheckBox.setStyle("-fx-background-image: url(icons/plus.png);"));
        }
    private void tableMapping(){
        table.setSelectionModel(null);
        //columns mapping
        checkColoumn.setCellValueFactory(new PropertyValueFactory<>("to"));
        textColoumn.setCellValueFactory(new PropertyValueFactory<>("text"));
        delColoumn.setCellValueFactory(new PropertyValueFactory<>("del"));
        //columns wight
        checkColoumn.prefWidthProperty().bind(table.widthProperty().divide(10));
        textColoumn.prefWidthProperty().bind(table.widthProperty().divide(1.25));
        delColoumn.prefWidthProperty().bind(table.widthProperty().divide(10));

    }
    private void rawElementsMapping(TableRaw raw){
        //del button logic
        raw.getDel().setOnAction(actionEvent1 -> {
            tableRaws.remove(raw);
            checkBoxCounter -=1;
            updateProgress();
        });
        //check button logic
        raw.getTo().setOnAction(actionEvent2 -> {
            updateProgress();
            String text = raw.getText().getText();
            if(raw.getTo().isSelected() && !raw.getText().getText().isEmpty()) {
                StringBuilder sb = new StringBuilder();
                sb.append("̵");
                for (int i = 0; i < text.length(); i++) {
                    sb.append(text.charAt(i)).append("̵");
                }
                raw.getText().setText(String.valueOf(sb));
            }
            else if (!raw.getTo().isSelected() && !raw.getText().getText().isEmpty()){
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < text.length(); i++) {
                    if(text.charAt(i) != '̵'){
                        sb.append(text.charAt(i));
                    }
                }
                raw.getText().setText(String.valueOf(sb));
            }
            else if(raw.getTo().isSelected() && raw.getText().getText().isEmpty()){
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
    }
    private CheckBox checkBoxFactory(){
        return new CheckBox();
    }
    private Button buttonFactory(){
        return new Button();
    }

    private TextField textFieldFactory(){
        TextField textField = new TextField();
        textField.setPromptText("New task...");
        return textField;
    }
    private void updateProgress(){
        List<CheckBox> checkBoxList = new ArrayList<>();
        for(int i= 0; i < checkBoxCounter; i++){
            checkBoxList.add(checkColoumn.getCellData(i));
        }
        Double currProgress = (double) checkBoxList.stream().filter(CheckBox::isSelected).count();
        double currProgressValue = currProgress/checkBoxCounter;
        progressBar.setProgress(currProgressValue);
        percent.setText(String.format("%.0f", currProgressValue*100)+ "%");
    }
}
