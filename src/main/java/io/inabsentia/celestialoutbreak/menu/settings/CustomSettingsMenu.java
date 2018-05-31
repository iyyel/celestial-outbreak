package io.inabsentia.celestialoutbreak.menu.settings;

import io.inabsentia.celestialoutbreak.controller.GameController;
import io.inabsentia.celestialoutbreak.handler.InputHandler;
import io.inabsentia.celestialoutbreak.menu.Menu;

import java.awt.*;

public final class CustomSettingsMenu extends Menu {

    public CustomSettingsMenu(GameController gameController, InputHandler inputHandler, Color fontColor) {
        super(gameController, inputHandler, fontColor);
    }

    @Override
    public void update() {
        if (inputHandler.isCancelPressed())
            gameController.switchState(GameController.State.SETTINGS_MENU);
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(fontColor);
        drawMenuTitle(g);

        drawSubmenuTitle("Customization Settings", g);
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