package io.inabsentia.celestialoutbreak.menu;


import io.inabsentia.celestialoutbreak.controller.Game;
import io.inabsentia.celestialoutbreak.entity.State;
import io.inabsentia.celestialoutbreak.handler.InputHandler;

import java.awt.*;

public class AboutMenu extends Menu {

    public AboutMenu(Game game, InputHandler inputHandler) {
        super(game, inputHandler);
    }

    @Override
    public void update() {
        if (inputHandler.isRejectPressed()) game.switchState(State.MENU);
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(fontColor);
        drawMenuTitle(g);
        drawXCenteredString("Controls are as follows", game.getHeight() / 2 - 200, g, msgFont);
        drawXCenteredString("WASD / Arrow keys to navigate and move", game.getHeight() / 2 - 100, g, msgFont);
        drawXCenteredString("confirm:           z", game.getHeight() / 2, g, msgFont);
        drawXCenteredString("reject:           x", game.getHeight() / 2 + 100, g, msgFont);
        drawXCenteredString("select/use:       space bar", game.getHeight() / 2 + 200, g, msgFont);
        drawGamePanel(g);
    }

}