package com.smesitejl.context;

import com.smesitejl.service.*;
import lombok.Getter;

@Getter
public class ApplicationContext {

    private static ApplicationContext instance;

    private final ContentDisplayService contentDisplayService;
    private final DataTransferService dataTransferService;
    private final TableMapperService tableMapperService;
    private final ProgressProcessingService progressProcessingService;
    private final DayTimerService dayTimerService;

    public static ApplicationContext getInstance(){
        if (instance == null) {
            instance = new ApplicationContext();
        }
        return instance;
    }

    private ApplicationContext(){
        contentDisplayService = new ContentDisplayService();
        dataTransferService = new DataTransferService();
        tableMapperService = new TableMapperService();
        progressProcessingService = new ProgressProcessingService();
        dayTimerService = new DayTimerService();
    }
}
