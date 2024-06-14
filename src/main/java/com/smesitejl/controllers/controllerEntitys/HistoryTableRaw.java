package com.smesitejl.controllers.controllerEntitys;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HistoryTableRaw {
    private Label text;
    private Label time;
    private Label date;
    private Button delete;
}
