package io.inabsentia.celestialoutbreak.game.level;

import io.inabsentia.celestialoutbreak.game.controller.Game;
import io.inabsentia.celestialoutbreak.game.entity.Ball;
import io.inabsentia.celestialoutbreak.game.entity.BlockList;
import io.inabsentia.celestialoutbreak.game.entity.Paddle;
import io.inabsentia.celestialoutbreak.game.handler.FileHandler;
import io.inabsentia.celestialoutbreak.game.handler.InputHandler;
import io.inabsentia.celestialoutbreak.game.handler.SoundHandler;
import io.inabsentia.celestialoutbreak.game.menu.BottomPanelMenu;

import java.awt.*;

public class Level {

    private Paddle paddle;
    private Ball ball;
    private BlockList blockList;
    private Game game;
    private LevelSettings levelSettings;

    private InputHandler inputHandler;
    private SoundHandler soundHandler;
    private FileHandler fileHandler;

    private String levelType;
    private Color levelColor;

    private Point paddlePos;
    private int paddleWidth, paddleHeight, paddleSpeed;
    private Color paddleColor;

    private Point ballPos;
    private int ballSize, ballSpeed;
    private Color ballColor;

    private Point blockPos;
    private int blockAmount, blockWidth, blockHeight, blockSpacing;

    private BottomPanelMenu bottomPanelMenu;
    private Color bottomPanelColor;

    public Level(String settingsFileName, Game game, InputHandler inputHandler, SoundHandler soundHandler, FileHandler fileHandler) {
        this.game = game;
        this.inputHandler = inputHandler;
        this.soundHandler = soundHandler;
        this.fileHandler = fileHandler;
        levelSettings = new LevelSettings(settingsFileName, game, fileHandler);
        assignLevelSettings();

        paddle = new Paddle(paddlePos, paddleWidth, paddleHeight, paddleSpeed, paddleColor, game);
        ball = new Ball(ballPos, ballSize, ballSize, ballSpeed, ballColor, game);
        blockList = new BlockList(blockAmount, blockPos, blockWidth, blockHeight, blockSpacing, game);
        bottomPanelMenu = new BottomPanelMenu(game, inputHandler, Color.WHITE);
    }

    public void update() {
        paddle.update(inputHandler.left, inputHandler.right);
        ball.update(paddle, blockList);
        bottomPanelMenu.updatePanel(levelType, 0, 0, blockList.getBlocksLeft());
    }

    public void render(Graphics2D g) {
        paddle.render(g);
        ball.render(g);
        blockList.render(g);
        bottomPanelMenu.render(g);
    }

    private void assignLevelSettings() {
        levelType = levelSettings.getLevelType();
        levelColor = levelSettings.getLevelColor();

        paddlePos = levelSettings.getPaddlePos();
        paddleWidth = levelSettings.getPaddleWidth();
        paddleHeight = levelSettings.getPaddleHeight();
        paddleSpeed = levelSettings.getPaddleSpeed();
        paddleColor = levelSettings.getPaddleColor();

        ballPos = levelSettings.getBallPos();
        ballSize = levelSettings.getBallSize();
        ballSpeed = levelSettings.getBallSpeed();
        ballColor = levelSettings.getBallColor();

        blockPos = levelSettings.getBlockPos();
        blockAmount = levelSettings.getBlockAmount();
        blockWidth = levelSettings.getBlockWidth();
        blockHeight = levelSettings.getBlockHeight();
        blockSpacing = levelSettings.getBlockSpacing();

        bottomPanelColor = levelSettings.getBottomPanelColor();
    }

    public Color getLevelColor() {
        return levelColor;
    }

}