package io.inabsentia.celestialoutbreak.handler;

import io.inabsentia.celestialoutbreak.controller.Game;
import io.inabsentia.celestialoutbreak.level.Level;
import io.inabsentia.celestialoutbreak.utils.Utils;

import java.awt.*;

public class LevelHandler {

    private Level[] levels;
    private Level activeLevel;
    private final int INITIAL_LEVEL_INDEX = 3;

    private final Utils utils = Utils.getInstance();
    private final boolean DEV_ENABLED = utils.DEV_ENABLED;

    public LevelHandler(Game game, InputHandler inputHandler, SoundHandler soundHandler, FileHandler fileHandler) {
        levels = new Level[5];
        levels[0] = new Level("redlevel.config", game, inputHandler, soundHandler, fileHandler);
        levels[1] = new Level("greenlevel.config", game, inputHandler, soundHandler, fileHandler);
        levels[2] = new Level("bluelevel.config", game, inputHandler, soundHandler, fileHandler);
        levels[3] = new Level("purplelevel.config", game, inputHandler, soundHandler, fileHandler);
        levels[4] = new Level("bordeauxlevel.config", game, inputHandler, soundHandler, fileHandler);
        changeLevel(INITIAL_LEVEL_INDEX);
    }

    public void update() {
        activeLevel.update();
    }

    public void render(Graphics2D g) {
        activeLevel.render(g);
    }

    public void changeLevel(int index) {
        if (index >= 0 && index <= levels.length - 1) {
            if (DEV_ENABLED && activeLevel != null) {
                utils.logMessage("Changed level from '" + activeLevel.getLevelType() + "' to '" + levels[index].getLevelType() + "'.");
            }
            activeLevel = levels[index];
        }
    }

    public Level getActiveLevel() {
        return activeLevel;
    }

}