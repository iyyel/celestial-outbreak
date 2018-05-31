package io.inabsentia.celestialoutbreak.menu;

import io.inabsentia.celestialoutbreak.controller.GameController;
import io.inabsentia.celestialoutbreak.controller.GameController.State;
import io.inabsentia.celestialoutbreak.handler.InputHandler;
import io.inabsentia.celestialoutbreak.level.Level;

import java.awt.*;

public final class NewLevelMenu extends Menu {

    private Level activeLevel;

    public NewLevelMenu(GameController gameController, InputHandler inputHandler, Color fontColor) {
        super(gameController, inputHandler, fontColor);
    }

    @Override
    public void update() {
        if (inputHandler.isOKPressed()) gameController.switchState(State.PLAY);
        if (inputHandler.isCancelPressed()) gameController.switchState(State.MAIN_MENU);
    }

    public void updateActiveLevel(Level activeLevel) {
        this.activeLevel = activeLevel;
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(fontColor);
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