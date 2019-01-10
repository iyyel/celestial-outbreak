package io.iyyel.celestialoutbreak.level;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.entity.Ball;
import io.iyyel.celestialoutbreak.entity.BlockList;
import io.iyyel.celestialoutbreak.entity.Paddle;
import io.iyyel.celestialoutbreak.handler.InputHandler;
import io.iyyel.celestialoutbreak.menu.play.GamePanel;

import java.awt.*;

public final class Level {

    private InputHandler inputHandler = InputHandler.getInstance();

    /*
     * Rendering objects
     */
    private Paddle paddle;
    private Ball ball;
    private BlockList blockList;

    /*
     * Settings for the level
     */
    private LevelOptions levelOptions;

    /*
     * Level options
     */
    private String name;
    private String desc;
    private int playerLife;
    private String soundFileName;
    private Color color;

    /*
     * Paddle options
     */
    private Point paddlePos;
    private int paddleWidth;
    private int paddleHeight;
    private int paddleSpeed;
    private Color paddleColor;

    /*
     * Ball options
     */
    private Point ballPos;
    private int ballPosXOffset;
    private int ballPosYOffset;
    private int ballSize;
    private int ballSpeed;
    private Color ballColor;

    /*
     * Block options
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
     * GamePanel options
     */
    private GamePanel gamePanel;

    /*
     * Constructor
     */
    public Level(String optionsFileName, GameController gameController) {
        levelOptions = new LevelOptions(optionsFileName, gameController);
        initLevel(gameController);
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

    private void initLevel(GameController gameController) {
        /* Level options */
        name = levelOptions.getLevelName();
        desc = levelOptions.getLevelDesc();
        playerLife = levelOptions.getLevelPlayerLife();
        soundFileName = levelOptions.getLevelSoundFileName();
        color = levelOptions.getLevelColor();

        /* Paddle options */
        paddlePos = levelOptions.getPaddlePos();
        paddleWidth = levelOptions.getPaddleWidth();
        paddleHeight = levelOptions.getPaddleHeight();
        paddleSpeed = levelOptions.getPaddleSpeed();
        paddleColor = levelOptions.getPaddleColor();

        /* Ball options */
        ballPos = levelOptions.getBallPos();
        ballPosXOffset = levelOptions.getBallPosXOffset();
        ballPosYOffset = levelOptions.getBallPosYOffset();
        ballSize = levelOptions.getBallSize();
        ballSpeed = levelOptions.getBallSpeed();
        ballColor = levelOptions.getBallColor();

        /* BlockList options */
        blockPosStart = levelOptions.getBlockPosStart();
        blockPosXSpacing = levelOptions.getBlockPosXSpacing();
        blockPosYSpacing = levelOptions.getBlockPosYSpacing();
        blockAmount = levelOptions.getBlockAmount();
        blockHitPoints = levelOptions.getBlockHitPoints();
        blockWidth = levelOptions.getBlockWidth();
        blockHeight = levelOptions.getBlockHeight();
        blockLum = levelOptions.getBlockLum();
        blockSat = levelOptions.getBlockSat();

        /* Create objects after initializing the options */
        paddle = new Paddle(paddlePos, paddleWidth, paddleHeight, paddleSpeed, paddleColor, gameController);
        ball = new Ball(ballPos, ballSize, ballSize, ballColor, ballSpeed, ballPosXOffset, ballPosYOffset, gameController);
        blockList = new BlockList(blockAmount, blockHitPoints, blockPosStart, blockWidth, blockHeight,
                blockPosXSpacing, blockPosYSpacing, blockLum, blockSat, gameController);
        gamePanel = new GamePanel(gameController, levelOptions);
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

    public void pause() {
        ball.pause();
        paddle.pause();
    }

}