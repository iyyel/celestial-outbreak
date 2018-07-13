package io.iyyel.celestialoutbreak.menu.options_menu;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.menu.AbstractMenu;

import java.awt.*;

public final class ConfigOptionsMenu extends AbstractMenu {

    public ConfigOptionsMenu(GameController gameController) {
        super(gameController);
    }

    @Override
    public void update() {
        decInputTimer();

        if (inputHandler.isCancelPressed() && isInputAvailable()) {
            resetInputTimer();
            menuUseClip.play(false);
            gameController.switchState(GameController.State.OPTIONS_MENU);
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(menuFontColor);
        drawMenuTitle(g);

        drawSubmenuTitle(textHandler.TITLE_CONFIGURATION_OPTIONS_MENU, g);
        g.setFont(msgFont);

        int xPos = 80;
        int yPos = 260;
        int yPosInc = 35;
        int yPosSepInc = 70;

        g.drawString(textHandler.menuSettingsMsg01, xPos, yPos);
        g.drawString(textHandler.menuSettingsMsg02, xPos, yPos + yPosInc);

        g.drawString(textHandler.menuSettingsMsg03, xPos, yPos + yPosSepInc + yPosInc);
        g.drawString(textHandler.menuSettingsMsg04, xPos, yPos + yPosSepInc + yPosInc * 2);

        g.drawString(textHandler.menuSettingsMsg05, xPos, yPos + yPosSepInc * 2 + yPosInc * 2);
        g.drawString(textHandler.menuSettingsMsg06, xPos, yPos + yPosSepInc * 2 + yPosInc * 3);

        g.drawString(textHandler.menuSettingsMsg07, xPos, yPos + yPosSepInc * 3 + yPosInc * 3);
        g.drawString(textHandler.menuSettingsMsg08, xPos, yPos + yPosSepInc * 3 + yPosInc * 4);
        g.drawString(textHandler.menuSettingsMsg09, xPos, yPos + yPosSepInc * 3 + yPosInc * 5);

        drawInformationPanel(g);
    }

}