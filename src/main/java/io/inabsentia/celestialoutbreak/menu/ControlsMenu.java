package io.inabsentia.celestialoutbreak.menu;

import io.inabsentia.celestialoutbreak.controller.Game;
import io.inabsentia.celestialoutbreak.entity.State;
import io.inabsentia.celestialoutbreak.handler.InputHandler;

import java.awt.*;

public class ControlsMenu extends Menu {

    public ControlsMenu(Game game, InputHandler inputHandler, Color fontColor) {
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
        g.setFont(msgFont);

       // drawXCenteredString("Controls", game.getHeight() / 2 - 170, g, msgFont);
        drawSubmenuTitle("Controls", g, msgFont);
        g.drawString("Movement & Navigation:      WASD/Arrow keys", 200, game.getHeight() / 2 - 100);
        g.drawString("OK:                                     z", 250, game.getHeight() / 2 - 50);
        g.drawString("Cancel:                                x", 250, game.getHeight() / 2);
        g.drawString("Select & Use:                       space", 250, game.getHeight() / 2 + 50);

        drawInformationPanel(g);
    }

}