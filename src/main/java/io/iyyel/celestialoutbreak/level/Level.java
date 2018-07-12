package io.iyyel.celestialoutbreak.level;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.entity.Ball;
import io.iyyel.celestialoutbreak.entity.BlockList;
import io.iyyel.celestialoutbreak.entity.Paddle;
import io.iyyel.celestialoutbreak.handler.FileHandler;
import io.iyyel.celestialoutbreak.handler.InputHandler;
import io.iyyel.celestialoutbreak.handler.SoundHandler;
import io.iyyel.celestialoutbreak.menu.GamePanel;

import java.awt.*;

public final class Level {

    /*
     * Rendering objects.
     */
    private Paddle paddle;
    private Ball ball;
    private BlockList blockList;

    /*
     * GameController object.
     */
    private GameController gameController;

    /*
     * Settings for the level.
     */
    private LevelConfig levelConfig;

    /*
     * Handlers for the level.
     */
    private InputHandler inputHandler = InputHandler.getInstance();
    private SoundHandler soundHandler = SoundHandler.getInstance();
    private FileHandler fileHandler = FileHandler.getInstance();

    /*
     * Level settings.
     */
    private String name;
    private String desc;
    private Color color;

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
    private GamePanel gamePanel;
    private Color GamePanelColor;

    /*
     * Constructor.
     */
    public Level(String settingsFileName, GameController gameController) {
        this.gameController = gameController;

        levelConfig = new LevelConfig(settingsFileName, gameController);
        initSettings();

        /* Create objects after initializing the settings. */
        paddle = new Paddle(paddlePos, paddleWidth, paddleHeight, paddleSpeed, paddleColor, gameController);
        ball = new Ball(ballPos, ballSize, ballSize, ballColor, ballSpeed, ballPosXOffset, ballPosYOffset, gameController);
        blockList = new BlockList(blockAmount, blockPos, blockWidth, blockHeight, blockSpacing, gameController);
        gamePanel = new GamePanel(gameController);
    }

    public void update() {
        paddle.update(inputHandler.isLeftPressed(), inputHandler.isRightPressed());
        ball.update(paddle, blockList);
        gamePanel.updatePanel(name, blockList.getBlocksLeft());
    }

    public void render(Graphics2D g) {
        paddle.render(g);
        ball.render(g);
        blockList.render(g);
        gamePanel.render(g);
    }

    private void initSettings() {
        /* Level settings. */
        name = levelConfig.getLevelName();
        desc = levelConfig.getLevelDesc();
        color = levelConfig.getLevelColor();

        /* Paddle settings. */
        paddlePos = levelConfig.getPaddlePos();
        paddleWidth = levelConfig.getPaddleWidth();
        paddleHeight = levelConfig.getPaddleHeight();
        paddleSpeed = levelConfig.getPaddleSpeed();
        paddleColor = levelConfig.getPaddleColor();

        /* Ball settings. */
        ballPos = levelConfig.getBallPos();
        ballPosXOffset = levelConfig.getBallPosXOffset();
        ballPosYOffset = levelConfig.getBallPosYOffset();
        ballSize = levelConfig.getBallSize();
        ballSpeed = levelConfig.getBallSpeed();
        ballColor = levelConfig.getBallColor();

        /* BlockList settings. */
        blockPos = levelConfig.getBlockPos();
        blockAmount = levelConfig.getBlockAmount();
        blockWidth = levelConfig.getBlockWidth();
        blockHeight = levelConfig.getBlockHeight();
        blockSpacing = levelConfig.getBlockSpacing();

        /* GamePanel settings. */
        GamePanelColor = levelConfig.getGamePanelColor();
    }

    public boolean isFinished() {
        if (blockList.getBlocksLeft() <= 0) {
            return true;
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public Color getColor() {
        return color;
    }

}