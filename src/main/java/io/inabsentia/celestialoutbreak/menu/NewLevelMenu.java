package io.inabsentia.celestialoutbreak.menu;

import io.inabsentia.celestialoutbreak.controller.Game;
import io.inabsentia.celestialoutbreak.entity.State;
import io.inabsentia.celestialoutbreak.handler.InputHandler;
import io.inabsentia.celestialoutbreak.level.Level;

import java.awt.*;

public class NewLevelMenu extends Menu {

    private Level activeLevel;

    public NewLevelMenu(Game game, InputHandler inputHandler, Color fontColor) {
        super(game, inputHandler, fontColor);
    }

    @Override
    public void update() {
        if (inputHandler.isOKPressed()) game.switchState(State.PLAY);
        if (inputHandler.isCancelPressed()) game.switchState(State.MENU);
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
            drawXCenteredString(activeLevel.getDesc(), game.getHeight() / 2, g, msgFont);
        }

        drawInformationPanel(g);
    }

}