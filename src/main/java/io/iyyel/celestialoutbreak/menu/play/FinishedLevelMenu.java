package io.iyyel.celestialoutbreak.menu.play;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.controller.GameController.State;
import io.iyyel.celestialoutbreak.menu.AbstractMenu;

import java.awt.*;

public final class FinishedLevelMenu extends AbstractMenu {

    private String prevLevelName, nextLevelName;

    public FinishedLevelMenu(GameController gameController) {
        super(gameController);
    }

    @Override
    public void update() {
        decInputTimer();

        if (inputHandler.isOKPressed() && isInputAvailable()) {
            resetInputTimer();
            gameController.switchState(State.NEW_LEVEL_SCREEN);
        }

        if (inputHandler.isCancelPressed() && isInputAvailable()) {
            resetInputTimer();
            menuUseClip.play(false);
            gameController.switchState(State.MAIN_MENU);
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(menuFontColor);
        drawMenuTitle(g);
        drawCenterString(prevLevelName + " has been obliterated", 300, g, msgFont);
        drawCenterString(nextLevelName + " is the next awaiting challenge", 350, g, msgFont);
        drawCenterString("Are you prepared?", 450, g, msgFont);
        drawInformationPanel(g);
    }

    public void updateLevelNames(String prevLevelName, String nextLevelName) {
        this.prevLevelName = prevLevelName;
        this.nextLevelName = nextLevelName;
    }

}