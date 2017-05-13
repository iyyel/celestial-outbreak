package io.inabsentia.celestialoutbreak.level;

import io.inabsentia.celestialoutbreak.controller.Game;
import io.inabsentia.celestialoutbreak.handler.InputHandler;
import io.inabsentia.celestialoutbreak.handler.SoundHandler;

import java.awt.*;

public class GreenLevel extends Level {

    public GreenLevel(Game game, InputHandler inputHandler, SoundHandler soundHandler) {
        super(game, inputHandler, soundHandler);
    }

    @Override
    public void levelSetup() {
        // Level setup
        // temp green lol
        levelColor = new Color(0x77DD77);

        // Paddle setup
        paddlePos = new Point((game.getWidth() / 2) - 25, game.getHeight() - 50);
        paddleWidth = 60;
        paddleHeight = 10;
        paddleSpeed = 8;
        paddleColor = Color.WHITE;

        // Ball Setup
        ballPos = new Point(game.getWidth() / 2, game.getHeight() / 2);
        ballSize = 15;
        ballSpeed = 6;
        ballColor = Color.WHITE;

        // BlockList setup
        blockAmount = 20;
        blockPos = new Point(30, 30);
        blockWidth = 50;
        blockHeight = 15;
        blockSpacing = 15;

        // bottomPanelMenu setup
        bottomPanelColor = Color.WHITE;
    }

}