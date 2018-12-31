package io.iyyel.celestialoutbreak.handler;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.level.Level;

import java.awt.*;
import java.io.File;
import java.util.List;

public final class LevelHandler {

    private final TextHandler textHandler = TextHandler.getInstance();
    private final FileHandler fileHandler = FileHandler.getInstance();
    private final OptionsHandler optionsHandler = OptionsHandler.getInstance();

    private int activeLevelIndex = 0;
    private Level[] levels;

    private GameController gameController;

    private static LevelHandler instance;

    static {
        try {
            instance = new LevelHandler();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private LevelHandler() {

    }

    public synchronized static LevelHandler getInstance() {
        return instance;
    }

    public void update() {
        if (levels[activeLevelIndex] == null || gameController == null) {
            return;
        }

        levels[activeLevelIndex].update();

        if (levels[activeLevelIndex].isFinished()) {
            //startNextLevel();
            // gameController.switchState(GameController.State.POST_LEVEL);
            //if (optionsHandler.isVerboseLogEnabled()) {
            //     fileHandler.writeLog(textHandler.vLevelFinishedMsg(getPrevLevel().getName()));
            // }
            if (optionsHandler.isVerboseLogEnabled()) {
                fileHandler.writeLog("Finished " + levels[activeLevelIndex].getName() + " level!");
            }

            gameController.switchState(GameController.State.POST_LEVEL);
        }
    }

    public void render(Graphics2D g) {
        levels[activeLevelIndex].render(g);
    }

    public void initLevelHandler(GameController gameController) {
        this.gameController = gameController;

        List<String> levelConfigFileList = fileHandler.readLinesFromFile(textHandler.LEVEL_CONFIG_FILE_CLIENT_PATH);
        levels = new Level[levelConfigFileList.size()];

        //TODO: Remove magic number here.
        if (levels.length > 16) {
            fileHandler.writeLog("Maximum levels exceeded: 16 - shutting down. (MAKE THIS SHOW A POPUP!)");
            gameController.stop();
        }

        for (int i = 0; i < levels.length; i++) {
            String settingsFileName = textHandler.LEVEL_DIR_PATH + File.separator + levelConfigFileList.get(i);
            levels[i] = new Level(settingsFileName, gameController);
        }

    }

    public Level getActiveLevel() {
        return levels[activeLevelIndex];
    }

    public int getLevelAmount() {
        return levels.length;
    }

    public Level getLevel(int index) {
        return levels[index];
    }

    public void setActiveLevelIndex(int index) {
        this.activeLevelIndex = index;
    }

}