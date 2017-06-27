package io.inabsentia.celestialoutbreak.menu;

import io.inabsentia.celestialoutbreak.controller.Game;
import io.inabsentia.celestialoutbreak.entity.State;
import io.inabsentia.celestialoutbreak.handler.InputHandler;

import java.awt.*;

public class FinishedLevelMenu extends Menu {

    private String prevLevelName, nextLevelName;

    public FinishedLevelMenu(Game game, InputHandler inputHandler) {
        super(game, inputHandler);
    }

    @Override
    public void update() {
        if (inputHandler.isConfirmPressed()) game.switchState(State.NEW_LEVEL);
        if (inputHandler.isRejectPressed()) game.switchState(State.MENU);
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

    public void setLevelNames(String prevLevelName, String nextLevelName) {
        this.prevLevelName = prevLevelName;
        this.nextLevelName = nextLevelName;
    }

}