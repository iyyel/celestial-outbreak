package io.iyyel.celestialoutbreak.menu.play;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.controller.GameController.State;
import io.iyyel.celestialoutbreak.level.Level;
import io.iyyel.celestialoutbreak.menu.AbstractMenu;

import java.awt.*;

public final class NewLevelMenu extends AbstractMenu {

    private Level activeLevel;

    public NewLevelMenu(GameController gameController) {
        super(gameController);
    }

    @Override
    public void update() {
        decInputTimer();

        if (inputHandler.isOKPressed() && isInputAvailable()) {
            resetInputTimer();
            gameController.switchState(State.PLAY_SCREEN);
        }

        if (inputHandler.isCancelPressed() && isInputAvailable()) {
            resetInputTimer();
            menuUseClip.play(false);
            gameController.switchState(State.MAIN_MENU);
        }
    }

    public void updateActiveLevel(Level activeLevel) {
        this.activeLevel = activeLevel;
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(menuFontColor);
        drawCenterString(textHandler.GAME_TITLE, 100, g, titleFont);

        /*
         * A small amount of time is needed
         * for the update function to catch up,
         * so it's needed to check whether the
         * active level is null or not.
         */
        if (activeLevel != null) {
            drawSubmenuTitle(activeLevel.getName(), g);
            drawCenterString(activeLevel.getDesc(), gameController.getHeight() / 2, g, msgFont);
            //drawMenuMessage(activeLevel.getDesc(), g, 80,gameController.getHeight() / 2, 20, 20, 60);
        }

        drawInfoPanel(g);
    }

}