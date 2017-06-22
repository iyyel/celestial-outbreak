package io.inabsentia.celestialoutbreak.handler;

import io.inabsentia.celestialoutbreak.controller.Game;
import io.inabsentia.celestialoutbreak.level.Level;
import io.inabsentia.celestialoutbreak.utils.Utils;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class LevelHandler {

    private Level[] levels;
    private Level activeLevel;
    private final int INITIAL_LEVEL_INDEX = 0;

    private final Utils utils = Utils.getInstance();
    private final TextHandler textHandler = TextHandler.getInstance();
    private final FileHandler fileHandler = FileHandler.getInstance();

    public LevelHandler(Game game, InputHandler inputHandler, SoundHandler soundHandler, FileHandler fileHandler) {
        ArrayList<String> levelConfigFileList = (ArrayList<String>) fileHandler.readLinesFromFile(textHandler.LEVEL_CONFIG_FILE_PATH);

        levels = new Level[levelConfigFileList.size()];
        for (int i = 0; i < levels.length; i++) levels[i] = new Level(textHandler.LEVEL_DIR_PATH + File.separator + levelConfigFileList.get(i), game, inputHandler, soundHandler, fileHandler);

        changeLevel(INITIAL_LEVEL_INDEX);
    }

    public void update() {
        activeLevel.update();

        if (activeLevel.isFinished()) {
            fileHandler.writeLogMessage(getActiveLevel().getLevelType() + " finished.");
        }
    }

    public void render(Graphics2D g) {
        activeLevel.render(g);
    }

    private void changeLevel(int index) {
        if (index >= 0 && index <= levels.length - 1) {
            if (utils.isVerboseEnabled() && activeLevel != null) fileHandler.writeLogMessage(textHandler.changedLevelMessage(activeLevel.getLevelType(), levels[index].getLevelType()));
            activeLevel = levels[index];
        }
    }

    public Level getActiveLevel() {
        return activeLevel;
    }

}