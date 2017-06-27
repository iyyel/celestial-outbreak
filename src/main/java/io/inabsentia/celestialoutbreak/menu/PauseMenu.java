package io.inabsentia.celestialoutbreak.menu;

import io.inabsentia.celestialoutbreak.controller.Game;
import io.inabsentia.celestialoutbreak.handler.InputHandler;

import java.awt.*;

public class PauseMenu extends Menu {

    public PauseMenu(Game game, InputHandler inputHandler) {
        super(game, inputHandler);
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(fontColor);
        drawMenuTitle(g);
        drawXCenteredString(textHandler.pauseMsg, game.getHeight() / 2, g, msgFont);
        drawInformationPanel(g);
    }

}