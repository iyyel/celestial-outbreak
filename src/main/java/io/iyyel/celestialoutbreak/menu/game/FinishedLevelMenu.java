package io.iyyel.celestialoutbreak.menu.game;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.controller.GameController.State;
import io.iyyel.celestialoutbreak.handler.InputHandler;
import io.iyyel.celestialoutbreak.menu.Menu;

import java.awt.*;

public final class FinishedLevelMenu extends Menu {

    private String prevLevelName, nextLevelName;

    public FinishedLevelMenu(GameController gameController, InputHandler inputHandler, Color fontColor) {
        super(gameController, inputHandler, fontColor);
    }

    @Override
    public void update() {
        if (inputHandler.isOKPressed())
            gameController.switchState(State.NEW_LEVEL);

        if (inputHandler.isCancelPressed())
            gameController.switchState(State.MAIN_MENU);
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(fontColor);
        drawMenuTitle(g);
        drawXCenteredString(prevLevelName + " has been obliterated", 300, g, msgFont);
        drawXCenteredString(nextLevelName + " is the next awaiting challenge", 350, g, msgFont);
        drawXCenteredString("Are you prepared?", 450, g, msgFont);
        drawInformationPanel(g);
    }

    public void updateLevelNames(String prevLevelName, String nextLevelName) {
        this.prevLevelName = prevLevelName;
        this.nextLevelName = nextLevelName;
    }

}