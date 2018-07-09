package io.iyyel.celestialoutbreak.menu.main_menu;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.handler.InputHandler;
import io.iyyel.celestialoutbreak.menu.Menu;

import java.awt.*;

public final class PauseMenu extends Menu {

    public PauseMenu(GameController gameController, InputHandler inputHandler, Color fontColor) {
        super(gameController, inputHandler, fontColor);
    }

    @Override
    public void update() {
        if (inputHandler.isCancelPressed())
            gameController.switchState(GameController.State.MAIN_MENU);

        if (inputHandler.isPausePressed())
            gameController.switchState(GameController.State.PLAY);
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(fontColor);
        drawMenuTitle(g);
        drawXCenteredString(textHandler.MENU_MSG_PAUSED, gameController.getHeight() / 2, g, msgFont);
        drawInformationPanel(g);
    }

}