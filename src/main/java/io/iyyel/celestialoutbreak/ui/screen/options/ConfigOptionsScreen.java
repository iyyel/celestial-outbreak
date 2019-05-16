package io.iyyel.celestialoutbreak.ui.screen.options;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.ui.screen.AbstractScreen;

import java.awt.*;

public final class ConfigOptionsScreen extends AbstractScreen {

    public ConfigOptionsScreen(GameController gameController) {
        super(gameController);
    }

    @Override
    public void update() {
        decInputTimer();

        if (inputHandler.isCancelPressed() && isInputAvailable()) {
            resetInputTimer();
            menuUseClip.play(false);
            gameController.switchState(GameController.State.OPTIONS);
        }
    }

    @Override
    public void render(Graphics2D g) {
        drawScreenTitle(g);
        drawScreenSubtitle(textHandler.TITLE_CONFIGURATION_OPTIONS_SCREEN, g);

        int xPos = 80;
        int yPos = 260;
        int yPosInc = 35;
        int yPosSepInc = 70;

        g.setFont(msgFont);

        g.drawString(textHandler.menuConfigMsg01, xPos, yPos);
        g.drawString(textHandler.menuConfigMsg02, xPos, yPos + yPosInc);

        g.drawString(textHandler.menuConfigMsg03, xPos, yPos + yPosSepInc + yPosInc);
        g.drawString(textHandler.menuConfigMsg04, xPos, yPos + yPosSepInc + yPosInc * 2);

        g.drawString(textHandler.menuConfigMsg05, xPos, yPos + yPosSepInc * 2 + yPosInc * 2);
        g.drawString(textHandler.menuConfigMsg06, xPos, yPos + yPosSepInc * 2 + yPosInc * 3);
        g.drawString(textHandler.menuConfigMsg08, xPos, yPos + yPosSepInc * 2 + yPosInc * 4);

        g.drawString(textHandler.menuConfigMsg09, xPos, yPos + yPosSepInc * 3 + yPosInc * 4);
        g.drawString(textHandler.menuConfigMsg10, xPos, yPos + yPosSepInc * 3 + yPosInc * 5);

        drawScreenInfoPanel(g);
    }

}