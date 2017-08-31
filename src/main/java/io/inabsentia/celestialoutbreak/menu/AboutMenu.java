package io.inabsentia.celestialoutbreak.menu;

import io.inabsentia.celestialoutbreak.controller.Game;
import io.inabsentia.celestialoutbreak.entity.State;
import io.inabsentia.celestialoutbreak.handler.InputHandler;

import java.awt.*;

public class AboutMenu extends Menu {

    public AboutMenu(Game game, InputHandler inputHandler, Color fontColor) {
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

        drawSubmenuTitle("About", g);

        g.setFont(msgFont);
        g.drawString("All of the configuration related files can be found in the", 80, 260);
        g.drawString("following directory '" + textHandler.MAIN_DIR + "'", 80, 305);

        g.drawString("General settings can be found in the settings.config file,", 80, 375);
        g.drawString("in the settings directory.", 80, 410);

        g.drawString("Level specific settings can be found in the various level files", 80, 480);
        g.drawString("in the levels directory, e.g. mars_level.config.", 80, 515);

        g.drawString("Levels are played in order from the levels.config file.", 80, 585);
        g.drawString("If creating custom levels, make sure to add them to this file.", 80, 620);

        drawInformationPanel(g);
    }

}