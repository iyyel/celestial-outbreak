package io.iyyel.celestialoutbreak.menu.main_menu;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.handler.InputHandler;
import io.iyyel.celestialoutbreak.menu.Menu;

import java.awt.*;

public final class ScoresMenu extends Menu {

    public ScoresMenu(GameController gameController, InputHandler inputHandler, Color fontColor) {
        super(gameController, inputHandler, fontColor);
    }

    @Override
    public void update() {
        if (inputHandler.isCancelPressed())
            gameController.switchState(GameController.State.MAIN_MENU);
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(fontColor);
        drawMenuTitle(g);

        drawSubmenuTitle("Scores", g);

        g.setFont(msgFont);

        drawInformationPanel(g);
    }

}