package com.smesitejl.service;

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
}