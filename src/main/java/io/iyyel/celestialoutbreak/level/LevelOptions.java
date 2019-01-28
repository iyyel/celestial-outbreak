package io.iyyel.celestialoutbreak.level;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.handler.FileHandler;
import io.iyyel.celestialoutbreak.handler.TextHandler;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.awt.*;
import java.util.Map;

public final class LevelOptions {

    private final TextHandler textHandler = TextHandler.getInstance();
    private final FileHandler fileHandler = FileHandler.getInstance();

    /*
     * Level options.
     */
    private String levelName;
    private String levelDesc;
    private int levelPlayerLife;
    private String levelSoundFileName;
    private Color levelColor;

    /*
     * Paddle options.
     */
    private Point paddlePos;
    private int paddleWidth;
    private int paddleHeight;
    private int paddleSpeed;
    private Color paddleColor;

    /*
     * Ball options.
     */
    private Point ballPos;
    private int ballSize;
    private int ballSpeed;
    private Color ballColor;

    /*
     * BlockList options.
     */
    private Point blockPosStart;
    private int blockPosXSpacing;
    private int blockPosYSpacing;
    private int blockAmount;
    private int blockHitPoints;
    private int blockWidth;
    private int blockHeight;
    private float blockLum;
    private float blockSat;

    /*
     * GamePanel options.
     */
    private Color gamePanelColor;

    /*
     * Required objects.
     */
    private final GameController gameController;

    /*
     * Constructor.
     */
    public LevelOptions(String fileName, GameController gameController) {
        this.gameController = gameController;

        try {
            parseLevelOptions(fileName);
        } catch (Exception e) {
            /*
             * If the level options aren't able to be parsed correctly, any exception that will be
             * thrown will be printed to the console as well as the current log file, and then the
             * gameController will be stopped, since invalid level options has been found which might cause
             * the application to be in an inconsistent state.
             */
            fileHandler.writeLog(textHandler.errorParsingPropertiesMsg(fileName, ExceptionUtils.getStackTrace(e)));
            gameController.stop();
        }
    }

    private void parseLevelOptions(String fileName) {
        Map<String, String> map = fileHandler.readPropertiesFromFile(fileName);

        /* Level options */
        levelName = map.get(textHandler.PROP_LEVEL_NAME);
        levelDesc = map.get(textHandler.PROP_LEVEL_DESC);
        levelPlayerLife = Integer.parseInt(map.get(textHandler.PROP_LEVEL_PLAYER_LIFE));
        levelSoundFileName = map.get(textHandler.PROP_LEVEL_SOUND_FILE_NAME);
        int levelColorValue = Integer.decode(map.get(textHandler.PROP_LEVEL_COLOR));
        levelColor = new Color(levelColorValue);

        /* Paddle options */
        int paddlePosXOffset = Integer.parseInt(map.get(textHandler.PROP_PADDLE_POS_X_OFFSET));
        int paddlePosYOffset = Integer.parseInt(map.get(textHandler.PROP_PADDLE_POS_Y_OFFSET));
        paddlePos = new Point((gameController.getWidth() / 2) - paddlePosXOffset, gameController.getHeight() - paddlePosYOffset);
        paddleWidth = Integer.parseInt(map.get(textHandler.PROP_PADDLE_WIDTH));
        paddleHeight = Integer.parseInt(map.get(textHandler.PROP_PADDLE_HEIGHT));
        paddleSpeed = Integer.parseInt(map.get(textHandler.PROP_PADDLE_SPEED));
        int paddleCol = Integer.decode(map.get(textHandler.PROP_PADDLE_COLOR));
        paddleColor = new Color(paddleCol);

        /* Ball options */
        ballSize = Integer.parseInt(map.get(textHandler.PROP_BALL_SIZE));
        ballPos = new Point(paddlePos.x + (paddleWidth / 2) - (ballSize / 2), paddlePos.y - (ballSize));
        ballSpeed = Integer.parseInt(map.get(textHandler.PROP_BALL_SPEED));
        int ballColorHex = Integer.decode(map.get(textHandler.PROP_BALL_COLOR));
        ballColor = new Color(ballColorHex);

        /* Block options */
        int blockPosXStart = Integer.parseInt(map.get(textHandler.PROP_BLOCK_POS_X_START));
        int blockPosYStart = Integer.parseInt(map.get(textHandler.PROP_BLOCK_POS_Y_START));
        blockPosStart = new Point(blockPosXStart, blockPosYStart);
        blockPosXSpacing = Integer.parseInt(map.get(textHandler.PROP_BLOCK_POS_X_SPACING));
        blockPosYSpacing = Integer.parseInt(map.get(textHandler.PROP_BLOCK_POS_Y_SPACING));
        blockAmount = Integer.parseInt(map.get(textHandler.PROP_BLOCK_AMOUNT));
        blockHitPoints = Integer.parseInt(map.get(textHandler.PROP_BLOCK_HITPOINTS));
        blockWidth = Integer.parseInt(map.get(textHandler.PROP_BLOCK_WIDTH));
        blockHeight = Integer.parseInt(map.get(textHandler.PROP_BLOCK_HEIGHT));
        blockLum = Float.parseFloat(map.get(textHandler.PROP_BLOCK_LUMINANCE));
        blockSat = Float.parseFloat(map.get(textHandler.PROP_BLOCK_SATURATION));

        /* GamePanel options */
        int gamePanelColorInt = Integer.decode(map.get(textHandler.PROP_GAME_PANEL_COLOR));
        gamePanelColor = new Color(gamePanelColorInt);
    }

    public String getLevelName() {
        return levelName;
    }

    public String getLevelDesc() {
        return levelDesc;
    }

    public int getLevelPlayerLife() {
        return levelPlayerLife;
    }

    public String getLevelSoundFileName() {
        return levelSoundFileName;
    }

    public Color getLevelColor() {
        return levelColor;
    }

    public Point getPaddlePos() {
        return paddlePos;
    }

    public int getPaddleWidth() {
        return paddleWidth;
    }

    public int getPaddleHeight() {
        return paddleHeight;
    }

    public int getPaddleSpeed() {
        return paddleSpeed;
    }

    public Color getPaddleColor() {
        return paddleColor;
    }

    public Point getBallPos() {
        return ballPos;
    }

    public int getBallSize() {
        return ballSize;
    }

    public int getBallSpeed() {
        return ballSpeed;
    }

    public Color getBallColor() {
        return ballColor;
    }

    public Point getBlockPosStart() {
        return blockPosStart;
    }

    public int getBlockPosXSpacing() {
        return blockPosXSpacing;
    }

    public int getBlockPosYSpacing() {
        return blockPosYSpacing;
    }

    public int getBlockAmount() {
        return blockAmount;
    }

    public int getBlockHitPoints() {
        return blockHitPoints;
    }

    public int getBlockWidth() {
        return blockWidth;
    }

    public int getBlockHeight() {
        return blockHeight;
    }

    public float getBlockLum() {
        return blockLum;
    }

    public float getBlockSat() {
        return blockSat;
    }

    public Color getGamePanelColor() {
        return gamePanelColor;
    }

}