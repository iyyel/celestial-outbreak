package io.iyyel.celestialoutbreak.ui.screen.main;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.controller.GameController.State;
import io.iyyel.celestialoutbreak.ui.screen.AbstractScreen;

import java.awt.*;

public final class AboutScreen extends AbstractScreen {

    public AboutScreen(GameController gameController) {
        super(gameController);
    }

    @Override
    public void update() {
        decInputTimer();

        if (inputHandler.isCancelPressed() && isInputAvailable()) {
            resetInputTimer();
            menuNavClip.play(false);
            gameController.switchState(State.MAIN);
        }
    }

    @Override
    public void render(Graphics2D g) {
        int xPos = 100;
        int yPos = 250;
        int yPosInc = 35;
        int yPosSepInc = 55;

        g.setColor(screenFontColor);
        drawTitle(g);

        drawSubtitle(textHandler.TITLE_ABOUT_SCREEN, g);

        g.setFont(msgFont);

        g.drawString(textHandler.aboutMenuMsg01, xPos, yPos);
        g.drawString(textHandler.aboutMenuMsg02, xPos, yPos + yPosInc);
        g.drawString(textHandler.aboutMenuMsg03, xPos, yPos + yPosInc * 2);

        g.drawString(textHandler.aboutMenuMsg04, xPos, yPos + yPosSepInc + yPosInc * 2);
        g.drawString(textHandler.aboutMenuMsg05, xPos, yPos + yPosSepInc + yPosInc * 3);
        g.drawString(textHandler.aboutMenuMsg06, xPos, yPos + yPosSepInc + yPosInc * 4);
        g.drawString(textHandler.aboutMenuMsg07, xPos, yPos + yPosSepInc + yPosInc * 5);

        g.drawString(textHandler.aboutMenuMsg08, xPos, yPos + yPosSepInc * 2 + yPosInc * 5);
        g.drawString(textHandler.aboutMenuMsg09, xPos, yPos + yPosSepInc * 2 + yPosInc * 6);

        g.drawString(textHandler.aboutMenuMsg10, xPos, yPos + yPosSepInc * 3 + yPosInc * 6);
        g.drawString(textHandler.aboutMenuMsg11, xPos, yPos + yPosSepInc * 3 + yPosInc * 7);

        drawInfoPanel(g);
    }

}