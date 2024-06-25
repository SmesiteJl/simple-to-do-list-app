package com.smesitejl.entitys.widgetentitys;

import com.smesitejl.service.mainstageservice.TimerTaskControllerService;
import javafx.scene.control.Label;
import lombok.Getter;

@Getter
public class WidgetTableRow {
    private final Label text = new Label();
    private final Label time = new Label();
    TimerTaskControllerService timerTaskControllerService = new TimerTaskControllerService().createTimer(this.time);

    public WidgetTableRow(String text, String time){
        this.text.setText(text);
        this.time.setText(time);

    }
}
