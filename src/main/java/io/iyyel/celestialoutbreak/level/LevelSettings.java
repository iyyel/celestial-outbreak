package io.iyyel.celestialoutbreak.level;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.handler.FileHandler;
import io.iyyel.celestialoutbreak.handler.TextHandler;
import org.apache.commons.lang.exception.ExceptionUtils;

import java.awt.*;
import java.util.Map;

public final class LevelSettings {

    private final TextHandler textHandler = TextHandler.getInstance();

    /*
     * Level settings.
     */
    private String levelName;
    private String levelDesc;
    private Color levelColor;

    /*
     * Paddle settings.
     */
    private Point paddlePos;
    private int paddleWidth, paddleHeight, paddleSpeed;
    private Color paddleColor;

    /*
     * Ball settings.
     */
    private Point ballPos;
    private int ballPosXOffset, ballPosYOffset, ballSize, ballSpeed;
    private Color ballColor;

    /*
     * BlockList settings.
     */
    private Point blockPos;
    private int blockAmount, blockWidth, blockHeight, blockSpacing;

    /*
     * GamePanel settings.
     */
    private Color gamePanelColor;

    /*
     * Required objects.
     */
    private final GameController gameController;
    private final FileHandler fileHandler;

    /*
     * Constructor.
     */
    public LevelSettings(String fileName, GameController gameController, FileHandler fileHandler) {
        this.gameController = gameController;
        this.fileHandler = fileHandler;

        try {
            parseLevelSettings(fileName);
        } catch (Exception e) {
            /*
             * If the level settings aren't able to be parsed correctly, any exception that will be
             * thrown will be printed to the console as well as the current log file, and then the
             * gameController will be stopped, since invalid level settings has been found which might cause
             * the application to be in an inconsistent state.
             */
            fileHandler.writeLog(textHandler.errorParsingPropertiesMsg(fileName, ExceptionUtils.getStackTrace(e)));
            gameController.stop();
        }
    }

    private void parseLevelSettings(String fileName) {
        Map<String, String> map = fileHandler.readPropertiesFromFile(fileName);

        /* Level settings. */
        levelName = map.get(textHandler.PROP_LEVEL_NAME);
        levelDesc = map.get(textHandler.PROP_LEVEL_DESC);
        int levelColorValue = Integer.decode(map.get(textHandler.PROP_LEVEL_COLOR_HEX));
        levelColor = new Color(levelColorValue);

        /* Paddle settings. */
        int paddlePosXOffset = Integer.parseInt(map.get(textHandler.PROP_PADDLE_POS_X_OFFSET));
        int paddlePosYOffset = Integer.parseInt(map.get(textHandler.PROP_PADDLE_POS_Y_OFFSET));

        paddlePos = new Point((gameController.getWidth() / 2) - paddlePosXOffset, gameController.getHeight() - paddlePosYOffset);
        paddleWidth = Integer.parseInt(map.get(textHandler.PROP_PADDLE_WIDTH));
        paddleHeight = Integer.parseInt(map.get(textHandler.PROP_PADDLE_HEIGHT));
        paddleSpeed = Integer.parseInt(map.get(textHandler.PROP_PADDLE_SPEED));

        int paddleColorHex = Integer.decode(map.get(textHandler.PROP_PADDLE_COLOR_HEX));
        paddleColor = new Color(paddleColorHex);

        /* Ball Setup. */
        ballPosXOffset = Integer.parseInt(map.get(textHandler.PROP_BALL_POS_X_OFFSET));
        ballPosYOffset = Integer.parseInt(map.get(textHandler.PROP_BALL_POS_Y_OFFSET));
        ballPos = new Point((gameController.getWidth() / 2) - ballPosXOffset, (gameController.getHeight() / 2) - ballPosYOffset);

        ballSize = Integer.parseInt(map.get(textHandler.PROP_BALL_SIZE));
        ballSpeed = Integer.parseInt(map.get(textHandler.PROP_BALL_SPEED));

        int ballColorHex = Integer.decode(map.get(textHandler.PROP_BALL_COLOR_HEX));
        ballColor = new Color(ballColorHex);

        /* BlockList settings. */
        blockAmount = Integer.parseInt(map.get(textHandler.PROP_BLOCKLIST_BLOCK_AMOUNT));

        int blockListPosX = Integer.parseInt(map.get(textHandler.PROP_BLOCKLIST_POS_X));
        int blockListPosY = Integer.parseInt(map.get(textHandler.PROP_BLOCKLIST_POS_Y));
        blockPos = new Point(blockListPosX, blockListPosY);

        blockWidth = Integer.parseInt(map.get(textHandler.PROP_BLOCKLIST_BLOCK_WIDTH));
        blockHeight = Integer.parseInt(map.get(textHandler.PROP_BLOCKLIST_BLOCK_HEIGHT));
        blockSpacing = Integer.parseInt(map.get(textHandler.PROP_BLOCKLIST_BLOCK_SPACING));

        /* GamePanel settings. */
        int gamePanelColorHex = Integer.decode(map.get(textHandler.PROP_GAME_PANEL_COLOR_HEX));
        gamePanelColor = new Color(gamePanelColorHex);
    }

    public String getLevelName() {
        return levelName;
    }

    public String getLevelDesc() {
        return levelDesc;
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

    public int getBallPosXOffset() {
        return ballPosXOffset;
    }

    public int getBallPosYOffset() {
        return ballPosYOffset;
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

    public Point getBlockPos() {
        return blockPos;
    }

    public int getBlockAmount() {
        return blockAmount;
    }

    public int getBlockWidth() {
        return blockWidth;
    }

    public int getBlockHeight() {
        return blockHeight;
    }

    public int getBlockSpacing() {
        return blockSpacing;
    }

    public Color getGamePanelColor() {
        return gamePanelColor;
    }

}