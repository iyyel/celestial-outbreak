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

        int textXPos = 135;
        int textXPosOffset = 130;
        int textYPos = -50;
        int textYPosInc = 70;

        drawSubmenuTitle("Controls", g);

        g.setFont(msgFont);
        g.drawString("Movement & Navigation:", textXPos, game.getHeight() / 2 + textYPos);
        g.drawString("WASD/Arrow keys", game.getWidth() / 2 + textXPosOffset, game.getHeight() / 2 + textYPos);

        g.drawString("Confirm/OK:", textXPos, game.getHeight() / 2 + (textYPos + textYPosInc));
        g.drawString("z", game.getWidth() / 2 + textXPosOffset, game.getHeight() / 2 + (textYPos + textYPosInc));

        g.drawString("Cancel/Back:", textXPos, game.getHeight() / 2 + (textYPos + textYPosInc * 2));
        g.drawString("x", game.getWidth() / 2 + textXPosOffset, game.getHeight() / 2 + (textYPos + textYPosInc * 2));

        g.drawString("Select & Use:", textXPos, game.getHeight() / 2 + (textYPos + textYPosInc * 3));
        g.drawString("Space", game.getWidth() / 2 + textXPosOffset, game.getHeight() / 2 + (textYPos + textYPosInc * 3));

        drawInformationPanel(g);
    }

}