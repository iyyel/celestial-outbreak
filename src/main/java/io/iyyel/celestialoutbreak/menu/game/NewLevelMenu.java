package io.iyyel.celestialoutbreak.menu.game;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.controller.GameController.State;
import io.iyyel.celestialoutbreak.level.Level;
import io.iyyel.celestialoutbreak.menu.Menu;

import java.awt.*;

public final class NewLevelMenu extends Menu {

    private Level activeLevel;

    public NewLevelMenu(GameController gameController) {
        super(gameController);
    }

    @Override
    public void update() {
        if (inputHandler.isOKPressed()) {
            gameController.switchState(State.PLAY);
        }

        if (inputHandler.isCancelPressed()) {
            gameController.switchState(State.MAIN_MENU);
        }
    }

    public void updateActiveLevel(Level activeLevel) {
        this.activeLevel = activeLevel;
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(menuFontColor);
        drawXCenteredString(textHandler.GAME_TITLE, 100, g, titleFont);

        /*
         * A small amount of time is needed
         * for the update function to catch up,
         * so it's needed to check whether the
         * active level is null or not.
         */
        if (activeLevel != null) {
            drawSubmenuTitle(activeLevel.getName(), g);
            drawXCenteredString(activeLevel.getDesc(), gameController.getHeight() / 2, g, msgFont);
            //drawMenuMessage(activeLevel.getDesc(), g, 80,gameController.getHeight() / 2, 20, 20, 60);
        }

        drawInformationPanel(g);
    }

}