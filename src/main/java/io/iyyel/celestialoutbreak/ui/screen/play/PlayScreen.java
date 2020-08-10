package io.iyyel.celestialoutbreak.ui.screen.play;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.handler.LevelHandler;
import io.iyyel.celestialoutbreak.ui.screen.AbstractScreen;

import java.awt.*;

public class PlayScreen extends AbstractScreen {

    private final LevelHandler levelHandler = LevelHandler.getInstance();

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
            util.pauseTimer();
            gameController.switchState(GameController.State.PAUSE);
        }

    }

    @Override
    public void render(Graphics2D g) {
        levelHandler.render(g);
    }

}