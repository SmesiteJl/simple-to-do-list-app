package com.smesitejl.entitys;

import com.smesitejl.DataKeeper;
import com.smesitejl.service.ContentDisplayService;
import com.smesitejl.service.ProgressProcessingService;
import com.smesitejl.service.TimerTaskControllerService;
import javafx.scene.control.*;
import lombok.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class TaskTableRaw {
        @Getter
        private Button history;
        @Getter
        private CheckBox to;
        @Getter
        private TextField text;
        @Getter
        private Label time;
        @Getter
        private Button startup;
        @Getter
        private Button del;

        private final String timeFormat = "00:00:00";

        private final ContentDisplayService contentDisplayService = new ContentDisplayService();
        private final ProgressProcessingService progressProcessingService= new ProgressProcessingService();

        private enum timerStatus {
                IN_PROGRESS,
                PAUSED;
        }

        private timerStatus taskTimerStatus = timerStatus.PAUSED;

        //constructor for reading from json
        public TaskTableRaw(Boolean to, String text, String time, TableView<TaskTableRaw> table, TableView<HistoryTableRaw> historyTable, ProgressBar progressBar, TextField currentProgressText){
                //timer mapping
                this.time = new Label();
                if(time.isEmpty()){
                        this.time.setText(timeFormat);
                }
                else{
                        this.time.setText(time);
                }
                long seconds = 0;
                if(!this.time.getText().isEmpty()) {
                        seconds = secondsCounter(this.time.getText());
                }
                TimerTaskControllerService timerTaskControllerService = new TimerTaskControllerService().createTimer(this.time);
                timerTaskControllerService.setSeconds(seconds); //timer value set
                timerTaskControllerService.setPause(true);

                //toHistory Button logic
                this.history = new Button();
                this.history.setOnAction(actionEvent8 -> {
                        if (this.to.isSelected()) {
                                HistoryTableRaw historyTableRaw = new HistoryTableRaw(getUnStrikethroughText(this.text.getText()),
                                        this.time.getText(),
                                        historyTable);
                                DataKeeper.addHistoryTableRaw(historyTableRaw);
                                contentDisplayService.displayHistoryTable(historyTable);
                        }
                });
                this.history.setOnMouseEntered(action -> this.history.setStyle("-fx-background-image: url(icons/save.gif)"));
                this.history.setOnMouseExited(action -> this.history.setStyle("-fx-background-image: url(icons/save.png)"));

                //checkBox logic
                this.to = new CheckBox();
                this.to.setSelected(to);
                this.to.setOnAction(actionEvent2 -> {
                        progressProcessingService.updateProgress(progressBar, currentProgressText);
                        String textValue = this.text.getText().trim();
                        if(this.to.isSelected() && !this.text.getText().trim().isEmpty()) {
                                timerTaskControllerService.setPause(true);
                                this.startup.setStyle("-fx-background-image: url(icons/play.png)");
                                taskTimerStatus = timerStatus.PAUSED;
                                this.text.setText(getStrikethroughText(textValue));
                        }
                        else if (!this.to.isSelected() && !this.text.getText().trim().isEmpty()){
                                this.text.setText(getUnStrikethroughText(textValue));
                        }
                        else if(this.to.isSelected() && this.text.getText().trim().isEmpty()){
                                this.text.setText(this.text.getText().trim());
                                this.to.setSelected(false);
                                progressProcessingService.updateProgress(progressBar, currentProgressText);
                        }
                });
                /*//check box tooltips
                this.to.setOnMouseEntered(action -> {
                                if(this.text.getText().isEmpty()){
                                        this.to.setTooltip(new Tooltip("You cannot perform task without definition"));
                                }
                                else {
                                        this.to.setTooltip(new Tooltip("Keep it up"));
                                }*/
                //textField logic
                this.text = new TextField();
                this.text.setPromptText("New task...");
                this.text.setText(text);
                this.text.setOnKeyTyped(action -> {
                        progressProcessingService.updateProgress(progressBar, currentProgressText);
                });

                //startup button logic

                this.startup = new Button();
                this.startup.setStyle("-fx-background-image: url(icons/play.png)");
                this.startup.setOnAction(actionEvent3 -> {
                        if(!this.text.getText().isEmpty() && !this.to.isSelected() && taskTimerStatus.equals(timerStatus.PAUSED)) {
                                timerTaskControllerService.setPause(false);
                                this.startup.setStyle("-fx-background-image: url(icons/pause.png)");
                                this.startup.setOnMouseEntered(action -> this.startup.setStyle("-fx-background-image: url(icons/pause.gif)"));
                                this.startup.setOnMouseExited(action -> this.startup.setStyle("-fx-background-image: url(icons/pause.png)"));
                                taskTimerStatus = timerStatus.IN_PROGRESS;
                        }
                        else if(taskTimerStatus.equals(timerStatus.IN_PROGRESS)){
                                this.startup.setStyle("-fx-background-image: url(icons/play.png)");
                                this.startup.setOnMouseEntered(action -> this.startup.setStyle("-fx-background-image: url(icons/play.gif)"));
                                this.startup.setOnMouseExited(action -> this.startup.setStyle("-fx-background-image: url(icons/play.png)"));
                                taskTimerStatus = timerStatus.PAUSED;
                                timerTaskControllerService.setPause(true);
                        }
                });
                this.startup.setOnMouseEntered(action -> this.startup.setStyle("-fx-background-image: url(icons/play.gif)"));
                this.startup.setOnMouseExited(action -> this.startup.setStyle("-fx-background-image: url(icons/play.png)"));

                //del button logic
                this.del = new Button();
                this.del.setOnAction(actionEvent1 -> {
                        timerTaskControllerService.cancel();
                        DataKeeper.removeTaskTableRaw(this);
                        table.setItems(DataKeeper.getTaskTaskTableRaws()); //TODO: to ContentDisplaySevise
                        progressProcessingService.updateProgress(progressBar, currentProgressText);
                });
                //del button animation
                this.del.setOnMouseEntered(action -> this.del.setStyle("-fx-background-image: url(icons/bin.gif)"));
                this.del.setOnMouseExited(action -> this.del.setStyle("-fx-background-image: url(icons/bin.png)"));
                
        }
        //constructor for creating a new raw by button
        public TaskTableRaw(TableView<TaskTableRaw> table, TableView<HistoryTableRaw> historyTable, ProgressBar progressBar, TextField currentProgressText){
                //timer mapping
                this.time = new Label();
                this.time.setText(timeFormat);
                long seconds = 0;
                TimerTaskControllerService timerTaskControllerService = new TimerTaskControllerService().createTimer(this.time);
                timerTaskControllerService.setSeconds(seconds); //timer value set
                timerTaskControllerService.setPause(true);

                //toHistory Button logic
                this.history = new Button();
                this.history.setOnAction(actionEvent8 -> {
                        if (this.to.isSelected()) {
                                HistoryTableRaw historyTableRaw = new HistoryTableRaw(getUnStrikethroughText(this.text.getText()),
                                        this.time.getText(),
                                        historyTable);
                                DataKeeper.addHistoryTableRaw(historyTableRaw);
                                contentDisplayService.displayHistoryTable(historyTable);
                        }
                });
                this.history.setOnMouseEntered(action -> this.history.setStyle("-fx-background-image: url(icons/save.gif)"));
                this.history.setOnMouseExited(action -> this.history.setStyle("-fx-background-image: url(icons/save.png)"));

                //checkBox logic
                this.to = new CheckBox();
                this.to.setOnAction(actionEvent2 -> {
                        progressProcessingService.updateProgress(progressBar, currentProgressText);
                        String textValue = this.text.getText().trim();
                        if(this.to.isSelected() && !this.text.getText().trim().isEmpty()) {
                                timerTaskControllerService.setPause(true);
                                this.startup.setStyle("-fx-background-image: url(icons/play.png)");
                                taskTimerStatus = timerStatus.PAUSED;
                                this.text.setText(getStrikethroughText(textValue));
                        }
                        else if (!this.to.isSelected() && !this.text.getText().trim().isEmpty()){
                                this.text.setText(getUnStrikethroughText(textValue));
                        }
                        else if(this.to.isSelected() && this.text.getText().trim().isEmpty()){
                                this.text.setText(this.text.getText().trim());
                                this.to.setSelected(false);
                                progressProcessingService.updateProgress(progressBar, currentProgressText);
                        }
                });
                /*//check box tooltips
                this.to.setOnMouseEntered(action -> {
                                if(this.text.getText().isEmpty()){
                                        this.to.setTooltip(new Tooltip("You cannot perform task without definition"));
                                }
                                else {
                                        this.to.setTooltip(new Tooltip("Keep it up"));
                                }*/
                //textField logic
                this.text = new TextField();
                this.text.setPromptText("New task...");
                this.text.setOnKeyTyped(action -> {
                        progressProcessingService.updateProgress(progressBar, currentProgressText);
                });

                //startup button logic

                this.startup = new Button();
                this.startup.setStyle("-fx-background-image: url(icons/play.png)");
                this.startup.setOnAction(actionEvent3 -> {
                        if(!this.text.getText().isEmpty() && !this.to.isSelected() && taskTimerStatus.equals(timerStatus.PAUSED)) {
                                timerTaskControllerService.setPause(false);
                                this.startup.setStyle("-fx-background-image: url(icons/pause.png)");
                                this.startup.setOnMouseEntered(action -> this.startup.setStyle("-fx-background-image: url(icons/pause.gif)"));
                                this.startup.setOnMouseExited(action -> this.startup.setStyle("-fx-background-image: url(icons/pause.png)"));
                                taskTimerStatus = timerStatus.IN_PROGRESS;
                        }
                        else if(taskTimerStatus.equals(timerStatus.IN_PROGRESS)){
                                this.startup.setStyle("-fx-background-image: url(icons/play.png)");
                                this.startup.setOnMouseEntered(action -> this.startup.setStyle("-fx-background-image: url(icons/play.gif)"));
                                this.startup.setOnMouseExited(action -> this.startup.setStyle("-fx-background-image: url(icons/play.png)"));
                                taskTimerStatus = timerStatus.PAUSED;
                                timerTaskControllerService.setPause(true);
                        }
                });
                this.startup.setOnMouseEntered(action -> this.startup.setStyle("-fx-background-image: url(icons/play.gif)"));
                this.startup.setOnMouseExited(action -> this.startup.setStyle("-fx-background-image: url(icons/play.png)"));

                //del button logic
                this.del = new Button();
                this.del.setOnAction(actionEvent1 -> {
                        timerTaskControllerService.cancel();
                        DataKeeper.removeTaskTableRaw(this);
                        table.setItems(DataKeeper.getTaskTaskTableRaws());
                        progressProcessingService.updateProgress(progressBar, currentProgressText);
                });
                //del button animation
                this.del.setOnMouseEntered(action -> this.del.setStyle("-fx-background-image: url(icons/bin.gif)"));
                this.del.setOnMouseExited(action -> this.del.setStyle("-fx-background-image: url(icons/bin.png)"));
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
