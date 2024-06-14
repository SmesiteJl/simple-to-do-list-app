package com.smesitejl.controllers;

import lombok.Getter;

import java.util.Timer;
import java.util.TimerTask;

public class TimerTaskController {
    @Getter
    private long seconds;
    private Timer timer;
    private TimerTask timerTask;
    @Getter
    private boolean paused;

    public TimerTaskController() {
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