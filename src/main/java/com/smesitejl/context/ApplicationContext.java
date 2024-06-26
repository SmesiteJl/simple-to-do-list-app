package com.smesitejl.context;

import com.smesitejl.service.mainstageservice.*;
import com.smesitejl.service.widgetservice.WidgetTableMappingService;
import lombok.Getter;

@Getter
public class ApplicationContext {

    private static ApplicationContext instance;
    private final DataTransferService dataTransferService;
    private final TableMapperService tableMapperService;
    private final ProgressProcessingService progressProcessingService;
    private final DayTimerService dayTimerService;
    private final HistoryTableService historyTableService;
    private final TaskTableService taskTableService;
    private final WidgetTableMappingService widgetTableMappingService;


    public static ApplicationContext getInstance(){
        if (instance == null) {
            instance = new ApplicationContext();
        }
        return instance;
    }

    private ApplicationContext(){
        taskTableService = new TaskTableService();
        historyTableService = new HistoryTableService();
        dataTransferService = new DataTransferService();
        tableMapperService = new TableMapperService();
        progressProcessingService = new ProgressProcessingService();
        dayTimerService = new DayTimerService();
        widgetTableMappingService = new WidgetTableMappingService();
    }
}
