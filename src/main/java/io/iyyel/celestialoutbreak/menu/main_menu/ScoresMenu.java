package io.iyyel.celestialoutbreak.menu.main_menu;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.menu.AbstractMenu;

import java.awt.*;

public final class ScoresMenu extends AbstractMenu {

    public ScoresMenu(GameController gameController) {
        super(gameController);
    }

    @Override
    public void update() {
        if (inputHandler.isCancelPressed()) {
            menuUseClip.play(false);
            gameController.switchState(GameController.State.MAIN_MENU);
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(menuFontColor);
        drawMenuTitle(g);

        drawSubmenuTitle("Scores", g);

        g.setFont(msgFont);

        drawInformationPanel(g);
    }

}