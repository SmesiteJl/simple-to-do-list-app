package com.smesitejl.service.mainstageservice;

import javafx.application.Platform;
import javafx.scene.control.Label;
import lombok.Getter;

import java.util.Timer;
import java.util.TimerTask;

public class TimerTaskControllerService {
    @Getter
    private long seconds;
    private Timer timer;
    private TimerTask timerTask;
    @Getter
    private boolean paused;

    public TimerTaskControllerService() {
        this.paused = false;
    }
    public void setTimer(Timer timer) {
        this.timer = timer;
    }
    public void setPause(boolean pause) {
        this.paused = pause;
    }
    public void cancel() {
        if (timer != null) {
            timer.cancel();
        }
    }
    public void setTimerTask(TimerTask timerTask) {
        this.timerTask = timerTask;
    }

    public void setSeconds(long seconds){
        this.seconds = seconds;
    }

    public TimerTaskControllerService createTimer(Label timerLabel){
        Timer timer = new Timer();
        TimerTaskControllerService timerTaskControllerService = new TimerTaskControllerService();
        TimerTask timerTask = new TimerTask() {
            private int seconds = 0;
            @Override
            public void run() {
                if (!timerTaskControllerService.isPaused()) {
                    timerTaskControllerService.setSeconds(timerTaskControllerService.getSeconds() + 1);
                    long seconds = timerTaskControllerService.getSeconds();
                    long hours = seconds / 3600;
                    long minutes = (seconds % 3600) / 60;
                    long secs = seconds % 60;
                    String timeString = String.format("%02d:%02d:%02d", hours, minutes, secs);
                    Platform.runLater(() -> timerLabel.setText(timeString));
                }
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
        timerTaskControllerService.setTimer(timer);
        timerTaskControllerService.setTimerTask(timerTask);
        return timerTaskControllerService;
    }
}