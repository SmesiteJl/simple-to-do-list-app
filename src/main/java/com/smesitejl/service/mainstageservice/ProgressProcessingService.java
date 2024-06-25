package com.smesitejl.service.mainstageservice;

import com.smesitejl.entitys.mainstageentitys.TaskTableRow;
import com.smesitejl.repository.DataKeeper;
import com.smesitejl.controller.Controller;

public class ProgressProcessingService {
    private double currProgressValue;
    private double prevProgressValue;

    public void updateProgress(){
        prevProgressValue = currProgressValue;
        double selectedNotEmpty = 0.;
        double notEmptyTasks = 0.;
        for (int i = 0; i < DataKeeper.getInstance().getTaskTaskTableRows().size(); i++) {
            TaskTableRow row = DataKeeper.getInstance().getTaskTaskTableRows().get(i);
            if(row.getTo().isSelected() && !row.getText().getText().isEmpty()){
                selectedNotEmpty+=1;
            }
            if(!row.getText().getText().isEmpty()){
                notEmptyTasks +=1;
            }
        }
        if(notEmptyTasks == 0.){
            currProgressValue = 0.;
        }
        else{
            currProgressValue = selectedNotEmpty / notEmptyTasks;
        }

        Controller.getInstance().displayProgress(currProgressValue, prevProgressValue);
    }
}
