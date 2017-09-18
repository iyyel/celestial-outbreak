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

        String msg = textHandler.menuSettingsMsg01 +
                textHandler.menuSettingsMsg02 + textHandler.menuSettingsMsg03 + textHandler.menuSettingsMsg04 + textHandler.menuSettingsMsg05 + textHandler.menuSettingsMsg06 + textHandler.menuSettingsMsg07 + textHandler.menuSettingsMsg08 + textHandler.menuSettingsMsg09;


        g.setFont(msgFont);
        /*
        g.drawString(textHandler.menuSettingsMsg01, 80, 260);
        g.drawString(textHandler.menuSettingsMsg02, 80, 295);

        g.drawString(textHandler.menuSettingsMsg03, 80, 365);
        g.drawString(textHandler.menuSettingsMsg04, 80, 400);

        g.drawString(textHandler.menuSettingsMsg05, 80, 470);
        g.drawString(textHandler.menuSettingsMsg06, 80, 505);

        g.drawString(textHandler.menuSettingsMsg07, 80, 575);
        g.drawString(textHandler.menuSettingsMsg08, 80, 610);
        g.drawString(textHandler.menuSettingsMsg09, 80, 645);
        */
        drawMenuMessage(msg, g, 80, 260, 35, 70);

        drawInformationPanel(g);
    }

}