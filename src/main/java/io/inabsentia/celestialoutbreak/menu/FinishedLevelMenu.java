package io.inabsentia.celestialoutbreak.menu;

import io.inabsentia.celestialoutbreak.controller.Game;
import io.inabsentia.celestialoutbreak.entity.State;
import io.inabsentia.celestialoutbreak.handler.InputHandler;

import java.awt.*;

public class FinishedLevelMenu extends Menu {

    private final Font titleFont, msgFont;
    private final Color fontColor;

    private String prevLevelName, nextLevelName;

    public FinishedLevelMenu(Game game, InputHandler inputHandler, Color fontColor) {
        super(game, inputHandler);
        this.fontColor = fontColor;

        titleFont = new Font("Verdana", Font.PLAIN, 52);
        msgFont = new Font("Verdana", Font.PLAIN, 32);
    }

    @Override
    public void update() {
        if (inputHandler.yes) game.changeState(State.PLAY);
        if (inputHandler.no) game.changeState(State.MENU);
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(fontColor);

        g.setFont(titleFont);
        g.drawString(textHandler.TITLE, game.getWidth() / 2 - 220, 100);

        g.setFont(msgFont);
        g.drawString(prevLevelName + " has been obliterated", game.getWidth() / 2 - 190, 300);
        g.drawString(nextLevelName + " is the next awaiting challenge", game.getWidth() / 2 - 265, 350);
        g.drawString("Are you prepared?", game.getWidth() / 2 - 155, 450);
    }

    public void setLevelNames(String prevLevelName, String nextLevelName) {
        this.prevLevelName = prevLevelName;
        this.nextLevelName = nextLevelName;
    }

}