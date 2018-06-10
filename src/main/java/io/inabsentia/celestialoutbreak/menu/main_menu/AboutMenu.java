package io.inabsentia.celestialoutbreak.menu.main_menu;

import io.inabsentia.celestialoutbreak.controller.GameController;
import io.inabsentia.celestialoutbreak.controller.GameController.State;
import io.inabsentia.celestialoutbreak.handler.InputHandler;
import io.inabsentia.celestialoutbreak.menu.Menu;

import java.awt.*;

public final class AboutMenu extends Menu {

    public AboutMenu(GameController gameController, InputHandler inputHandler, Color fontColor) {
        super(gameController, inputHandler, fontColor);
    }

    @Override
    public void update() {
        if (inputHandler.isCancelPressed())
            gameController.switchState(State.MAIN_MENU);
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(fontColor);
        drawMenuTitle(g);

        drawSubmenuTitle("About", g);

        g.setFont(msgFont);

        drawInformationPanel(g);
    }

}