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
        int msgXPos = 80;
        int msgYPos = 260;
        int msgYInc = 35;
        int msgYSepInc = 70;

        g.setColor(fontColor);
        drawMenuTitle(g);

        drawSubmenuTitle("Settings", g);

        g.setFont(msgFont);

        g.drawString(textHandler.menuSettingsMsg01, msgXPos, 260);
        g.drawString(textHandler.menuSettingsMsg02, msgXPos, 295);

        g.drawString(textHandler.menuSettingsMsg03, msgXPos, 365);
        g.drawString(textHandler.menuSettingsMsg04, msgXPos, 400);

        g.drawString(textHandler.menuSettingsMsg05, msgXPos, 470);
        g.drawString(textHandler.menuSettingsMsg06, msgXPos, 505);

        g.drawString(textHandler.menuSettingsMsg07, msgXPos, 575);
        g.drawString(textHandler.menuSettingsMsg08, msgXPos, 610);
        g.drawString(textHandler.menuSettingsMsg09, msgXPos, 645);

        drawInformationPanel(g);
    }

}