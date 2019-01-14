package io.iyyel.celestialoutbreak.screen.play;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.controller.GameController.State;
import io.iyyel.celestialoutbreak.handler.LevelHandler;
import io.iyyel.celestialoutbreak.level.Level;
import io.iyyel.celestialoutbreak.screen.AbstractScreen;

import java.awt.*;

public final class PostLevelScreen extends AbstractScreen {

    private final LevelHandler levelHandler = LevelHandler.getInstance();

    private boolean isFirstUpdate = true;
    private boolean hasWon = false;

    public PostLevelScreen(GameController gameController) {
        super(gameController);
    }

    @Override
    public void update() {
        decInputTimer();

        if (isFirstUpdate) {
            isFirstUpdate = false;
            utils.stopTimer();
            hasWon = levelHandler.getActiveLevel().isWon();
        }

        if (inputHandler.isOKPressed() && isInputAvailable()) {
            resetInputTimer();
            isFirstUpdate = true;
            levelHandler.getActiveLevel().stopSound();
            levelHandler.resetActiveLevel();
            gameController.switchState(State.MAIN);
        }
    }

    @Override
    public void render(Graphics2D g) {
        Level activeLevel = levelHandler.getActiveLevel();

        drawScreenTitle(g);
        drawScreenSubtitle(activeLevel.getName(), g);

        if (hasWon) {
            drawScreenMessage("You are victorious! The " + levelHandler.getActiveLevel().getName() + " level has been defeated.", 0, g);
            drawScreenMessage("You reached a total score of 1234.", 50, g);
            drawScreenMessage("Time: " + textHandler.getTimeString(utils.getTimeElapsed()), 100, g);
        } else {
            drawScreenMessage("You have lost. The " + levelHandler.getActiveLevel().getName() + " level shines in grace upon you.", 0, g);
            drawScreenMessage("You reached a total score of 1234.", 50, g);
            drawScreenMessage("Time: " + textHandler.getTimeString(utils.getTimeElapsed()), 100, g);
        }

        drawScreenToolTip("Press '" + textHandler.BTN_CONTROL_FORWARD_OK + "' to go to the main menu.", g);
        drawScreenInfoPanel(g);
    }

}