package com.smesitejl.service;

import com.google.gson.*;
import com.smesitejl.controller.Controller;
import com.smesitejl.entitys.HistoryTableRow;
import com.smesitejl.entitys.TaskTableRow;
import com.smesitejl.repository.DataKeeper;
import javafx.collections.ObservableList;

import java.io.*;

public class DataTransferService {
    private final PathProviderService pathProviderService = new PathProviderService();
    private final ObservableList<TaskTableRow> taskTablerows = DataKeeper.getInstance().getTaskTaskTableRows();
    private final ObservableList<HistoryTableRow> historyTablerows = DataKeeper.getInstance().getHistoryTableRows();
    private final PathProviderService path = new PathProviderService();






    public void unloadHistory(){
        Gson gson = new Gson();
        JsonArray jsonArray = new JsonArray();
        for (int i = 0; i < historyTablerows.size(); i++) {
            JsonObject jsonObject = new JsonObject();
            HistoryTableRow row = historyTablerows.get(i);
            jsonObject.addProperty("text", row.getText().getText());
            jsonObject.addProperty("time", row.getTime().getText());
            jsonObject.addProperty("day", row.getDate().getText());
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
        for (int i = 0; i < taskTablerows.size(); i++) {
            JsonObject jsonObject = new JsonObject();
            TaskTableRow row = taskTablerows.get(i);
            if(!row.getText().getText().trim().isEmpty()){
            jsonObject.addProperty("to", row.getTo().isSelected());
            jsonObject.addProperty("text", row.getText().getText());
            jsonObject.addProperty("time", row.getTime().getText());
            jsonArray.add(jsonObject);
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathProviderService.getTasksTablePath()))) {
            gson.toJson(jsonArray, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void downloadCurrentTasks() {
        try (BufferedReader reader = new BufferedReader(new FileReader(path.getTasksTablePath()))) {
            JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();
            for (JsonElement elem : jsonArray) {
                Controller.getInstance().addTaskTablerow(elem.getAsJsonObject().get("to").getAsBoolean(),
                        elem.getAsJsonObject().get("text").getAsString(),
                        elem.getAsJsonObject().get("time").getAsString());

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void downloadHistory(){
        try (BufferedReader reader = new BufferedReader(new FileReader(path.getHistoryTablePath()))) {
            JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();
            for (JsonElement elem : jsonArray) {
                Controller.getInstance().addHistoryTablerow(elem.getAsJsonObject().get("text").getAsString(),
                        elem.getAsJsonObject().get("time").getAsString(),
                        elem.getAsJsonObject().get("day").getAsString());
                           }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
