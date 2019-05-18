package io.iyyel.celestialoutbreak.main;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.handler.LogHandler;
import io.iyyel.celestialoutbreak.handler.TextHandler;

import javax.swing.*;

public final class Main {

    private static final TextHandler textHandler = TextHandler.getInstance();
    private static final LogHandler logHandler = LogHandler.getInstance();

    public static void main(String[] args) {
        logHandler.log(textHandler.LOG_SEPARATOR, LogHandler.LogLevel.INFO, false);
        logHandler.log(textHandler.GAME_INIT_STARTED, LogHandler.LogLevel.INFO, false);
        SwingUtilities.invokeLater(() -> new GameController().start());
    }

}