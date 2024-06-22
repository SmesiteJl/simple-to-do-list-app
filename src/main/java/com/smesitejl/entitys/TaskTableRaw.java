package com.smesitejl.entitys;
import javafx.scene.control.*;
import lombok.*;


@NoArgsConstructor
@Getter
public class TaskTableRaw {
        private Button history = new Button();
        private CheckBox to = new CheckBox();
        private TextField text = new TextField();
        private Label time = new Label();
        private Button startup = new Button();
        private Button del = new Button();
}
