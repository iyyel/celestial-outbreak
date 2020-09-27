package io.iyyel.celestialoutbreak.ui.screen.play;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.handler.LevelHandler;
import io.iyyel.celestialoutbreak.ui.screen.AbstractScreen;

import java.awt.*;

public final class PauseScreen extends AbstractScreen {

    private final LevelHandler levelHandler = LevelHandler.getInstance();

    private boolean isFirstUpdate = true;
    private boolean isExiting = false;

    private String pauseStatusText = "Exiting the level will result in lost progress.";

    public PauseScreen(GameController gameController) {
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
            menuNavClip.play(false);
            pauseStatusText = "Are you sure you want to exit the level?";
        }

        if (inputHandler.isOKPressed() && isInputAvailable() && isExiting) {
            resetInputTimer();
            isFirstUpdate = true;
            menuNavClip.play(false);
            pauseStatusText = "Exiting the level will result in lost progress.";
            levelHandler.resetActiveLevel();
            soundHandler.getSoundClip(textHandler.SOUND_FILE_NAME_PAUSE).stop();
            util.stopTimer();
            util.resetTimer();
            gameController.switchState(GameController.State.SELECT_LEVEL);
        }

        if (inputHandler.isPausePressed() && isInputAvailable()) {
            resetInputTimer();
            isFirstUpdate = true;
            menuNavClip.play(false);
            pauseStatusText = "Exiting the level will result in lost progress.";
            soundHandler.getSoundClip(textHandler.SOUND_FILE_NAME_PAUSE).stop();
            levelHandler.getActiveLevel().playSound();
            util.resumeTimer();
            gameController.switchState(GameController.State.PLAY);
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(screenFontColor);
        drawTitle(g);
        drawSubtitle(textHandler.TITLE_PAUSE_SCREEN, g);
        drawCenteredText(textHandler.GAME_TITLE + " is paused.", 0, g);
        drawCenteredText(pauseStatusText, 630, inputBtnFont, g);
        drawToolTip("Press " + textHandler.BTN_CONTROL_PAUSE + " to resume or " + textHandler.BTN_CONTROL_OK + " to exit level.", g);
        drawInfoPanel(g);
    }

    private void doFirstUpdate() {
        isFirstUpdate = false;
        isExiting = false;
        resetInputTimer();
    }

}