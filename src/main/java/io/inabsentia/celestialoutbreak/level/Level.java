package io.inabsentia.celestialoutbreak.level;

import io.inabsentia.celestialoutbreak.controller.Game;
import io.inabsentia.celestialoutbreak.entity.Ball;
import io.inabsentia.celestialoutbreak.entity.BlockList;
import io.inabsentia.celestialoutbreak.entity.Paddle;
import io.inabsentia.celestialoutbreak.handler.FileHandler;
import io.inabsentia.celestialoutbreak.handler.InputHandler;
import io.inabsentia.celestialoutbreak.handler.SoundHandler;
import io.inabsentia.celestialoutbreak.menu.GamePanel;

import java.awt.*;

public class Level {

    /*
     * Rendering objects.
     */
    private Paddle paddle;
    private Ball ball;
    private BlockList blockList;

    /*
     * Game object.
     */
    private Game game;

    /*
     * Settings for the level.
     */
    private LevelSettings levelSettings;

    /*
     * Handlers for the level.
     */
    private InputHandler inputHandler;
    private SoundHandler soundHandler;
    private FileHandler fileHandler;

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
     * BottomPanel settings.
     */
    private GamePanel gamePanel;
    private Color bottomPanelColor;

    /*
     * Constructor.
     */
    public Level(String settingsFileName, Game game, InputHandler inputHandler, SoundHandler soundHandler, FileHandler fileHandler) {
        this.game = game;
        this.inputHandler = inputHandler;
        this.soundHandler = soundHandler;
        this.fileHandler = fileHandler;

        levelSettings = new LevelSettings(settingsFileName, game, fileHandler);
        assignLevelSettings();

        paddle = new Paddle(paddlePos, paddleWidth, paddleHeight, paddleSpeed, paddleColor, game);
        ball = new Ball(ballPos, ballPosXOffset, ballPosYOffset, ballSize, ballSize, ballSpeed, ballColor, game);
        blockList = new BlockList(blockAmount, blockPos, blockWidth, blockHeight, blockSpacing, game);
        gamePanel = new GamePanel(game, inputHandler, bottomPanelColor);
    }

    public void update() {
        paddle.update(inputHandler.isLeftPressed(), inputHandler.isRightPressed());
        ball.update(paddle, blockList);
        gamePanel.updatePanel(levelName, 0, 0, blockList.getBlocksLeft());
    }

    public void render(Graphics2D g) {
        paddle.render(g);
        ball.render(g);
        blockList.render(g);
        gamePanel.render(g);
    }

    private void assignLevelSettings() {
        /* Level settings. */
        levelName = levelSettings.getLevelName();
        levelDesc = levelSettings.getLevelDesc();
        levelColor = levelSettings.getLevelColor();

        /* Paddle settings. */
        paddlePos = levelSettings.getPaddlePos();
        paddleWidth = levelSettings.getPaddleWidth();
        paddleHeight = levelSettings.getPaddleHeight();
        paddleSpeed = levelSettings.getPaddleSpeed();
        paddleColor = levelSettings.getPaddleColor();

        /* Ball settings. */
        ballPos = levelSettings.getBallPos();
        ballPosXOffset = levelSettings.getBallPosXOffset();
        ballPosYOffset = levelSettings.getBallPosYOffset();
        ballSize = levelSettings.getBallSize();
        ballSpeed = levelSettings.getBallSpeed();
        ballColor = levelSettings.getBallColor();

        /* BlockList settings. */
        blockPos = levelSettings.getBlockPos();
        blockAmount = levelSettings.getBlockAmount();
        blockWidth = levelSettings.getBlockWidth();
        blockHeight = levelSettings.getBlockHeight();
        blockSpacing = levelSettings.getBlockSpacing();

        /* bottomPanel settings. */
        bottomPanelColor = levelSettings.getBottomPanelColor();
    }

    public boolean isFinished() {
        if (blockList.getBlocksLeft() == 0) return true;
        else return false;
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

}