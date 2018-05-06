package io.inabsentia.celestialoutbreak.menu;

import io.inabsentia.celestialoutbreak.controller.GameController;
import io.inabsentia.celestialoutbreak.controller.GameController.State;
import io.inabsentia.celestialoutbreak.handler.InputHandler;

import java.awt.*;

public final class PauseMenu extends Menu {

    public PauseMenu(GameController gameController, InputHandler inputHandler, Color fontColor) {
        super(gameController, inputHandler, fontColor);
    }

    @Override
    public void update() {
        if (inputHandler.isCancelPressed()) gameController.switchState(State.MENU);
        if (inputHandler.isPausePressed()) gameController.switchState(State.PLAY);
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(fontColor);
        drawMenuTitle(g);
        drawXCenteredString(textHandler.MENU_MSG_PAUSED, gameController.getHeight() / 2, g, msgFont);
        drawInformationPanel(g);
    }

}