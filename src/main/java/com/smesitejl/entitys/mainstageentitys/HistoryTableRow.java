package com.smesitejl.entitys.mainstageentitys;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HistoryTableRow {
    private Label text = new Label();
    private Label time = new Label();
    private Label date = new Label();
    private Button delete = new Button();
}
