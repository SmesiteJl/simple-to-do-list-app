package com.smesitejl.entitys;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HistoryTableRaw {
    private Label text = new Label();
    private Label time = new Label();
    private Label date = new Label();
    private Button delete = new Button();
}
