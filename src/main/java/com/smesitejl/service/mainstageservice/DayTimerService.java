package com.smesitejl.service.mainstageservice;


import com.smesitejl.repository.StyleProvider;
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
        StyleProvider.getInstance().getStartDayButtonStyleOnStartCondition(startDayButton);
        TimerTaskControllerService timerTaskControllerService = new TimerTaskControllerService().createTimer(dayTimer);
        timerTaskControllerService.setPause(true);
        startDayButton.setOnAction(actionEvent5 -> {
            if(daySessionStatus.equals(timerStatus.PAUSED) || daySessionStatus.equals(timerStatus.NOT_STARTED)){
                daySessionStatus = timerStatus.IN_PROGRESS;
                timerTaskControllerService.setPause(false);
                startDayButton.setText("Pause day");
                StyleProvider.getInstance().getStartDayButtonStyleOnPlayCondition(startDayButton);

            }
            else if (daySessionStatus.equals(timerStatus.IN_PROGRESS)){
                daySessionStatus = timerStatus.PAUSED;
                StyleProvider.getInstance().getStartDayButtonStyleOnPauseCondition(startDayButton);
                timerTaskControllerService.setPause(true);
                startDayButton.setText("Start day");
            }
        });

        //endDay button logic
        StyleProvider.getInstance().getEndDayButtonStyle(endDayButton);
        endDayButton.setOnAction(actionEvent6 -> {
            timerTaskControllerService.setPause(true);
            dayTimer.setText("Start your day now!");
            timerTaskControllerService.setSeconds(0);
            startDayButton.setText("Start day");
            daySessionStatus = timerStatus.NOT_STARTED;
            StyleProvider.getInstance().getStartDayButtonStyleOnStartCondition(startDayButton);
            //TODO: сделать вылетающее окно "Сегодня вы проработали HH:MM:SS. Потрясающе!"
        });
    }
    public boolean getIsTimerPaused(){
        switch (daySessionStatus){
            case NOT_STARTED:
            case PAUSED:
                return true;
            default:
                return false;
        }
    }
}
