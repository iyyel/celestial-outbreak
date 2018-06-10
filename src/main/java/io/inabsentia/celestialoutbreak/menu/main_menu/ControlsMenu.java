package io.inabsentia.celestialoutbreak.menu.main_menu;

import io.inabsentia.celestialoutbreak.controller.GameController;
import io.inabsentia.celestialoutbreak.controller.GameController.State;
import io.inabsentia.celestialoutbreak.handler.InputHandler;
import io.inabsentia.celestialoutbreak.menu.Menu;

import java.awt.*;

public final class ControlsMenu extends Menu {

    public ControlsMenu(GameController gameController, InputHandler inputHandler, Color fontColor) {
        super(gameController, inputHandler, fontColor);
    }

    @Override
    public void update() {
        if (inputHandler.isCancelPressed())
            gameController.switchState(State.MAIN_MENU);
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
        g.drawString(textHandler.menuControlsMsg01, xPos, gameController.getHeight() / 2 + yPos);
        g.drawString(textHandler.menuControlsMsg02, gameController.getWidth() / 2 + xPoxInc, gameController.getHeight() / 2 + yPos);

        g.drawString(textHandler.menuControlsMsg03, xPos, gameController.getHeight() / 2 + (yPos + yPosInc));
        g.drawString(textHandler.menuControlsMsg04, gameController.getWidth() / 2 + xPoxInc, gameController.getHeight() / 2 + (yPos + yPosInc));

        g.drawString(textHandler.menuControlsMsg05, xPos, gameController.getHeight() / 2 + (yPos + yPosInc * 2));
        g.drawString(textHandler.menuControlsMsg06, gameController.getWidth() / 2 + xPoxInc, gameController.getHeight() / 2 + (yPos + yPosInc * 2));

        g.drawString(textHandler.menuControlsMsg07, xPos, gameController.getHeight() / 2 + (yPos + yPosInc * 3));
        g.drawString(textHandler.menuControlsMsg08, gameController.getWidth() / 2 + xPoxInc, gameController.getHeight() / 2 + (yPos + yPosInc * 3));

        drawInformationPanel(g);
    }

}