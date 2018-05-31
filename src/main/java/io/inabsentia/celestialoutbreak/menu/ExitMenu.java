package io.inabsentia.celestialoutbreak.menu;

import io.inabsentia.celestialoutbreak.controller.GameController;
import io.inabsentia.celestialoutbreak.controller.GameController.State;
import io.inabsentia.celestialoutbreak.handler.InputHandler;

import java.awt.*;

public final class ExitMenu extends Menu {

    public ExitMenu(GameController gameController, InputHandler inputHandler, Color fontColor) {
        super(gameController, inputHandler, fontColor);
    }

    @Override
    public void update() {
        if (inputHandler.isOKPressed()) gameController.stop();
        if (inputHandler.isCancelPressed()) gameController.switchState(State.MAIN_MENU);
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(fontColor);
        drawMenuTitle(g);
        drawSubmenuTitle("Exit", g);
        drawXCenteredString("Do you wish to exit the game?", gameController.getHeight() / 2, g, msgFont);
        drawInformationPanel(g);
    }

}