package io.iyyel.celestialoutbreak.menu.main_menu;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.menu.AbstractMenu;

import java.awt.*;

public final class PauseMenu extends AbstractMenu {

    private boolean isFirstUpdate = true;

    public PauseMenu(GameController gameController) {
        super(gameController);
    }

    @Override
    public void update() {
        decInputTimer();

        if (isFirstUpdate) {
            isFirstUpdate = false;
            resetInputTimer();
        }

        if (inputHandler.isCancelPressed() && isInputAvailable()) {
            resetInputTimer();
            isFirstUpdate = true;
            menuUseClip.play(false);
            gameController.switchState(GameController.State.MAIN_MENU);
        }

        if (inputHandler.isPausePressed() && isInputAvailable()) {
            resetInputTimer();
            isFirstUpdate = true;
            menuUseClip.play(false);
            gameController.switchState(GameController.State.PLAY_SCREEN);
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(menuFontColor);
        drawMenuTitle(g);
        drawCenterString(textHandler.MENU_MSG_PAUSED, gameController.getHeight() / 2, g, msgFont);
        drawInformationPanel(g);
    }

}