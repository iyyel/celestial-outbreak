package io.iyyel.celestialoutbreak.level;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.ui.entity.Ball;
import io.iyyel.celestialoutbreak.ui.entity.BlockList;
import io.iyyel.celestialoutbreak.ui.entity.Paddle;
import io.iyyel.celestialoutbreak.handler.InputHandler;
import io.iyyel.celestialoutbreak.handler.SoundHandler;
import io.iyyel.celestialoutbreak.handler.TextHandler;
import io.iyyel.celestialoutbreak.ui.screen.play.GamePanel;

import java.awt.*;

public final class Level {

    private TextHandler textHandler = TextHandler.getInstance();
    private InputHandler inputHandler = InputHandler.getInstance();
    private SoundHandler soundHandler = SoundHandler.getInstance();

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
    private Dimension paddleDim;
    private int paddleSpeed;
    private Color paddleColor;

    /*
     * Ball options
     */
    private Point ballPos;
    private int ballSize;
    private int ballSpeed;
    private Color ballColor;

    /*
     * Block options
     */
    private Point blockPosStart;
    private Point blockPosSpacing;
    private int blockAmount;
    private int blockHitPoints;
    private Dimension blockDim;
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
        paddle.update();
        ball.update();
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
        paddleDim = levelOptions.getPaddleDim();
        paddleSpeed = levelOptions.getPaddleSpeed();
        paddleColor = levelOptions.getPaddleColor();

        /* Ball options */
        ballPos = levelOptions.getBallPos();
        ballSize = levelOptions.getBallSize();
        ballSpeed = levelOptions.getBallSpeed();
        ballColor = levelOptions.getBallColor();

        /* BlockList options */
        blockPosStart = levelOptions.getBlockPosStart();
        blockPosSpacing = levelOptions.getBlockPosSpacing();
        blockAmount = levelOptions.getBlockAmount();
        blockHitPoints = levelOptions.getBlockHitPoints();
        blockDim = levelOptions.getBlockDim();
        blockLum = levelOptions.getBlockLum();
        blockSat = levelOptions.getBlockSat();

        /* Create objects after initializing the options */
        paddle = new Paddle(paddlePos, paddleDim, paddleSpeed, paddleColor, gameController, inputHandler);
        blockList = new BlockList(blockAmount, blockHitPoints, blockPosStart, blockDim, blockPosSpacing, blockLum, blockSat, gameController);
        ball = new Ball(ballPos, ballSize, ballColor, ballSpeed, gameController, paddle, blockList);
        gamePanel = new GamePanel(gameController, levelOptions);

        /* Add level audio to SoundHandler */
        soundHandler.addSoundClip(soundFileName, textHandler.getClientSoundFilePath(soundFileName));
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

    public void playSound() {
        soundHandler.getSoundClip(soundFileName).play(true);
    }

    public void stopSound() {
        soundHandler.getSoundClip(soundFileName).stop();
    }

    public void pauseSound() {
        soundHandler.getSoundClip(soundFileName).pause();
    }

}