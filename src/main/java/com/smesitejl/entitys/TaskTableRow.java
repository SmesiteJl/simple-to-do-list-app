package com.smesitejl.entitys;
import com.smesitejl.service.TimerTaskControllerService;
import javafx.scene.control.*;
import lombok.*;


@NoArgsConstructor
@Getter
public class TaskTableRow {
        private Button history = new Button();
        private CheckBox to = new CheckBox();
        private TextField text = new TextField();
        private Label time = new Label();
        private Button startup = new Button();
        private Button del = new Button();
        TimerTaskControllerService timerTaskControllerService = new TimerTaskControllerService().createTimer(this.time);
}
