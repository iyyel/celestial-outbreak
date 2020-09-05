package io.iyyel.celestialoutbreak.level;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.handler.SoundHandler;
import io.iyyel.celestialoutbreak.handler.TextHandler;
import io.iyyel.celestialoutbreak.ui.entity.*;
import io.iyyel.celestialoutbreak.ui.entity.effects.Effect;
import io.iyyel.celestialoutbreak.ui.screen.play.GamePanel;

import java.awt.*;
import java.util.Random;

public final class Level {

    private final TextHandler textHandler = TextHandler.getInstance();
    private final SoundHandler soundHandler = SoundHandler.getInstance();

    /*
     * Rendering objects
     */
    private Paddle paddle;
    private Ball ball;
    private BlockField blockField;

    /*
     * Settings for the level
     */
    private final LevelOptions levelOptions;

    /*
     * Level options
     */
    private String name;
    private String desc;
    private int playerLife;
    private int playerLifeInit;
    private String soundFileName;
    private Color color;

    /*
     * Power up options.
     */
    private Dimension powerUpDim;
    private int powerUpSpeed;
    private int powerUpChance;
    private PowerUp.Style powerUpStyle;

    /*
     * Power up effects.
     */
    private Effect[] effects;

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
    private Dimension ballDim;
    private int ballSpeed;
    private Ball.Style ballStyle;
    private Color ballColor;

    /*
     * Block options
     */
    private Point blockPosStart;
    private Point blockPosSpacing;
    private int blockAmount;
    private int blockHealth;
    private Dimension blockDim;
    private Block.Style blockStyle;
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
        blockField.render(g);
        gamePanel.render(g);
    }

    private void initLevel(GameController gameController) {
        /* Level options */
        name = levelOptions.getLevelName();
        desc = levelOptions.getLevelDesc();
        playerLife = levelOptions.getLevelPlayerLife();
        playerLifeInit = playerLife;
        soundFileName = levelOptions.getLevelSoundFileName();
        color = levelOptions.getLevelColor();

        /* Power up options */
        powerUpDim = levelOptions.getPowerUpDim();
        powerUpSpeed = levelOptions.getPowerUpSpeed();
        powerUpChance = levelOptions.getPowerUpChance();
        powerUpStyle = levelOptions.getPowerUpStyle();

        /* Effects */
        effects = levelOptions.getEffects();

        /* Paddle options */
        paddlePos = levelOptions.getPaddlePos();
        paddleDim = levelOptions.getPaddleDim();
        paddleSpeed = levelOptions.getPaddleSpeed();
        paddleColor = levelOptions.getPaddleColor();

        /* Ball options */
        ballPos = levelOptions.getBallPos();
        ballDim = levelOptions.getBallDim();
        ballSpeed = levelOptions.getBallSpeed();
        ballStyle = levelOptions.getBallStyle();
        ballColor = levelOptions.getBallColor();

        /* BlockField options */
        blockPosStart = levelOptions.getBlockPosStart();
        blockPosSpacing = levelOptions.getBlockPosSpacing();
        blockAmount = levelOptions.getBlockAmount();
        blockHealth = levelOptions.getBlockHitPoints();
        blockDim = levelOptions.getBlockDim();
        blockStyle = levelOptions.getBlockStyle();
        blockLum = levelOptions.getBlockLum();
        blockSat = levelOptions.getBlockSat();

        /* Create objects after initializing the options */
        paddle = new Paddle(paddlePos, paddleDim, paddleColor, paddleSpeed, gameController.getWidth(), gameController.getHeight());
        blockField = new BlockField(blockAmount, blockHealth, blockPosStart, blockDim, blockPosSpacing, blockStyle, blockLum, blockSat, gameController.getWidth());
        ball = new Ball(ballPos, ballDim, ballColor, ballSpeed, ballStyle, paddle, blockField, gameController.getWidth(), gameController.getHeight());
        gamePanel = new GamePanel(gameController, levelOptions);

        /* Add level audio to SoundHandler */
        soundHandler.addSoundClip(soundFileName, textHandler.getClientSoundFilePath(soundFileName));
    }

    public Dimension getPowerUpDim() {
        return powerUpDim;
    }

    public int getPowerUpSpeed() {
        return powerUpSpeed;
    }

    public int getPowerUpChance() {
        return powerUpChance;
    }

    public PowerUp.Style getPowerUpStyle() {
        return powerUpStyle;
    }

    public boolean isWon() {
        return blockField.getBlocksLeft() <= 0;
    }

    public boolean isLost() {
        return playerLife <= 0;
    }

    public int getPlayerLife() {
        return playerLife;
    }

    public int getPlayerLifeInit() {
        return playerLifeInit;
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

    public BlockField getBlockField() {
        return blockField;
    }

    public void decPlayerLife() {
        if (playerLife > 0) {
            playerLife -= 1;
        }
    }

    public void pause() {
        ball.stopUpdate(120);
        paddle.stopUpdate(120);
    }

    public int getBlockHealth() {
        return blockHealth;
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

    public Effect getRandomEffect() {
        int index = new Random().nextInt(effects.length);
        return effects[index];
    }

}