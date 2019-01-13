package io.iyyel.celestialoutbreak.screen.play;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.screen.AbstractScreen;

import java.awt.*;

public class PlayScreen extends AbstractScreen {

    public PlayScreen(GameController gameController) {
        super(gameController);
    }

    @Override
    public void update() {
        levelHandler.update();
        decInputTimer();

        if (inputHandler.isPausePressed() && isInputAvailable()) {
            resetInputTimer();
            menuUseClip.play(false);
            levelHandler.getActiveLevel().pauseSound();
            utils.pauseTimer();
            gameController.switchState(GameController.State.PAUSE);
        }

    }

    @Override
    public void render(Graphics2D g) {
        levelHandler.render(g);
    }

}