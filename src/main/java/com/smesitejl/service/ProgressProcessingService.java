package com.smesitejl.service;

import com.smesitejl.DataKeeper;
import com.smesitejl.entitys.TaskTableRaw;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;

public class ProgressProcessingService {
    public ProgressProcessingService(){

    }
    ContentDisplayService contentDisplayService = new ContentDisplayService();
    public void updateProgress(ProgressBar progressBar, TextField currentProgressText){
        Double selectedNotEmpty = 0.;
        Double notEmptyTasks = 0.;
        for (int i = 0; i < DataKeeper.getTaskTaskTableRaws().size(); i++) {
            TaskTableRaw raw = DataKeeper.getTaskTaskTableRaws().get(i);
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
        contentDisplayService.displayProgress(currProgressValue, progressBar, currentProgressText);
    }
}
