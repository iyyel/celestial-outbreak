package io.iyyel.celestialoutbreak.ui.screen.main;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.ui.screen.AbstractScreen;

import java.awt.*;

public final class ScoresScreen extends AbstractScreen {

    public ScoresScreen(GameController gameController) {
        super(gameController);
    }

    @Override
    public void update() {
        super.update();
        updateNavCancel(GameController.State.MAIN);
    }

    @Override
    public void render(Graphics2D g) {
        drawScreenTitles(textHandler.TITLE_SCORES_SCREEN, g);
        drawScreenInfoPanel(g);
    }

}