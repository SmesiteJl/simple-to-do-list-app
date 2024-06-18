package com.smesitejl.service;


import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class DayTimerService {

    private enum timerStatus {
        NOT_STARTED,
        IN_PROGRESS,
        PAUSED;
    }
    private timerStatus daySessionStatus = timerStatus.NOT_STARTED;
    public void createDayTimer(Button startDayButton, Button endDayButton, Label dayTimer){
        //startDay button logic
        startDayButton.setOnMouseEntered(action5 -> startDayButton.setStyle("-fx-background-image: url(icons/sunrise.gif);"));
        startDayButton.setOnMouseExited(action6 -> startDayButton.setStyle("-fx-background-image: url(icons/sunrise.png);"));
        TimerTaskControllerService timerTaskControllerService = new TimerTaskControllerService().createTimer(dayTimer);
        timerTaskControllerService.setPause(true);
        startDayButton.setOnAction(actionEvent5 -> {
            if(daySessionStatus.equals(timerStatus.PAUSED) || daySessionStatus.equals(timerStatus.NOT_STARTED)){
                daySessionStatus = timerStatus.IN_PROGRESS;
                timerTaskControllerService.setPause(false);
                startDayButton.setText("Pause day");
                startDayButton.setStyle("-fx-background-image: url(icons/pauseSun.gif);");
                startDayButton.setOnMouseEntered(action5 -> startDayButton.setStyle("-fx-background-image: url(icons/pauseSun.gif);"));
                startDayButton.setOnMouseExited(action6 -> startDayButton.setStyle("-fx-background-image: url(icons/pauseSun.gif);"));

            }
            else if (daySessionStatus.equals(timerStatus.IN_PROGRESS)){
                daySessionStatus = timerStatus.PAUSED;
                startDayButton.setStyle("-fx-background-image: url(icons/playSun.png);");
                startDayButton.setOnMouseEntered(action5 -> startDayButton.setStyle("-fx-background-image: url(icons/playSun.png);"));
                startDayButton.setOnMouseExited(action6 -> startDayButton.setStyle("-fx-background-image: url(icons/playSun.png);"));
                timerTaskControllerService.setPause(true);
                startDayButton.setText("Start day");
            }
        });

        //endDay button logic
        endDayButton.setOnAction(actionEvent6 -> {
            timerTaskControllerService.setPause(true);
            dayTimer.setText("Start your day now!");
            timerTaskControllerService.setSeconds(0);
            startDayButton.setText("Start day");
            daySessionStatus = timerStatus.NOT_STARTED;
            startDayButton.setStyle("-fx-background-image: url(icons/sunrise.png);");
            startDayButton.setOnMouseEntered(action5 -> startDayButton.setStyle("-fx-background-image: url(icons/sunrise.gif);"));
            startDayButton.setOnMouseExited(action6 -> startDayButton.setStyle("-fx-background-image: url(icons/sunrise.png);"));
            //TODO: сделать вылетающее окно "Сегодня вы проработали HH:MM:SS. Потрясающе!"
        });
        endDayButton.setOnMouseEntered(action4 -> endDayButton.setStyle("-fx-background-image: url(icons/sunset.gif);"));
        endDayButton.setOnMouseExited(action5 -> endDayButton.setStyle("-fx-background-image: url(icons/sunset.png);"));
    }
}
