package com.smesitejl.service;

import com.smesitejl.DataKeeper;
import com.smesitejl.controller.Controller;
import com.smesitejl.entitys.TaskTableRaw;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;

public class ProgressProcessingService {
    private Double currProgressValue;
    public void updateProgress(ProgressBar progressBar, TextField currentProgressText){
        double selectedNotEmpty = 0.;
        double notEmptyTasks = 0.;
        for (int i = 0; i < DataKeeper.getTaskTaskTableRaws().size(); i++) {
            TaskTableRaw raw = DataKeeper.getTaskTaskTableRaws().get(i);
            if(raw.getTo().isSelected() && !raw.getText().getText().isEmpty()){
                selectedNotEmpty+=1;
            }
            if(!raw.getText().getText().isEmpty()){
                notEmptyTasks +=1;
            }
        }
        currProgressValue = selectedNotEmpty / notEmptyTasks;
        if (currProgressValue.isNaN()){
            currProgressValue = 0.;
        }
        System.out.println("using controller instance");
        Controller.getInstance().displayProgress(currProgressValue);
    }
}
