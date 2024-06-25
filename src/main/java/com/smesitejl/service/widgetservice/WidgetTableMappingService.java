package com.smesitejl.service.widgetservice;

import com.smesitejl.entitys.widgetentitys.WidgetTableRow;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class WidgetTableMappingService {
    public void doWidgetTableMapping(TableView<WidgetTableRow> table,
                                     TableColumn<WidgetTableRow, Label> taskColumn,
                                     TableColumn<WidgetTableRow, Label> timeColumn){
        table.setSelectionModel(null);
        taskColumn.setCellValueFactory(new PropertyValueFactory<>("text"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        timeColumn.setPrefWidth(70);
        taskColumn.prefWidthProperty().bind(table.widthProperty().subtract(timeColumn.getPrefWidth()));
    }
}
