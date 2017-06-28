package io.inabsentia.celestialoutbreak.handler;

import io.inabsentia.celestialoutbreak.controller.Game;
import io.inabsentia.celestialoutbreak.entity.State;
import io.inabsentia.celestialoutbreak.level.Level;
import io.inabsentia.celestialoutbreak.utils.GameUtils;

import java.awt.*;
import java.io.File;
import java.util.List;

public class LevelHandler {

    private final GameUtils gameUtils = GameUtils.getInstance();
    private final TextHandler textHandler = TextHandler.getInstance();

    private Game game;
    private final FileHandler fileHandler;

    private Level[] levels;
    private Level activeLevel;

    private int currentLevelIndex = 0;

    public LevelHandler(int currentLevelIndex, Game game, InputHandler inputHandler, SoundHandler soundHandler, FileHandler fileHandler) {
        this.currentLevelIndex = currentLevelIndex;
        this.game = game;
        this.fileHandler = fileHandler;
        List<String> levelConfigFileList = fileHandler.readLinesFromFile(textHandler.LEVEL_CONFIG_FILE_LOCAL_PATH);

        levels = new Level[levelConfigFileList.size()];
        for (int i = 0; i < levels.length; i++) levels[i] = new Level(textHandler.LEVEL_DIR_PATH + File.separator + levelConfigFileList.get(i), game, inputHandler, soundHandler, fileHandler);

        activeLevel = levels[currentLevelIndex];
    }

    public void update() {
        activeLevel.update();

        if (activeLevel.isFinished()) {
            startNextLevel();
            game.switchState(State.FINISHED_LEVEL);
            if (gameUtils.isVerboseEnabled()) fileHandler.writeLogMsg(textHandler.vLevelFinishedMsg(getPrevLevel().getLevelName()));
        }
    }

    public void render(Graphics2D g) {
        activeLevel.render(g);
    }

    private void startNextLevel() {
        if (currentLevelIndex >= 0 && currentLevelIndex + 1 <= levels.length - 1) {
            currentLevelIndex++;
            if (gameUtils.isVerboseEnabled()) fileHandler.writeLogMsg(textHandler.vChangedLevelMsg(activeLevel.getLevelName(), levels[currentLevelIndex].getLevelName()));
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