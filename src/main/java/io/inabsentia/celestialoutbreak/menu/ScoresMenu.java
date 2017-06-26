package io.inabsentia.celestialoutbreak.menu;


import io.inabsentia.celestialoutbreak.controller.Game;
import io.inabsentia.celestialoutbreak.entity.State;
import io.inabsentia.celestialoutbreak.handler.InputHandler;

import java.awt.*;

public class ScoresMenu extends Menu {

    public ScoresMenu(Game game, InputHandler inputHandler) {
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
        drawXCenteredString("Not yet implemented :(", game.getHeight() / 2, g, msgFont);
        drawGamePanel(g);
    }

}