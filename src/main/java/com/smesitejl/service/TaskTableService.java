package com.smesitejl.service;

import com.smesitejl.DataKeeper;
import com.smesitejl.controller.Controller;
import com.smesitejl.entitys.TaskTableRaw;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskTableService {

    private final String timeFormat = "00:00:00";

    private enum timerStatus {
        IN_PROGRESS,
        PAUSED;
    }

    private timerStatus taskTimerStatus = timerStatus.PAUSED;

    public void  addTaskTableRaw(){
        TaskTableRaw raw = new TaskTableRaw();
        //timer mapping
        raw.getTime().setText(timeFormat);
        long seconds = 0;
        TimerTaskControllerService timerTaskControllerService = new TimerTaskControllerService().createTimer(raw.getTime());
        timerTaskControllerService.setSeconds(seconds); //timer value set
        timerTaskControllerService.setPause(true);

        //toHistory Button logic
        raw.getHistory().setOnAction(actionEvent8 -> {
            if (raw.getTo().isSelected()) {
                Controller.getInstance().addHistoryTableRaw(getUnStrikethroughText(raw.getText().getText()),raw.getTime().getText());
            }
        });
        raw.getHistory().setOnMouseEntered(action -> raw.getHistory().setStyle("-fx-background-image: url(icons/save.gif)"));
        raw.getHistory().setOnMouseExited(action -> raw.getHistory().setStyle("-fx-background-image: url(icons/save.png)"));

        //checkBox logic
        raw.getTo().setOnAction(actionEvent2 -> {
            Controller.getInstance().updateProgress();
            String textValue = raw.getText().getText().trim();
            if(raw.getTo().isSelected() && !raw.getText().getText().trim().isEmpty()) {
                timerTaskControllerService.setPause(true);
                raw.getStartup().setStyle("-fx-background-image: url(icons/play.png)");
                taskTimerStatus = timerStatus.PAUSED;
                raw.getText().setText(getStrikethroughText(textValue));
            }
            else if (!raw.getTo().isSelected() && !raw.getText().getText().trim().isEmpty()){
                raw.getText().setText(getUnStrikethroughText(textValue));
            }
            else if(raw.getTo().isSelected() && raw.getText().getText().trim().isEmpty()){
                raw.getText().setText(raw.getText().getText().trim());
                raw.getTo().setSelected(false);
                Controller.getInstance().updateProgress();
            }
        });
                /*//check box tooltips
                raw.getTo().setOnMouseEntered(action -> {
                                if(raw.getText().getText().isEmpty()){
                                        raw.getTo().setTooltip(new Tooltip("You cannot perform task without definition"));
                                }
                                else {
                                        raw.getTo().setTooltip(new Tooltip("Keep it up"));
                                }*/
        //textField logic
        raw.getText().setPromptText("New task...");
        raw.getText().setOnKeyTyped(action -> {
            Controller.getInstance().updateProgress();
        });

        //startup button logic
        raw.getStartup().setStyle("-fx-background-image: url(icons/play.png)");
        raw.getStartup().setOnAction(actionEvent3 -> {
            if(!raw.getText().getText().isEmpty() && !raw.getTo().isSelected() && taskTimerStatus.equals(timerStatus.PAUSED)) {
                timerTaskControllerService.setPause(false);
                raw.getStartup().setStyle("-fx-background-image: url(icons/pause.png)");
                raw.getStartup().setOnMouseEntered(action -> raw.getStartup().setStyle("-fx-background-image: url(icons/pause.gif)"));
                raw.getStartup().setOnMouseExited(action -> raw.getStartup().setStyle("-fx-background-image: url(icons/pause.png)"));
                taskTimerStatus = timerStatus.IN_PROGRESS;
            }
            else if(taskTimerStatus.equals(timerStatus.IN_PROGRESS)){
                raw.getStartup().setStyle("-fx-background-image: url(icons/play.png)");
                raw.getStartup().setOnMouseEntered(action -> raw.getStartup().setStyle("-fx-background-image: url(icons/play.gif)"));
                raw.getStartup().setOnMouseExited(action -> raw.getStartup().setStyle("-fx-background-image: url(icons/play.png)"));
                taskTimerStatus = timerStatus.PAUSED;
                timerTaskControllerService.setPause(true);
            }
        });
        raw.getStartup().setOnMouseEntered(action -> raw.getStartup().setStyle("-fx-background-image: url(icons/play.gif)"));
        raw.getStartup().setOnMouseExited(action -> raw.getStartup().setStyle("-fx-background-image: url(icons/play.png)"));

        //del button logic
        raw.getDel().setOnAction(actionEvent1 -> {
            timerTaskControllerService.cancel();
            Controller.getInstance().removeTaskTableRaw(raw);

        });
        //del button animation
        raw.getDel().setOnMouseEntered(action -> raw.getDel().setStyle("-fx-background-image: url(icons/bin.gif)"));
        raw.getDel().setOnMouseExited(action -> raw.getDel().setStyle("-fx-background-image: url(icons/bin.png)"));
        DataKeeper.getInstance().addTaskTableRaw(raw);
    }
    
    public void addTaskTableRaw(Boolean to, String text, String time){
        TaskTableRaw raw = new TaskTableRaw();
        //timer mapping
        if(time.isEmpty()){
            raw.getTime().setText(timeFormat);
        }
        else{
            raw.getTime().setText(time);
        }
        long seconds = 0;
        if(!raw.getTime().getText().isEmpty()) {
            seconds = secondsCounter(raw.getTime().getText());
        }
        TimerTaskControllerService timerTaskControllerService = new TimerTaskControllerService().createTimer(raw.getTime());
        timerTaskControllerService.setSeconds(seconds); //timer value set
        timerTaskControllerService.setPause(true);

        //toHistory Button logic
        raw.getHistory().setOnAction(actionEvent8 -> {
            if (raw.getTo().isSelected()) {
                Controller.getInstance().addHistoryTableRaw(getUnStrikethroughText(raw.getText().getText()),raw.getTime().getText());
            }
        });
        raw.getHistory().setOnMouseEntered(action -> raw.getHistory().setStyle("-fx-background-image: url(icons/save.gif)"));
        raw.getHistory().setOnMouseExited(action -> raw.getHistory().setStyle("-fx-background-image: url(icons/save.png)"));

        //checkBox logic
        raw.getTo().setSelected(to);
        raw.getTo().setOnAction(actionEvent2 -> {
            Controller.getInstance().updateProgress();
            String textValue = raw.getText().getText().trim();
            if(raw.getTo().isSelected() && !raw.getText().getText().trim().isEmpty()) {
                timerTaskControllerService.setPause(true);
                raw.getStartup().setStyle("-fx-background-image: url(icons/play.png)");
                taskTimerStatus = timerStatus.PAUSED;
                raw.getText().setText(getStrikethroughText(textValue));
            }
            else if (!raw.getTo().isSelected() && !raw.getText().getText().trim().isEmpty()){
                raw.getText().setText(getUnStrikethroughText(textValue));
            }
            else if(raw.getTo().isSelected() && raw.getText().getText().trim().isEmpty()){
                raw.getText().setText(raw.getText().getText().trim());
                raw.getTo().setSelected(false);
                Controller.getInstance().updateProgress();
            }
        });
                /*//check box tooltips
                raw.getTo().setOnMouseEntered(action -> {
                                if(raw.getText().getText().isEmpty()){
                                        raw.getTo().setTooltip(new Tooltip("You cannot perform task without definition"));
                                }
                                else {
                                        raw.getTo().setTooltip(new Tooltip("Keep it up"));
                                }*/
        //textField logic
        raw.getText().setPromptText("New task...");
        raw.getText().setText(text);
        raw.getText().setOnKeyTyped(action -> {
            Controller.getInstance().updateProgress();
        });

        //startup button logic
        raw.getStartup().setStyle("-fx-background-image: url(icons/play.png)");
        raw.getStartup().setOnAction(actionEvent3 -> {
            if(!raw.getText().getText().isEmpty() && !raw.getTo().isSelected() && taskTimerStatus.equals(timerStatus.PAUSED)) {
                timerTaskControllerService.setPause(false);
                raw.getStartup().setStyle("-fx-background-image: url(icons/pause.png)");
                raw.getStartup().setOnMouseEntered(action -> raw.getStartup().setStyle("-fx-background-image: url(icons/pause.gif)"));
                raw.getStartup().setOnMouseExited(action -> raw.getStartup().setStyle("-fx-background-image: url(icons/pause.png)"));
                taskTimerStatus = timerStatus.IN_PROGRESS;
            }
            else if(taskTimerStatus.equals(timerStatus.IN_PROGRESS)){
                raw.getStartup().setStyle("-fx-background-image: url(icons/play.png)");
                raw.getStartup().setOnMouseEntered(action -> raw.getStartup().setStyle("-fx-background-image: url(icons/play.gif)"));
                raw.getStartup().setOnMouseExited(action -> raw.getStartup().setStyle("-fx-background-image: url(icons/play.png)"));
                taskTimerStatus = timerStatus.PAUSED;
                timerTaskControllerService.setPause(true);
            }
        });
        raw.getStartup().setOnMouseEntered(action -> raw.getStartup().setStyle("-fx-background-image: url(icons/play.gif)"));
        raw.getStartup().setOnMouseExited(action -> raw.getStartup().setStyle("-fx-background-image: url(icons/play.png)"));

        //del button logic
        raw.getDel().setOnAction(actionEvent1 -> {
            timerTaskControllerService.cancel();
            Controller.getInstance().removeTaskTableRaw(raw);
        });
        //del button animation
        raw.getDel().setOnMouseEntered(action -> raw.getDel().setStyle("-fx-background-image: url(icons/bin.gif)"));
        raw.getDel().setOnMouseExited(action -> raw.getDel().setStyle("-fx-background-image: url(icons/bin.png)"));
        DataKeeper.getInstance().addTaskTableRaw(raw);
    }
    

    public void removeTaskTableRaw(TaskTableRaw raw){
        DataKeeper.getInstance().removeTaskTableRaw(raw);
        Controller.getInstance().displayTaskTable();
        Controller.getInstance().updateProgress();

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
        Date reference = null;
        try {
            reference = dateFormat.parse(timeFormat);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Date date = null;
        try {
            date = dateFormat.parse(time);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return (date.getTime() - reference.getTime()) / 1000L;
    }


}
