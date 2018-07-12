package io.iyyel.celestialoutbreak.menu.main_menu;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.controller.GameController.State;
import io.iyyel.celestialoutbreak.menu.Menu;

import java.awt.*;

public final class AboutMenu extends Menu {

    public AboutMenu(GameController gameController) {
        super(gameController);
    }

    @Override
    public void update() {
        if (inputHandler.isCancelPressed()) {
            gameController.switchState(State.MAIN_MENU);
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(menuFontColor);
        drawMenuTitle(g);

        drawSubmenuTitle("About", g);

        g.setFont(msgFont);

        drawInformationPanel(g);
    }

}