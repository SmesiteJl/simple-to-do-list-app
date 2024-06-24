package com.smesitejl.service;

import com.smesitejl.entitys.TaskTableRow;
import com.smesitejl.repository.DataKeeper;
import com.smesitejl.controller.Controller;
import com.smesitejl.repository.StyleProvider;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskTableService {

    private static final String TIME_FORMAT= "00:00:00";

    private enum timerStatus {
        IN_PROGRESS,
        PAUSED
    }

    private timerStatus taskTimerStatus = timerStatus.PAUSED;

    public void addTaskTableRow(){
        DataKeeper.getInstance().addTaskTableRow(getDefaultRowTableRow());
    }
    
    public void addTaskTableRow(Boolean to, String text, String time){
        TaskTableRow row = getDefaultRowTableRow();
        row.getTo().setSelected(to);
        row.getText().setText(text);
        if(row.getTo().isSelected()){
            row.getText().setEditable(false);
        }
        row.getTime().setText(time);
        row.getTimerTaskControllerService().setSeconds(secondsCounter(row.getTime().getText()));
        DataKeeper.getInstance().addTaskTableRow(row);
    }
    

    public void removeTaskTableRow(TaskTableRow row){
        DataKeeper.getInstance().removeTaskTableRow(row);
        Controller.getInstance().displayTaskTable();
        Controller.getInstance().updateProgress();

    }

    private TaskTableRow getDefaultRowTableRow(){
        TaskTableRow row = new TaskTableRow();
        //timer mapping
        row.getTime().setText(TIME_FORMAT);
        row.getTimerTaskControllerService().setSeconds(0);
        row.getTimerTaskControllerService().setPause(true);
        taskTimerStatus = timerStatus.PAUSED;

        //toHistory Button logic
        row.getHistory().setOnAction(actionEvent8 -> {
            if (row.getTo().isSelected()) {
                Controller.getInstance().addHistoryTableRow(getUnStrikethroughText(row.getText().getText()),row.getTime().getText());
            }
        });
        StyleProvider.getInstance().getHistoryButtonStyle(row.getHistory());
        //checkBox logic
        row.getTo().setOnAction(actionEvent2 -> {
            Controller.getInstance().updateProgress();
            String textValue = row.getText().getText().trim();
            if(row.getTo().isSelected() && !row.getText().getText().trim().isEmpty()) {
                row.getTimerTaskControllerService().setPause(true);
                StyleProvider.getInstance().getStartupButtonStyleOnPauseCondition(row.getStartup());

                taskTimerStatus = timerStatus.PAUSED;
                row.getTimerTaskControllerService().setPause(true);
                row.getText().setText(getStrikethroughText(textValue));
                row.getText().setEditable(false);
            }
            else if (!row.getTo().isSelected() && !row.getText().getText().trim().isEmpty()){
                row.getText().setText(getUnStrikethroughText(textValue));
                row.getText().setEditable(true);
            }
            else if(row.getTo().isSelected() && row.getText().getText().trim().isEmpty()){
                row.getText().setText(row.getText().getText().trim());
                row.getTo().setSelected(false);
                Controller.getInstance().updateProgress();
            }
        });
                /*//check box tooltips
                row.getTo().setOnMouseEntered(action -> {
                                if(row.getText().getText().isEmpty()){
                                        row.getTo().setTooltip(new Tooltip("You cannot perform task without definition"));
                                }
                                else {
                                        row.getTo().setTooltip(new Tooltip("Keep it up"));
                                }*/
        //textField logic
        row.getText().setPromptText("New task...");
        row.getText().setOnKeyTyped(action ->{
            if(row.getText().getText().trim().isEmpty()) {
                row.getTimerTaskControllerService().setPause(true);
                row.getTimerTaskControllerService().setSeconds(0);
                row.getTime().setText(TIME_FORMAT);
                taskTimerStatus = timerStatus.PAUSED;
                StyleProvider.getInstance().getStartupButtonStyleOnPauseCondition(row.getStartup());
            }
            Controller.getInstance().updateProgress();
        });

        //startup button logic
        StyleProvider.getInstance().getStartupButtonStyleOnPauseCondition(row.getStartup());
        row.getStartup().setOnAction(actionEvent3 -> {
            if(!row.getText().getText().trim().isEmpty() && !row.getTo().isSelected() && taskTimerStatus.equals(timerStatus.PAUSED)) {
                row.getTimerTaskControllerService().setPause(false);
                StyleProvider.getInstance().getStartupButtonStyleOnPlayCondition(row.getStartup());
                taskTimerStatus = timerStatus.IN_PROGRESS;
            }
            else if(taskTimerStatus.equals(timerStatus.IN_PROGRESS)){
                StyleProvider.getInstance().getStartupButtonStyleOnPauseCondition(row.getStartup());
                taskTimerStatus = timerStatus.PAUSED;
                row.getTimerTaskControllerService().setPause(true);
            }
        });
        //del button logic
        row.getDel().setOnAction(actionEvent1 -> {
            row.getTimerTaskControllerService().cancel();
            Controller.getInstance().removeTaskTableRow(row);

        });
        //del button animation
        StyleProvider.getInstance().getDelButtonStyle(row.getDel());
        return row;

    }

    private String getStrikethroughText(String text) {
        StringBuilder sb = new StringBuilder();
        sb.append("̵");
        for (int i = 0; i < text.length(); i++) {
            sb.append(text.charAt(i)).append("̵");
        }
        return String.valueOf(sb);
    }
    private String getUnStrikethroughText(String text) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            if(text.charAt(i) != '̵'){
                sb.append(text.charAt(i));
            }
        }
        return String.valueOf(sb);
    }

    private long secondsCounter(String time){
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date reference;
        try {
            reference = dateFormat.parse(TIME_FORMAT);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Date date;
        try {
            date = dateFormat.parse(time);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return (date.getTime() - reference.getTime()) / 1000L;
    }


}
