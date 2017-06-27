package io.inabsentia.celestialoutbreak.menu;


import io.inabsentia.celestialoutbreak.controller.Game;
import io.inabsentia.celestialoutbreak.entity.State;
import io.inabsentia.celestialoutbreak.handler.InputHandler;

import java.awt.*;

public class SettingsMenu extends Menu {

    public SettingsMenu(Game game, InputHandler inputHandler) {
        super(game, inputHandler);
    }

    @Override
    public void update() {
        if (inputHandler.isRejectPressed()) game.switchState(State.MENU);
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(fontColor);
        drawMenuTitle(g);
        drawXCenteredString(textHandler.menuSettingsMsg01, 200, g, msgFont);
        drawXCenteredString(textHandler.menuSettingsMsg02, 250, g, msgFont);
        drawXCenteredString(textHandler.menuSettingsMsg03, 350, g, msgFont);
        drawXCenteredString(textHandler.menuSettingsMsg04, 400, g, msgFont);
        drawXCenteredString(textHandler.menuSettingsMsg05, 450, g, msgFont);
        drawXCenteredString(textHandler.menuSettingsMsg06, 500, g, msgFont);
        drawInformationPanel(g);
    }

}