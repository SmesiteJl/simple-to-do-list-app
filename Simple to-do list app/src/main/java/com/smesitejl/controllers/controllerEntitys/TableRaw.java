package com.smesitejl.controllers.controllerEntitys;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class TableRaw {
        private CheckBox to;
        private TextField text;
        private Button del;
}
