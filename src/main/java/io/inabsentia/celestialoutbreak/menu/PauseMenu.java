package io.inabsentia.celestialoutbreak.menu;

import io.inabsentia.celestialoutbreak.controller.Game;
import io.inabsentia.celestialoutbreak.handler.InputHandler;

import java.awt.*;

public class PauseMenu extends Menu {

    private final Font titleFont, pauseMsgFont, pauseStartMsgFont;

    private final Color fontColor;

    public PauseMenu(Game game, InputHandler inputHandler, Color fontColor) {
        super(game, inputHandler);
        this.fontColor = fontColor;

        titleFont = new Font("Verdana", Font.PLAIN, 52);
        pauseMsgFont = new Font("Verdana", Font.PLAIN, 45);
        pauseStartMsgFont = new Font("Verdana", Font.PLAIN, 32);
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(fontColor);

        g.setFont(titleFont);
        g.drawString(textHandler.TITLE, game.getWidth() / 2 - 220, 100);

        g.setFont(pauseMsgFont);
        g.drawString(textHandler.pauseMsg, game.getWidth() / 2 - 80, 300);

        g.setFont(pauseStartMsgFont);
        g.drawString(textHandler.pauseStartMsg, game.getWidth() / 2 - 225, game.getHeight() / 2);
    }

}