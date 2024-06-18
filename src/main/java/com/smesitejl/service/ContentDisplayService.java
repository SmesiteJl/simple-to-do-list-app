package com.smesitejl.service;

import com.smesitejl.entitys.HistoryTableRaw;
import com.smesitejl.DataKeeper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ContentDisplayService {


    public void displayHistoryTable(TableView<HistoryTableRaw> historyTable){
        historyTable.setItems(getReverseList());
    }

    public void displayProgress(Double currProgressValue, ProgressBar progressBar, TextField currentProgressText){
        progressBar.setProgress(currProgressValue);
        currentProgressText.setText(String.format("%.0f", currProgressValue * 100) + "%");
    }

    private ObservableList<HistoryTableRaw> getReverseList(){
        ObservableList<HistoryTableRaw> reverseHistoryList = FXCollections.observableArrayList();
        for(int i = DataKeeper.getHistoryTableRaws().size()-1; i >= 0; i--){
            reverseHistoryList.add(DataKeeper.getHistoryTableRaws().get(i));
        }
        return reverseHistoryList;
    }
}
