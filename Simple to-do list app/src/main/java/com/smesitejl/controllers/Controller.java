package com.smesitejl.controllers;

import com.smesitejl.controllers.controllerEntitys.TableRaw;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
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
           TableRaw raw = new TableRaw(new CheckBox(), textFieldFactory(),buttonFactory());
          rawElementsMapping(raw);
           tableRaws.add(raw);
           table.setItems(tableRaws);
           updateProgress();
                    }
                );
        }
    private void tableMapping(){
        checkColoumn.setCellValueFactory(new PropertyValueFactory<>("to"));
        textColoumn.setCellValueFactory(new PropertyValueFactory<>("text"));
        delColoumn.setCellValueFactory(new PropertyValueFactory<>("del"));
        checkColoumn.prefWidthProperty().bind(table.widthProperty().divide(10));
        textColoumn.prefWidthProperty().bind(table.widthProperty().divide(1.25));
        delColoumn.prefWidthProperty().bind(table.widthProperty().divide(10));

    }
    private void rawElementsMapping(TableRaw raw){
        raw.getDel().setOnAction(actionEvent1 -> {
            tableRaws.remove(raw);
            checkBoxCounter -=1;
            updateProgress();
        });
        raw.getTo().setOnAction(actionEvent2 -> {
            updateProgress();
            String text = raw.getText().getText();
            if(raw.getTo().isSelected()) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < text.length(); i++) {
                    sb.append(text.charAt(i)).append("̵");
                }
                raw.getText().setText(String.valueOf(sb));
            }
            else{
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < text.length(); i++) {
                    if(text.charAt(i) != '̵'){
                        sb.append(text.charAt(i));
                    }
                }
                raw.getText().setText(String.valueOf(sb));
            }
        });
    }

    private Button buttonFactory(){
        Button butt = new Button();
        butt.setPrefSize(32,32);
        butt.setStyle("-fx-background-image: url(del2.png)");
        butt.setPadding(Insets.EMPTY);
        return butt;
    }

    private TextField textFieldFactory(){
        TextField textField = new TextField();
        textField.setStyle("-fx-font-size: 18; -fx-font-weight: bold");
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
