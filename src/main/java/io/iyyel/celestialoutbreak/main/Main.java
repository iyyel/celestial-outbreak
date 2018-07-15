package io.iyyel.celestialoutbreak.main;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.handler.FileHandler;
import io.iyyel.celestialoutbreak.handler.TextHandler;

import javax.swing.*;

public final class Main {

    private static final TextHandler textHandler = TextHandler.getInstance();
    private static final FileHandler fileHandler = FileHandler.getInstance();

    public static void main(String[] args) {
        fileHandler.writeLog(textHandler.LOG_SEPARATOR);
        fileHandler.writeLog(textHandler.GAME_INIT_STARTED);
        SwingUtilities.invokeLater(() -> new GameController().start());
    }

}