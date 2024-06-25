package com.smesitejl.entitys.mainstageentitys;
import com.smesitejl.service.mainstageservice.TimerTaskControllerService;
import javafx.scene.control.*;
import lombok.*;


@NoArgsConstructor
@Getter
public class TaskTableRow {
        private final Button history = new Button();
        private final CheckBox to = new CheckBox();
        private final TextField text = new TextField();
        private final Label time = new Label();
        private final Button startup = new Button();
        private final Button del = new Button();
        private final TimerTaskControllerService timerTaskControllerService = new TimerTaskControllerService().createTimer(this.time);
}
