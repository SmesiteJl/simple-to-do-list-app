package com.smesitejl.repository;

import javafx.scene.control.Button;

public class StyleProvider {

    private static StyleProvider instance;

    private static final String STARTUP_BUTTON_STYLE_ON_PAUSE_CONDITION_PNG = "-fx-background-image: url(icons/play.png);";
    private static final String STARTUP_BUTTON_STYLE_ON_PAUSE_CONDITION_GIF = "-fx-background-image: url(icons/play.gif);";
    private static final String STARTUP_BUTTON_STYLE_ON_PLAY_CONDITION_PNG = "-fx-background-image: url(icons/pause.png);";
    private static final String STARTUP_BUTTON_STYLE_ON_PLAY_CONDITION_GIF = "-fx-background-image: url(icons/pause.gif);";
    private static final String HISTORY_BUTTON_STYLE_PNG = "-fx-background-image: url(icons/save.png);";
    private static final String HISTORY_BUTTON_STYLE_GIF = "-fx-background-image: url(icons/save.gif);";
    private static final String DEL_BUTTON_STYLE_PNG = "-fx-background-image: url(icons/bin.png);";
    private static final String DEL_BUTTON_STYLE_GIF = "-fx-background-image: url(icons/bin.gif);";
    private static final String START_DAY_BUTTON_STYLE_ON_START_CONDITION_PNG = "-fx-background-image: url(icons/sunrise.png);";
    private static final String START_DAY_BUTTON_STYLE_ON_START_CONDITION_GIF = "-fx-background-image: url(icons/sunrise.gif);";
    private static final String START_DAY_BUTTON_STYLE_ON_PLAY_CONDITION_GIF = "-fx-background-image: url(icons/pauseSun.gif);";
    private static final String START_DAY_BUTTON_STYLE_ON_PAUSE_CONDITION_PNG = "-fx-background-image: url(icons/playSun.png);";
    private static final String END_DAY_BUTTON_STYLE_PNG = "-fx-background-image: url(icons/sunset.png);";
    private static final String END_DAY_BUTTON_STYLE_GIF = "-fx-background-image: url(icons/sunset.gif);";

    private static final String HISTORY_DEL_BUTTON_STYLE_PNG = "-fx-background-image: url(icons/hisBin.png);";
    private static final String HISTORY_DEL_BUTTON_STYLE_GIF = "-fx-background-image: url(icons/hisBin.gif);";

    private static final String PLUS_BUTTON_STYLE_PNG = "-fx-background-image: url(icons/plus.png);";
    private static final String PLUS_BUTTON_STYLE_GIF = "-fx-background-image: url(icons/plus.gif);";

    private static final String COLLAPSE_BUTTON_STYLE_PNG = "-fx-background-image: url(icons/doubleLeft.png);";
    private static final String COLLAPSE_BUTTON_STYLE_GIF = "-fx-background-image: url(icons/doubleLeft.gif);";

    private static final String EXPAND_BUTTON_STYLE_PNG = "-fx-background-image: url(icons/doubleRight.png);";
    private static final String EXPAND_BUTTON_STYLE_GIF = "-fx-background-image: url(icons/doubleRight.gif);";
    private static final String ON_ANY_BUTTON_PRESSED_BACKGROUND_COLOR = "-fx-background-color: LIGHTGRAY;";
    private static final String ON_ANY_BUTTON_RELEASED_BACKGROUND_COLOR = "-fx-background-color: WHITE;";





    public static StyleProvider getInstance(){
        if(instance == null){
            instance = new StyleProvider();
        }
        return instance;
    }

    private StyleProvider(){
    }
    public void getStartupButtonStyleOnPlayCondition(Button button){
        button.setStyle(STARTUP_BUTTON_STYLE_ON_PLAY_CONDITION_PNG);
        button.setOnMouseEntered(action -> button.setStyle(STARTUP_BUTTON_STYLE_ON_PLAY_CONDITION_GIF));
        button.setOnMouseExited(action -> button.setStyle(STARTUP_BUTTON_STYLE_ON_PLAY_CONDITION_PNG));
        button.setOnMousePressed(action -> button.setStyle(STARTUP_BUTTON_STYLE_ON_PLAY_CONDITION_PNG + ON_ANY_BUTTON_PRESSED_BACKGROUND_COLOR));
        button.setOnMouseReleased(action -> button.setStyle(STARTUP_BUTTON_STYLE_ON_PLAY_CONDITION_GIF));

    }
    public void getStartupButtonStyleOnPauseCondition(Button button){
        button.setStyle(STARTUP_BUTTON_STYLE_ON_PAUSE_CONDITION_PNG);
        button.setOnMouseEntered(action -> button.setStyle(STARTUP_BUTTON_STYLE_ON_PAUSE_CONDITION_GIF));
        button.setOnMouseExited(action -> button.setStyle(STARTUP_BUTTON_STYLE_ON_PAUSE_CONDITION_PNG));
        button.setOnMousePressed(action -> button.setStyle(STARTUP_BUTTON_STYLE_ON_PAUSE_CONDITION_PNG + ON_ANY_BUTTON_PRESSED_BACKGROUND_COLOR));
        button.setOnMouseReleased(action -> button.setStyle(STARTUP_BUTTON_STYLE_ON_PAUSE_CONDITION_GIF));

    }

    public void getHistoryButtonStyle(Button button){
        button.setOnMouseEntered(action -> button.setStyle(HISTORY_BUTTON_STYLE_GIF));
        button.setOnMouseExited(action -> button.setStyle(HISTORY_BUTTON_STYLE_PNG));
        button.setOnMousePressed(action -> button.setStyle(HISTORY_BUTTON_STYLE_PNG + ON_ANY_BUTTON_PRESSED_BACKGROUND_COLOR));
        button.setOnMouseReleased(action -> button.setStyle(HISTORY_BUTTON_STYLE_GIF));
    }

    public void getDelButtonStyle(Button button){
        button.setOnMouseEntered(action -> button.setStyle(DEL_BUTTON_STYLE_GIF));
        button.setOnMouseExited(action -> button.setStyle(DEL_BUTTON_STYLE_PNG));
        button.setOnMousePressed(action -> button.setStyle(DEL_BUTTON_STYLE_PNG + ON_ANY_BUTTON_PRESSED_BACKGROUND_COLOR));
        button.setOnMouseReleased(action -> button.setStyle(DEL_BUTTON_STYLE_GIF));
    }

    public void getStartDayButtonStyleOnStartCondition(Button button){
        button.setStyle(START_DAY_BUTTON_STYLE_ON_START_CONDITION_PNG);
        button.setOnMouseEntered(action5 -> button.setStyle(START_DAY_BUTTON_STYLE_ON_START_CONDITION_GIF));
        button.setOnMouseExited(action6 -> button.setStyle(START_DAY_BUTTON_STYLE_ON_START_CONDITION_PNG));
    }
    public void getStartDayButtonStyleOnPlayCondition(Button button){
        button.setStyle(START_DAY_BUTTON_STYLE_ON_PLAY_CONDITION_GIF);
        button.setOnMouseEntered(action5 -> button.setStyle(START_DAY_BUTTON_STYLE_ON_PLAY_CONDITION_GIF));
        button.setOnMouseExited(action6 -> button.setStyle(START_DAY_BUTTON_STYLE_ON_PLAY_CONDITION_GIF));

    }

    public void getStartDayButtonStyleOnPauseCondition(Button button){
        button.setStyle(START_DAY_BUTTON_STYLE_ON_PAUSE_CONDITION_PNG);
        button.setOnMouseEntered(action5 -> button.setStyle(START_DAY_BUTTON_STYLE_ON_PAUSE_CONDITION_PNG));
        button.setOnMouseExited(action6 -> button.setStyle(START_DAY_BUTTON_STYLE_ON_PAUSE_CONDITION_PNG));

    }

    public void getEndDayButtonStyle(Button button){
        button.setOnMouseEntered(action4 -> button.setStyle(END_DAY_BUTTON_STYLE_GIF));
        button.setOnMouseExited(action5 -> button.setStyle(END_DAY_BUTTON_STYLE_PNG));
    }

    public void getHistoryDelButtonStyle(Button button){
        button.setOnMouseEntered(action -> button.setStyle(HISTORY_DEL_BUTTON_STYLE_GIF));
        button.setOnMouseExited(action -> button.setStyle(HISTORY_DEL_BUTTON_STYLE_PNG));
        button.setOnMousePressed(action -> button.setStyle(HISTORY_DEL_BUTTON_STYLE_PNG + ON_ANY_BUTTON_PRESSED_BACKGROUND_COLOR));
        button.setOnMouseReleased(action -> button.setStyle(HISTORY_DEL_BUTTON_STYLE_GIF));
    }
    public void getPlusButtonStyle(Button button){
        button.setOnMouseEntered(action -> button.setStyle(PLUS_BUTTON_STYLE_GIF));
        button.setOnMouseExited(action -> button.setStyle(PLUS_BUTTON_STYLE_PNG));
    }

    public void getCollapseButtonStyle(Button button){
        button.setOnMouseEntered(action -> button.setStyle(COLLAPSE_BUTTON_STYLE_GIF));
        button.setOnMouseExited(action -> button.setStyle(COLLAPSE_BUTTON_STYLE_PNG));
        button.setOnMousePressed(action -> button.setStyle(COLLAPSE_BUTTON_STYLE_PNG + ON_ANY_BUTTON_PRESSED_BACKGROUND_COLOR));
        button.setOnMouseReleased(action -> button.setStyle(COLLAPSE_BUTTON_STYLE_GIF));

    }
    public void getExpandButtonStyle(Button button){
        button.setOnMouseEntered(action -> button.setStyle(EXPAND_BUTTON_STYLE_GIF));
        button.setOnMouseExited(action -> button.setStyle(EXPAND_BUTTON_STYLE_PNG));
        button.setOnMousePressed(action -> button.setStyle(EXPAND_BUTTON_STYLE_PNG+ ON_ANY_BUTTON_PRESSED_BACKGROUND_COLOR));
        button.setOnMouseReleased(action -> button.setStyle(EXPAND_BUTTON_STYLE_GIF));
    }
    public void getSimpleViewButtonStyle(Button button){
        button.setOnMousePressed(action -> button.setStyle(ON_ANY_BUTTON_PRESSED_BACKGROUND_COLOR));
        button.setOnMouseReleased(action -> button.setStyle(ON_ANY_BUTTON_RELEASED_BACKGROUND_COLOR));
    }

}
