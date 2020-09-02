package io.iyyel.celestialoutbreak.handler;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.level.Level;
import io.iyyel.celestialoutbreak.util.Util;

import java.awt.*;
import java.io.File;
import java.util.List;

public final class LevelHandler {

    private final TextHandler textHandler = TextHandler.getInstance();
    private final LogHandler logHandler = LogHandler.getInstance();
    private final FileHandler fileHandler = FileHandler.getInstance();
    private final Util util = Util.getInstance();

    private int activeLevelIndex = 0;
    private Level[] levels;
    private String[] levelOptionsFileNames;

    private Color[] levelColors;
    private String[] levelNames;
    private int[] levelPlayerLives;
    private int[] levelBlockAmounts;
    private int[] levelHitPoints;

    private long currentScore = 0;

    private GameController gameController;

    private static final LevelHandler instance;

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
        calculateScore();

        if (levels[activeLevelIndex].isWon()) {
            logHandler.log("Won " + getActiveLevel().getName() + " level!", LogHandler.LogLevel.INFO, true);
            gameController.switchState(GameController.State.POST_LEVEL);
        }

        if (levels[activeLevelIndex].isLost()) {
            logHandler.log("Lost " + getActiveLevel().getName() + " level!", LogHandler.LogLevel.INFO, true);
            gameController.switchState(GameController.State.POST_LEVEL);
        }
    }

    public void render(Graphics2D g) {
        levels[activeLevelIndex].render(g);
    }

    public void initPreLevels(GameController gameController) {
        this.gameController = gameController;

        List<String> levelConfigFileList = fileHandler.readLinesFromFile(textHandler.LEVEL_CONFIG_FILE_CLIENT_PATH);
        levels = new Level[levelConfigFileList.size()];
        levelOptionsFileNames = new String[levelConfigFileList.size()];

        //TODO: Remove magic number here.
        if (levels.length > 12) {
            logHandler.log("Maximum levels exceeded: 12 - shutting down. (MAKE THIS SHOW A POPUP!)", LogHandler.LogLevel.ERROR, false);
            gameController.stop();
            return;
        }

        for (int i = 0; i < levels.length; i++) {
            levelOptionsFileNames[i] = textHandler.LEVEL_DIR_PATH + File.separator + levelConfigFileList.get(i);
        }

        levelColors = getLevelColorProperties();
        levelNames = getLevelStringProperties(textHandler.PROP_KEY_LEVEL_NAME);
        levelPlayerLives = getLevelIntProperties(textHandler.PROP_KEY_LEVEL_PLAYER_LIFE);
        levelBlockAmounts = getLevelIntProperties(textHandler.PROP_KEY_BLOCK_AMOUNT);
        levelHitPoints = getLevelIntProperties(textHandler.PROP_KEY_BLOCK_HEALTH);
    }

    public void loadLevel(int index) {
        levels[index] = new Level(levelOptionsFileNames[index], gameController);
    }

    private String[] getLevelStringProperties(String pKey) {
        String[] arr = new String[levels.length];
        for (int i = 0; i < levels.length; i++) {
            arr[i] = fileHandler.readPropertyFromFile(pKey, levelOptionsFileNames[i]);
        }
        return arr;
    }

    private Color[] getLevelColorProperties() {
        Color[] colors = new Color[levels.length];
        for (int i = 0; i < levels.length; i++) {
            int levelColorInt = Integer.decode(fileHandler.readPropertyFromFile(textHandler.PROP_KEY_LEVEL_COLOR, levelOptionsFileNames[i]));
            colors[i] = new Color(levelColorInt);
        }
        return colors;
    }

    private int[] getLevelIntProperties(String pKey) {
        int[] arr = new int[levels.length];
        for (int i = 0; i < levels.length; i++) {
            arr[i] = Integer.parseInt(fileHandler.readPropertyFromFile(pKey, levelOptionsFileNames[i]));
        }
        return arr;
    }

    private void calculateScore() {
        long blockHitPointsPunish = (getActiveLevel().getBlockField().getTotalHealth() - getActiveLevel().getBlockField().getTotalHealthLeft()) * 10;
        long timePunish = Math.abs(util.getTimeElapsed() / getActiveLevel().getBlockHealth());
        long lostLifePunish = (getActiveLevel().getPlayerLifeInit() - getActiveLevel().getPlayerLife()) * getActiveLevel().getBlockField().getHealth() * 10;
        currentScore = blockHitPointsPunish - timePunish - lostLifePunish;
        if (currentScore < 0) {
            currentScore = 0;
        }
    }

    public void resetActiveLevel() {
        logHandler.log("Level[" + activeLevelIndex + "] has been reset.", LogHandler.LogLevel.INFO, true);
        currentScore = 0;
        levels[activeLevelIndex] = null;
    }

    public Level getActiveLevel() {
        return getLevel(activeLevelIndex);
    }

    public int getLevelAmount() {
        return levels.length;
    }

    private Level getLevel(int index) {
        return levels[index];
    }

    public void setActiveLevelIndex(int index) {
        this.activeLevelIndex = index;
    }

    public Color[] getLevelColors() {
        return levelColors;
    }

    public String[] getLevelNames() {
        return levelNames;
    }

    public int[] getLevelPlayerLives() {
        return levelPlayerLives;
    }

    public int[] getLevelHitPoints() {
        return levelHitPoints;
    }

    public int[] getLevelBlockAmounts() {
        return levelBlockAmounts;
    }

    public long getCurrentScore() {
        return currentScore;
    }

}