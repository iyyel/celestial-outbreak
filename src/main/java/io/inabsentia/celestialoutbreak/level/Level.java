package io.inabsentia.celestialoutbreak.level;

import io.inabsentia.celestialoutbreak.controller.Game;
import io.inabsentia.celestialoutbreak.entity.Ball;
import io.inabsentia.celestialoutbreak.entity.BlockList;
import io.inabsentia.celestialoutbreak.entity.Paddle;
import io.inabsentia.celestialoutbreak.handler.InputHandler;
import io.inabsentia.celestialoutbreak.handler.SoundHandler;
import io.inabsentia.celestialoutbreak.menu.BottomPanelMenu;

import java.awt.*;

public abstract class Level {

    protected Paddle paddle;
    protected Ball ball;
    protected BlockList blockList;
    protected Game game;

    protected InputHandler inputHandler;
    protected SoundHandler soundHandler;

    protected Color levelColor;

    protected Point paddlePos;
    protected int paddleWidth, paddleHeight, paddleSpeed;
    protected Color paddleColor;

    protected Point ballPos;
    protected int ballSize, ballSpeed;
    protected Color ballColor;

    protected Point blockPos;
    protected int blockAmount, blockWidth, blockHeight, blockSpacing;

    protected BottomPanelMenu bottomPanelMenu;
    protected Color bottomPanelColor;

    public Level(Game game, InputHandler inputHandler, SoundHandler soundHandler) {
        this.game = game;
        this.inputHandler = inputHandler;
        this.soundHandler = soundHandler;
        levelSetup();

        paddle = new Paddle(paddlePos, paddleWidth, paddleHeight, paddleSpeed, paddleColor, game);
        ball = new Ball(ballPos, ballSize, ballSize, ballSpeed, ballColor, game);
        blockList = new BlockList(blockAmount, blockPos, blockWidth, blockHeight, blockSpacing, game);
        bottomPanelMenu = new BottomPanelMenu(game, inputHandler, Color.WHITE);
    }

    public abstract void levelSetup();

    public void update() {
        paddle.update(inputHandler.left, inputHandler.right);
        ball.update(paddle, blockList);
        bottomPanelMenu.updateBlockAmount(blockList.getBlocksLeft());
    }

    public void render(Graphics2D g) {
        paddle.render(g);
        ball.render(g);
        blockList.render(g);
        bottomPanelMenu.render(g);
    }

    public Color getLevelColor() {
        return levelColor;
    }

}