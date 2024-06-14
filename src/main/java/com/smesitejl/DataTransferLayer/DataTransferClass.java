package com.smesitejl.DataTransferLayer;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.smesitejl.controllers.controllerEntitys.HistoryTableRaw;
import com.smesitejl.controllers.controllerEntitys.TableRaw;
import javafx.collections.ObservableList;

import java.io.FileWriter;
import java.io.IOException;

public class DataTransferClass {
    private final String HISTORY_TABLE_PATH = "src/main/resources/data/history.json";
    private final String TABLE_PATH = "src/main/resources/data/currentTasks.json";
    ObservableList<TableRaw> tableRaws;
    ObservableList<HistoryTableRaw> historyTableRaws;
    public DataTransferClass(ObservableList<TableRaw> tableRaws,ObservableList<HistoryTableRaw> historyTableRaws){
        this.tableRaws = tableRaws;
        this.historyTableRaws = historyTableRaws;
    }


    public void unloadHistory(){
        Gson gson = new Gson();
        JsonArray jsonArray = new JsonArray();
        for (int i = 0; i < historyTableRaws.size(); i++) {
            JsonObject jsonObject = new JsonObject();
            HistoryTableRaw raw = historyTableRaws.get(i);
            jsonObject.addProperty("text", raw.getText().getText());
            jsonObject.addProperty("time", raw.getTime().getText());
            jsonObject.addProperty("day", raw.getDate().getText());
            jsonArray.add(jsonObject);
        }
        try (FileWriter writer = new FileWriter(HISTORY_TABLE_PATH)) {
            gson.toJson(jsonArray, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void unloadCurrentTasks() {
        Gson gson = new Gson();
        JsonArray jsonArray = new JsonArray();
        for (int i = 0; i <tableRaws.size(); i++) {
            JsonObject jsonObject = new JsonObject();
            TableRaw raw = tableRaws.get(i);
            jsonObject.addProperty("to", raw.getTo().isSelected());
            jsonObject.addProperty("text", raw.getText().getText());
            jsonObject.addProperty("time", raw.getTime().getText());
            jsonArray.add(jsonObject);
        }
        try (FileWriter writer = new FileWriter(TABLE_PATH)) {
            gson.toJson(jsonArray, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
