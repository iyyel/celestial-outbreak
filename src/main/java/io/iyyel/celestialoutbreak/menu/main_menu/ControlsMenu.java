package io.iyyel.celestialoutbreak.menu.main_menu;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.menu.AbstractMenu;

import java.awt.*;

public final class ControlsMenu extends AbstractMenu {

    public ControlsMenu(GameController gameController) {
        super(gameController);
    }

    @Override
    public void update() {
        decInputTimer();

        if (inputHandler.isCancelPressed() && isInputAvailable()) {
            resetInputTimer();
            menuUseClip.play(false);
            gameController.switchState(GameController.State.MAIN_MENU);
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(menuFontColor);
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