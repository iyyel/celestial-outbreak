package io.iyyel.celestialoutbreak.level;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.entity.Ball;
import io.iyyel.celestialoutbreak.entity.BlockList;
import io.iyyel.celestialoutbreak.entity.Paddle;
import io.iyyel.celestialoutbreak.handler.InputHandler;
import io.iyyel.celestialoutbreak.menu.GamePanel;

import java.awt.*;

public final class Level {

    /*
     * Rendering objects.
     */
    private Paddle paddle;
    private Ball ball;
    public BlockList blockList;

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

    /*
     * Level options.
     */
    private String name;
    private String desc;
    private Color color;
    private int playerLife;

    /*
     * Paddle options.
     */
    private Point paddlePos;
    private int paddleWidth, paddleHeight, paddleSpeed;
    private Color paddleColor;

    /*
     * Ball options.
     */
    private Point ballPos;
    private int ballPosXOffset, ballPosYOffset, ballSize, ballSpeed;
    private Color ballColor;

    /*
     * BlockList options.
     */
    private Point blockPos;
    private int blockAmount, blockHP, blockWidth, blockHeight, blockSpacing;

    /*
     * GamePanel options.
     */
    private GamePanel gamePanel;

    /*
     * Constructor.
     */
    public Level(String settingsFileName, GameController gameController) {
        this.gameController = gameController;

        levelConfig = new LevelConfig(settingsFileName, gameController);
        initSettings();

        /* Create objects after initializing the options. */
        paddle = new Paddle(paddlePos, paddleWidth, paddleHeight, paddleSpeed, paddleColor, gameController);
        ball = new Ball(ballPos, ballSize, ballSize, ballColor, ballSpeed, ballPosXOffset, ballPosYOffset, gameController);
        blockList = new BlockList(blockAmount, blockHP, blockPos, blockWidth, blockHeight, blockSpacing, gameController);
        gamePanel = new GamePanel(gameController);
    }

    public void update() {
        paddle.update(inputHandler.isLeftPressed(), inputHandler.isRightPressed());
        ball.update(paddle, blockList);
        gamePanel.update();
    }

    public void render(Graphics2D g) {
        paddle.render(g);
        ball.render(g);
        blockList.render(g);
        gamePanel.render(g);
    }

    private void initSettings() {
        /* Level options. */
        name = levelConfig.getLevelName();
        desc = levelConfig.getLevelDesc();
        color = levelConfig.getLevelColor();
        playerLife = levelConfig.getPlayerLife();

        /* Paddle options. */
        paddlePos = levelConfig.getPaddlePos();
        paddleWidth = levelConfig.getPaddleWidth();
        paddleHeight = levelConfig.getPaddleHeight();
        paddleSpeed = levelConfig.getPaddleSpeed();
        paddleColor = levelConfig.getPaddleColor();

        /* Ball options. */
        ballPos = levelConfig.getBallPos();
        ballPosXOffset = levelConfig.getBallPosXOffset();
        ballPosYOffset = levelConfig.getBallPosYOffset();
        ballSize = levelConfig.getBallSize();
        ballSpeed = levelConfig.getBallSpeed();
        ballColor = levelConfig.getBallColor();

        /* BlockList options. */
        blockPos = levelConfig.getBlockPos();
        blockAmount = levelConfig.getBlockAmount();
        blockHP = levelConfig.getBlockHP();
        blockWidth = levelConfig.getBlockWidth();
        blockHeight = levelConfig.getBlockHeight();
        blockSpacing = levelConfig.getBlockSpacing();
    }

    public boolean isWon() {
        return blockList.getBlocksLeft() <= 0;
    }

    public boolean isLost() {
        return playerLife <= 0;
    }

    public int getPlayerLife() {
        return playerLife;
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

    public int getBlockAmount() {
        return blockAmount;
    }

    public int getBlocksLeft() {
        return blockList.getBlocksLeft();
    }

    public void decPlayerLife() {
        if (playerLife > 0) {
            playerLife -= 1;
        }
    }

    public void pauseBall() {
        ball.pauseBall();
    }

    public void pausePaddle() {
        paddle.pausePaddle();
    }

}