package io.inabsentia.celestialoutbreak.level;

import io.inabsentia.celestialoutbreak.controller.Game;
import io.inabsentia.celestialoutbreak.handler.FileHandler;

import java.awt.*;
import java.util.Map;

public class LevelSettings {

    /* Level */
    private String levelType;
    private Color levelColor;

    /* Paddle */
    private Point paddlePos;
    private int paddleWidth, paddleHeight, paddleSpeed;
    private Color paddleColor;

    /* Ball */
    private Point ballPos;
    private int ballSize, ballSpeed;
    private Color ballColor;

    /* BlockList */
    private Point blockPos;
    private int blockAmount, blockWidth, blockHeight, blockSpacing;

    /* BottomPanel */
    private Color bottomPanelColor;

    private Game game;
    private final FileHandler fileHandler;

    public LevelSettings(String fileName, Game game, FileHandler fileHandler) {
        this.game = game;
        this.fileHandler = fileHandler;

        try {
            loadLevelSettings(fileName);
        } catch (Exception e) {
            System.err.println("[ERROR]: Failed parsing io.inabsentia.celestialoutbreak.level settings '" + fileName + "'. ERROR: " + e.getMessage());
        }
    }

    private void loadLevelSettings(String fileName) throws Exception {
        Map<String, String> map = fileHandler.loadFromFile(fileName);

        /* Level settings */
        levelType = map.get("LevelType");
        int levelColorValue = Integer.decode(map.get("LevelColorHex"));
        levelColor = new Color(levelColorValue);

        /* Paddle settings */
        int paddlePosXOffset = Integer.parseInt(map.get("LevelPaddlePosXOffset"));
        int paddlePosYOffset = Integer.parseInt(map.get("LevelPaddlePosYOffset"));

        paddlePos = new Point((game.getWidth() / 2) - paddlePosXOffset, game.getHeight() - paddlePosYOffset);
        paddleWidth = Integer.parseInt(map.get("LevelPaddleWidth"));
        paddleHeight = Integer.parseInt(map.get("LevelPaddleHeight"));
        paddleSpeed = Integer.parseInt(map.get("LevelPaddleSpeed"));

        int paddleColorHex = Integer.decode(map.get("LevelPaddleColorHex"));
        paddleColor = new Color(paddleColorHex);

        /* Ball Setup */
        int ballPosXOffset = Integer.parseInt(map.get("LevelBallPosXOffset"));
        int ballPosYOffset = Integer.parseInt(map.get("LevelBallPosYOffset"));
        ballPos = new Point((game.getWidth() / 2) - ballPosXOffset, (game.getHeight() / 2) - ballPosYOffset);

        ballSize = Integer.parseInt(map.get("LevelBallSize"));
        ballSpeed = Integer.parseInt(map.get("LevelBallSpeed"));

        int ballColorHex = Integer.decode(map.get("LevelBallColorHex"));
        ballColor = new Color(ballColorHex);

        /* BlockList settings */
        blockAmount = Integer.parseInt(map.get("LevelBlockListBlockAmount"));

        int blockListPosX = Integer.parseInt(map.get("LevelBlockListPosX"));
        int blockListPosY = Integer.parseInt(map.get("LevelBlockListPosY"));
        blockPos = new Point(blockListPosX, blockListPosY);
        blockWidth = Integer.parseInt(map.get("LevelBlockListBlockWidth"));
        blockHeight = Integer.parseInt(map.get("LevelBlockListBlockHeight"));
        blockSpacing = Integer.parseInt(map.get("LevelBlockListBlockSpacing"));

        /* BottomPanel settings */
        int bottomPanelColorHex = Integer.decode(map.get("LevelBottomPanelColor"));
        bottomPanelColor = new Color(bottomPanelColorHex);
    }

    public String getLevelType() {
        return levelType;
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

    public Color getBottomPanelColor() {
        return bottomPanelColor;
    }

}