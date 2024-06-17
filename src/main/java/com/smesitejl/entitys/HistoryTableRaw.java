package com.smesitejl.entitys;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
