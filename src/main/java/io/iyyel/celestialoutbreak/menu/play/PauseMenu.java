package io.iyyel.celestialoutbreak.menu.play;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.handler.LevelHandler;
import io.iyyel.celestialoutbreak.menu.AbstractMenu;

import java.awt.*;

public final class PauseMenu extends AbstractMenu {

    private final LevelHandler levelHandler = LevelHandler.getInstance();

    private boolean isFirstUpdate = true;
    private boolean isExiting = false;

    private String pauseStatusText = "Exiting the level will result in lost progress.";

    public PauseMenu(GameController gameController) {
        super(gameController);
    }

    @Override
    public void update() {
        levelHandler.getActiveLevel().pause();
        decInputTimer();

        if (isFirstUpdate) {
            doFirstUpdate();
        }

        if (inputHandler.isOKPressed() && isInputAvailable() && !isExiting) {
            resetInputTimer();
            isExiting = true;
            menuUseClip.play(false);
            pauseStatusText = "Are you sure you want to exit the level?";
        }

        if (inputHandler.isOKPressed() && isInputAvailable() && isExiting) {
            resetInputTimer();
            isFirstUpdate = true;
            menuUseClip.play(false);
            pauseStatusText = "Exiting the level will result in lost progress.";
            levelHandler.resetActiveLevel();
            gameController.switchState(GameController.State.MAIN);
        }

        if (inputHandler.isPausePressed() && isInputAvailable()) {
            resetInputTimer();
            isFirstUpdate = true;
            menuUseClip.play(false);
            pauseStatusText = "Exiting the level will result in lost progress.";
            gameController.switchState(GameController.State.PLAY);
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(menuFontColor);
        drawMenuTitle(g);
        drawSubmenuTitle(textHandler.TITLE_PAUSE_SCREEN, g);
        drawCenterString(textHandler.GAME_TITLE + " is paused.", 350, g, msgFont);
        drawCenterString(pauseStatusText, 630, g, inputBtnFont);
        drawMenuToolTip("Press '" + textHandler.BTN_CONTROL_PAUSE + "' to resume or '" + textHandler.BTN_CONTROL_FORWARD_OK + "' to exit level.", g);
        drawInfoPanel(g);
    }

    private void doFirstUpdate() {
        isFirstUpdate = false;
        isExiting = false;
        resetInputTimer();
    }

}