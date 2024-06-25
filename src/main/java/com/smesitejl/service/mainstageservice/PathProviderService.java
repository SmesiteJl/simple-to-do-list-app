package com.smesitejl.service.mainstageservice;


import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class PathProviderService {
    private final String path;
    public PathProviderService(){
        try {
            path = new File(URLDecoder.decode(PathProviderService.class.getProtectionDomain().getCodeSource().getLocation().getPath(), "UTF-8")).getParent();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public String getHistoryTablePath() {
        return path + "\\data\\history.json";
    }

    public String getTasksTablePath() {
        return path + "\\data\\currentTasks.json";
    }
}
