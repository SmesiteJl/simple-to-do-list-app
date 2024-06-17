package com.smesitejl.service.DataTransferService;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.smesitejl.entitys.HistoryTableRaw;
import com.smesitejl.entitys.TableRaw;
import com.smesitejl.service.PathProviderService;
import javafx.collections.ObservableList;

import java.io.*;

public class DataTransferImpl {
    private final PathProviderService pathProviderService = new PathProviderService();
    ObservableList<TableRaw> tableRaws;
    ObservableList<HistoryTableRaw> historyTableRaws;
    public DataTransferImpl(ObservableList<TableRaw> tableRaws, ObservableList<HistoryTableRaw> historyTableRaws){
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
            jsonArray.add(jsonObject);        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathProviderService.getHistoryTablePath()))) {
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
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathProviderService.getTasksTablePath()))) {
            gson.toJson(jsonArray, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
