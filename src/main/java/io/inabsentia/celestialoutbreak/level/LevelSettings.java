package io.inabsentia.celestialoutbreak.level;

import io.inabsentia.celestialoutbreak.controller.Game;
import io.inabsentia.celestialoutbreak.handler.FileHandler;
import io.inabsentia.celestialoutbreak.handler.TextHandler;
import org.apache.commons.lang.exception.ExceptionUtils;

import java.awt.*;
import java.util.Map;

public class LevelSettings {

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
    private final Game game;
    private final FileHandler fileHandler;

    /*
     * Constructor.
     */
    public LevelSettings(String fileName, Game game, FileHandler fileHandler) {
        this.game = game;
        this.fileHandler = fileHandler;

        try {
            parseLevelSettings(fileName);
        } catch (Exception e) {
            /*
             * If the level settings aren't able to be parsed correctly, any exception that will be
             * thrown will be printed to the console as well as the current log file, and then the
             * game will be stopped, since invalid level settings has been found which might cause
             * the application to be in an inconsistent state.
             */
            fileHandler.writeLogMsg(textHandler.errParsingProperties(fileName, ExceptionUtils.getStackTrace(e)));
            game.stop();
        }
    }

    private void parseLevelSettings(String fileName) {
        Map<String, String> map = fileHandler.readPropertiesFromFile(fileName);

        /* Level settings. */
        levelName = map.get("LevelName");
        levelDesc = map.get("LevelDesc");
        int levelColorValue = Integer.decode(map.get("LevelColorHex"));
        levelColor = new Color(levelColorValue);

        /* Paddle settings. */
        int paddlePosXOffset = Integer.parseInt(map.get("LevelPaddlePosXOffset"));
        int paddlePosYOffset = Integer.parseInt(map.get("LevelPaddlePosYOffset"));

        paddlePos = new Point((game.getWidth() / 2) - paddlePosXOffset, game.getHeight() - paddlePosYOffset);
        paddleWidth = Integer.parseInt(map.get("LevelPaddleWidth"));
        paddleHeight = Integer.parseInt(map.get("LevelPaddleHeight"));
        paddleSpeed = Integer.parseInt(map.get("LevelPaddleSpeed"));

        int paddleColorHex = Integer.decode(map.get("LevelPaddleColorHex"));
        paddleColor = new Color(paddleColorHex);

        /* Ball Setup. */
        ballPosXOffset = Integer.parseInt(map.get("LevelBallPosXOffset"));
        ballPosYOffset = Integer.parseInt(map.get("LevelBallPosYOffset"));
        ballPos = new Point((game.getWidth() / 2) - ballPosXOffset, (game.getHeight() / 2) - ballPosYOffset);

        ballSize = Integer.parseInt(map.get("LevelBallSize"));
        ballSpeed = Integer.parseInt(map.get("LevelBallSpeed"));

        int ballColorHex = Integer.decode(map.get("LevelBallColorHex"));
        ballColor = new Color(ballColorHex);

        /* BlockList settings. */
        blockAmount = Integer.parseInt(map.get("LevelBlockListBlockAmount"));

        int blockListPosX = Integer.parseInt(map.get("LevelBlockListXPos"));
        int blockListPosY = Integer.parseInt(map.get("LevelBlockListXPos"));
        blockPos = new Point(blockListPosX, blockListPosY);
        blockWidth = Integer.parseInt(map.get("LevelBlockListBlockWidth"));
        blockHeight = Integer.parseInt(map.get("LevelBlockListBlockHeight"));
        blockSpacing = Integer.parseInt(map.get("LevelBlockListBlockSpacing"));

        /* GamePanel settings. */
        int gamePanelColorHex = Integer.decode(map.get("LevelGamePanelColor"));
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