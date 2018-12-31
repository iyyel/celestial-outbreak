package io.iyyel.celestialoutbreak.menu.play;

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
            doFirstUpdate();
        }

        if (inputHandler.isCancelPressed() && isInputAvailable()) {
            resetInputTimer();
            isFirstUpdate = true;
            menuUseClip.play(false);
            gameController.switchState(GameController.State.MAIN);
        }

        if (inputHandler.isPausePressed() && isInputAvailable()) {
            resetInputTimer();
            isFirstUpdate = true;
            menuUseClip.play(false);
            gameController.switchState(GameController.State.PLAY);
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(menuFontColor);
        drawMenuTitle(g);
        drawSubmenuTitle(textHandler.TITLE_PAUSE_SCREEN, g);
        drawCenterString(textHandler.GAME_TITLE + " is paused.", 350, g, msgFont);
        drawMenuToolTip("Press '" + textHandler.BTN_CONTROL_PAUSE + "' to resume or '" + textHandler.BTN_CONTROL_BACK_CANCEL + "' to exit level.", g);
        drawInfoPanel(g);
    }

    private void doFirstUpdate() {
        isFirstUpdate = false;
        resetInputTimer();
    }

}