package io.inabsentia.celestialoutbreak.menu;

import io.inabsentia.celestialoutbreak.controller.Game;
import io.inabsentia.celestialoutbreak.entity.State;
import io.inabsentia.celestialoutbreak.handler.InputHandler;

import java.awt.*;

public class SettingsMenu extends Menu {

    public SettingsMenu(Game game, InputHandler inputHandler, Color fontColor) {
        super(game, inputHandler, fontColor);
    }

    @Override
    public void update() {
        if (inputHandler.isCancelPressed()) game.switchState(State.MENU);
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(fontColor);
        drawMenuTitle(g);

        drawSubmenuTitle("Settings", g);
        g.setFont(msgFont);

        int xPos = 80;
        int yPos = 260;
        int yPosInc = 35;
        int yPosSepInc = 70;

        g.drawString(textHandler.menuSettingsMsg01, xPos, yPos);
        g.drawString(textHandler.menuSettingsMsg02, xPos, yPos + yPosInc);

        g.drawString(textHandler.menuSettingsMsg03, xPos, yPos + yPosSepInc + yPosInc);
        g.drawString(textHandler.menuSettingsMsg04, xPos, yPos + yPosSepInc + yPosInc * 2);

        g.drawString(textHandler.menuSettingsMsg05, xPos, yPos + yPosSepInc * 2 + yPosInc * 2);
        g.drawString(textHandler.menuSettingsMsg06, xPos, yPos + yPosSepInc * 2 + yPosInc * 3);

        g.drawString(textHandler.menuSettingsMsg07, xPos, yPos + yPosSepInc * 3 + yPosInc * 3);
        g.drawString(textHandler.menuSettingsMsg08, xPos, yPos + yPosSepInc * 3 + yPosInc * 4);
        g.drawString(textHandler.menuSettingsMsg09, xPos, yPos + yPosSepInc * 3 + yPosInc * 5);

        drawInformationPanel(g);
    }

}