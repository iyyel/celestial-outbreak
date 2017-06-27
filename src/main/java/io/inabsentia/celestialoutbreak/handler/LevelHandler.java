package io.inabsentia.celestialoutbreak.handler;

import io.inabsentia.celestialoutbreak.controller.Game;
import io.inabsentia.celestialoutbreak.entity.State;
import io.inabsentia.celestialoutbreak.level.Level;
import io.inabsentia.celestialoutbreak.utils.Utils;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class LevelHandler {

    private Level[] levels;
    private Level activeLevel;
    private int currentLevelIndex = 0;

    private final Utils utils = Utils.getInstance();
    private final TextHandler textHandler = TextHandler.getInstance();

    private Game game;
    private final FileHandler fileHandler;

    public LevelHandler(int currentLevelIndex, Game game, InputHandler inputHandler, SoundHandler soundHandler, FileHandler fileHandler) {
        this.currentLevelIndex = currentLevelIndex;
        this.game = game;
        this.fileHandler = fileHandler;
        ArrayList<String> levelConfigFileList = (ArrayList<String>) fileHandler.readLinesFromFile(textHandler.LEVEL_CONFIG_FILE_PATH);

        levels = new Level[levelConfigFileList.size()];
        for (int i = 0; i < levels.length; i++) levels[i] = new Level(textHandler.LEVEL_DIR_PATH + File.separator + levelConfigFileList.get(i), game, inputHandler, soundHandler, fileHandler);

        activeLevel = levels[currentLevelIndex];
    }

    public void update(State state) {
        activeLevel.update();

        if (activeLevel.isFinished()) {
            switchNextLevel();
            game.switchState(State.FINISHED_LEVEL);
            if (utils.isVerboseEnabled()) fileHandler.writeLogMessage(textHandler.vLevelFinishedMsg(getPrevLevel().getLevelName()));
        }
    }

    public void render(Graphics2D g) {
        activeLevel.render(g);
    }

    private void switchNextLevel() {
        if (currentLevelIndex >= 0 && currentLevelIndex + 1 <= levels.length - 1) {
            if (utils.isVerboseEnabled() && activeLevel != null) fileHandler.writeLogMessage(textHandler.vChangedLevelMsg(activeLevel.getLevelName(), levels[currentLevelIndex + 1].getLevelName()));
            activeLevel = levels[++currentLevelIndex];
        }
    }

    public Level getActiveLevel() {
        return activeLevel;
    }

    public Level getPrevLevel() {
        return levels[currentLevelIndex - 1];
    }

}