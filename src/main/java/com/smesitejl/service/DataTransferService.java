package com.smesitejl.service;

import com.google.gson.*;
import com.smesitejl.controller.Controller;
import com.smesitejl.entitys.HistoryTableRaw;
import com.smesitejl.entitys.TaskTableRaw;
import com.smesitejl.DataKeeper;
import javafx.collections.ObservableList;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.*;

public class DataTransferService {
    private final PathProviderService pathProviderService = new PathProviderService();
    private final ObservableList<TaskTableRaw> taskTableRaws = DataKeeper.getInstance().getTaskTaskTableRaws();
    private final ObservableList<HistoryTableRaw> historyTableRaws = DataKeeper.getInstance().getHistoryTableRaws();
    private final PathProviderService path = new PathProviderService();






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
        for (int i = 0; i < taskTableRaws.size(); i++) {
            JsonObject jsonObject = new JsonObject();
            TaskTableRaw raw = taskTableRaws.get(i);
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

    public void downloadCurrentTasks() {
        try (BufferedReader reader = new BufferedReader(new FileReader(path.getTasksTablePath()))) {
            JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();
            for (JsonElement elem : jsonArray) {
                Controller.getInstance().addTaskTableRaw(elem.getAsJsonObject().get("to").getAsBoolean(),
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
                Controller.getInstance().addHistoryTableRaw(elem.getAsJsonObject().get("text").getAsString(),
                        elem.getAsJsonObject().get("time").getAsString(),
                        elem.getAsJsonObject().get("day").getAsString());
                           }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
