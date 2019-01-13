package io.iyyel.celestialoutbreak.screen.main_menu;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.screen.AbstractScreen;

import java.awt.*;

public final class ExitScreen extends AbstractScreen {

    public ExitScreen(GameController gameController) {
        super(gameController);
    }

    @Override
    public void update() {
        decInputTimer();

        if (inputHandler.isOKPressed() && isInputAvailable()) {
            resetInputTimer();
            gameController.stop();
        }

        if (inputHandler.isCancelPressed() && isInputAvailable()) {
            resetInputTimer();
            menuUseClip.play(false);
            if (gameController.getPrevState() != GameController.State.NONE &&
                    gameController.getPrevState() == GameController.State.WELCOME) {
                gameController.switchState(GameController.State.WELCOME);
            } else {
                gameController.switchState(GameController.State.MAIN);
            }
        }

    }

    @Override
    public void render(Graphics2D g) {
        drawScreenTitle(g);
        drawScreenSubtitle(textHandler.TITLE_EXIT_SCREEN, g);
        drawScreenMessage("Do you wish to exit " + textHandler.GAME_TITLE + "?", 0, g);
        drawScreenToolTip("Press '" + textHandler.BTN_CONTROL_FORWARD_OK + "' to confirm or '" + textHandler.BTN_CONTROL_BACK_CANCEL + "' to go back.", g);
        drawScreenInfoPanel(g);
    }

}