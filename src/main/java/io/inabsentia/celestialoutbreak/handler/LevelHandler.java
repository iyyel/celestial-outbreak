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
    private final InputHandler inputHandler;
    private final SoundHandler soundHandler;
    private final FileHandler fileHandler;

    public LevelHandler(Game game, InputHandler inputHandler, SoundHandler soundHandler, FileHandler fileHandler) {
        this.game = game;
        this.inputHandler = inputHandler;
        this.soundHandler = soundHandler;
        this.fileHandler = fileHandler;
        ArrayList<String> levelConfigFileList = (ArrayList<String>) fileHandler.readLinesFromFile(textHandler.LEVEL_CONFIG_FILE_PATH);

        levels = new Level[levelConfigFileList.size()];
        for (int i = 0; i < levels.length; i++) levels[i] = new Level(textHandler.LEVEL_DIR_PATH + File.separator + levelConfigFileList.get(i), game, inputHandler, soundHandler, fileHandler);

        changeLevel(currentLevelIndex);
    }

    public void update(State state) {
        activeLevel.update();

        if (activeLevel.isFinished()) {
            changeLevel(++currentLevelIndex);
            game.changeState(State.NEW_LEVEL);
            if (utils.isVerboseEnabled()) fileHandler.writeLogMessage(textHandler.vLevelFinishedMsg(activeLevel.getLevelType()));
        }
    }

    public void render(Graphics2D g) {
        activeLevel.render(g);
    }

    private void changeLevel(int index) {
        if (index >= 0 && index <= levels.length - 1) {
            if (utils.isVerboseEnabled() && activeLevel != null) fileHandler.writeLogMessage(textHandler.vChangedLevelMsg(activeLevel.getLevelType(), levels[index].getLevelType()));
            activeLevel = levels[index];
        }
    }

    public Level getActiveLevel() {
        return activeLevel;
    }

    public Level getPrevLevel() {
        return levels[currentLevelIndex - 1];
    }

}