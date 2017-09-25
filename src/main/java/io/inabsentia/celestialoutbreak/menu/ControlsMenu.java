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

        int xPos = 135;
        int xPoxInc = 130;
        int yPos = -50;
        int yPosInc = 70;

        drawSubmenuTitle("Controls", g);

        g.setFont(msgFont);
        g.drawString(textHandler.menuControlsMsg01, xPos, game.getHeight() / 2 + yPos);
        g.drawString(textHandler.menuControlsMsg02, game.getWidth() / 2 + xPoxInc, game.getHeight() / 2 + yPos);

        g.drawString(textHandler.menuControlsMsg03, xPos, game.getHeight() / 2 + (yPos + yPosInc));
        g.drawString(textHandler.menuControlsMsg04, game.getWidth() / 2 + xPoxInc, game.getHeight() / 2 + (yPos + yPosInc));

        g.drawString(textHandler.menuControlsMsg05, xPos, game.getHeight() / 2 + (yPos + yPosInc * 2));
        g.drawString(textHandler.menuControlsMsg06, game.getWidth() / 2 + xPoxInc, game.getHeight() / 2 + (yPos + yPosInc * 2));

        g.drawString(textHandler.menuControlsMsg07, xPos, game.getHeight() / 2 + (yPos + yPosInc * 3));
        g.drawString(textHandler.menuControlsMsg08, game.getWidth() / 2 + xPoxInc, game.getHeight() / 2 + (yPos + yPosInc * 3));

        drawInformationPanel(g);
    }

}