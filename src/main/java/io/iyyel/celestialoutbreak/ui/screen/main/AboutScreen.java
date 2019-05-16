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
            menuUseClip.play(false);
            gameController.switchState(State.MAIN);
        }
    }

    int xPos = 80;
    int yPos = 260;
    int yPosInc = 35;
    int yPosSepInc = 70;

    @Override
    public void render(Graphics2D g) {
        g.setColor(screenFontColor);
        drawScreenTitle(g);

        drawScreenSubtitle(textHandler.TITLE_ABOUT_SCREEN, g);

        g.setFont(msgFont);

        g.drawString(textHandler.aboutMenuMsg01, xPos, yPos);
        g.drawString(textHandler.aboutMenuMsg02, xPos, yPos + yPosInc);

        g.drawString(textHandler.aboutMenuMsg03, xPos, yPos + yPosSepInc + yPosInc);
        g.drawString(textHandler.aboutMenuMsg04, xPos, yPos + yPosSepInc + yPosInc * 2);
        g.drawString(textHandler.aboutMenuMsg05, xPos, yPos + yPosSepInc + yPosInc * 3);

        g.drawString(textHandler.aboutMenuMsg06, xPos, yPos + yPosSepInc * 2 + yPosInc * 3);
        g.drawString(textHandler.aboutMenuMsg07, xPos, yPos + yPosSepInc * 2 + yPosInc * 4);

        g.drawString(textHandler.aboutMenuMsg08, xPos, yPos + yPosSepInc * 3 + yPosInc * 4);
        g.drawString(textHandler.aboutMenuMsg09, xPos, yPos + yPosSepInc * 3 + yPosInc * 5);

        drawScreenInfoPanel(g);
    }

}