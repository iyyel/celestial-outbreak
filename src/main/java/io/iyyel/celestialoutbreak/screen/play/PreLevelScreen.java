package io.iyyel.celestialoutbreak.screen.play;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.controller.GameController.State;
import io.iyyel.celestialoutbreak.handler.LevelHandler;
import io.iyyel.celestialoutbreak.level.Level;
import io.iyyel.celestialoutbreak.screen.AbstractScreen;

import java.awt.*;

public final class PreLevelScreen extends AbstractScreen {

    private final LevelHandler levelHandler = LevelHandler.getInstance();

    public PreLevelScreen(GameController gameController) {
        super(gameController);
    }

    @Override
    public void update() {
        decInputTimer();

        if (inputHandler.isOKPressed() && isInputAvailable()) {
            resetInputTimer();
            soundHandler.stopAllSound();
            menuUseClip.play(false);
            levelHandler.getActiveLevel().playSound();
            utils.startTimer();
            gameController.switchState(State.PLAY);
        }

    }

    @Override
    public void render(Graphics2D g) {
        Level activeLevel = levelHandler.getActiveLevel();
        drawScreenTitle(g);
        drawScreenSubtitle(activeLevel.getName(), g);
        drawScreenMessage(activeLevel.getDesc(), 0, g);
        drawScreenToolTip("Press '" + textHandler.BTN_CONTROL_FORWARD_OK + "' to start.", g);
        drawScreenInfoPanel(g);
    }

}