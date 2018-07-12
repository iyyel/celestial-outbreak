package io.iyyel.celestialoutbreak.handler;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.level.Level;
import io.iyyel.celestialoutbreak.utils.Utils;

import java.awt.*;
import java.io.File;
import java.util.List;

public final class LevelHandler {

    private final Utils utils = Utils.getInstance();
    private final TextHandler textHandler = TextHandler.getInstance();

    private GameController gameController;
    private final FileHandler fileHandler = FileHandler.getInstance();

    private Level[] levels;
    private Level activeLevel;

    private int currentLevelIndex;

    public LevelHandler(int currentLevelIndex, GameController gameController) {
        this.currentLevelIndex = currentLevelIndex;
        this.gameController = gameController;

        List<String> levelConfigFileList = fileHandler.readLinesFromFile(textHandler.LEVEL_CONFIG_FILE_CLIENT_PATH);

        levels = new Level[levelConfigFileList.size()];

        for (int i = 0; i < levels.length; i++) {
            String settingsFileName = textHandler.LEVEL_DIR_PATH + File.separator + levelConfigFileList.get(i);
            levels[i] = new Level(settingsFileName, gameController);
        }

        activeLevel = levels[currentLevelIndex];
    }

    public void update() {
        activeLevel.update();

        if (activeLevel.isFinished()) {
            startNextLevel();
            gameController.switchState(GameController.State.FINISHED_LEVEL_SCREEN);
            if (utils.isVerboseLogEnabled()) {
                fileHandler.writeLog(textHandler.vLevelFinishedMsg(getPrevLevel().getName()));
            }
        }
    }

    public void render(Graphics2D g) {
        activeLevel.render(g);
    }

    private void startNextLevel() {
        if (currentLevelIndex >= 0 && currentLevelIndex + 1 <= levels.length - 1) {
            currentLevelIndex++;
            if (utils.isVerboseLogEnabled()) {
                fileHandler.writeLog(textHandler.vChangedLevelMsg(activeLevel.getName(), levels[currentLevelIndex].getName()));
            }
            activeLevel = levels[currentLevelIndex];
        }
    }

    public Level getActiveLevel() {
        return activeLevel;
    }

    public Level getPrevLevel() {
        return levels[currentLevelIndex - 1];
    }

}