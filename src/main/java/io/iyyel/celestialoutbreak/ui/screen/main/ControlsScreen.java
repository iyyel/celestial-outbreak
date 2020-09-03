package io.iyyel.celestialoutbreak.ui.screen.main;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.ui.screen.AbstractScreen;

import java.awt.*;

public final class ControlsScreen extends AbstractScreen {

    public ControlsScreen(GameController gameController) {
        super(gameController);
    }

    @Override
    public void update() {
        decInputTimer();

        if (inputHandler.isCancelPressed() && isInputAvailable()) {
            resetInputTimer();
            menuNavClip.play(false);
            gameController.switchState(GameController.State.MAIN);
        }
    }

    @Override
    public void render(Graphics2D g) {
        drawTitle(g);

        int xPos = 105;
        int xPoxInc = 200;
        int yPos = -50;
        int yPosInc = 70;

        drawSubtitle(textHandler.TITLE_CONTROLS_SCREEN, g);

        g.setFont(msgFont);

        g.drawString(textHandler.menuControlsMsg01, xPos, getHalfHeight() + yPos);
        g.drawString(textHandler.menuControlsMsg02, getHalfWidth() + xPoxInc, getHalfHeight() + yPos);

        g.drawString(textHandler.menuControlsMsg03, xPos, getHalfHeight() + (yPos + yPosInc));
        g.drawString(textHandler.menuControlsMsg04, getHalfWidth() + xPoxInc, getHalfHeight() + (yPos + yPosInc));

        g.drawString(textHandler.menuControlsMsg05, xPos, getHalfHeight()+ (yPos + yPosInc * 2));
        g.drawString(textHandler.menuControlsMsg06, getHalfWidth() + xPoxInc, getHalfHeight() + (yPos + yPosInc * 2));

        g.drawString(textHandler.menuControlsMsg07, xPos, getHalfHeight() + (yPos + yPosInc * 3));
        g.drawString(textHandler.menuControlsMsg08, getHalfWidth() + xPoxInc, getHalfHeight() + (yPos + yPosInc * 3));

        g.drawString(textHandler.menuControlsMsg09, xPos, getHalfHeight() + (yPos + yPosInc * 4));
        g.drawString(textHandler.menuControlsMsg10, getHalfWidth() + xPoxInc, getHalfHeight()+ (yPos + yPosInc * 4));

        drawInfoPanel(g);
    }

}