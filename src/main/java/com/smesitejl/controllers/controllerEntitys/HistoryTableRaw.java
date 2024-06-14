package com.smesitejl.controllers.controllerEntitys;

import javafx.scene.control.Button;
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
    private TextField text;
    private TextField time;
    private TextField date;
    private Button delete;
}
