package com.smesitejl.controllers.controllerEntitys;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
public class TableRaw {
        private Button history;
        private CheckBox to;
        private TextField text;
        private Label time;
        private Button startup;
        private Button del;
}
