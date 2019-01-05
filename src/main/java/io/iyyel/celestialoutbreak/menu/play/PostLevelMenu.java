package io.iyyel.celestialoutbreak.menu.play;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.controller.GameController.State;
import io.iyyel.celestialoutbreak.handler.LevelHandler;
import io.iyyel.celestialoutbreak.level.Level;
import io.iyyel.celestialoutbreak.menu.AbstractMenu;

import java.awt.*;

public final class PostLevelMenu extends AbstractMenu {

    private final LevelHandler levelHandler = LevelHandler.getInstance();

    private boolean isFirstUpdate = true;

    public PostLevelMenu(GameController gameController) {
        super(gameController);
    }

    @Override
    public void update() {
        decInputTimer();

        if (isFirstUpdate) {
            isFirstUpdate = false;
            levelHandler.resetActiveLevel();
        }

        if (inputHandler.isOKPressed() && isInputAvailable()) {
            resetInputTimer();
            isFirstUpdate= true;
            gameController.switchState(State.MAIN);
        }
    }

    @Override
    public void render(Graphics2D g) {
        Level activeLevel = levelHandler.getActiveLevel();

        g.setColor(menuFontColor);
        drawCenterString(textHandler.GAME_TITLE, 100, g, titleFont);

        drawSubmenuTitle(activeLevel.getName(), g);


        if (levelHandler.getActiveLevel().isWon()) {
            drawCenterString("You are victorious! The " + levelHandler.getActiveLevel().getName() + " level has been beaten.", gameController.getHeight() / 2, g, msgFont);
            drawCenterString("You reached a score of 1234.", gameController.getHeight() / 2 + 40, g, msgFont);
        } else {
            drawCenterString("You have lost. The " + levelHandler.getActiveLevel().getName() + " level shines in grace upon you.", gameController.getHeight() / 2, g, msgFont);
            drawCenterString("You reached a score of 1234.", gameController.getHeight() / 2 + 40, g, msgFont);
        }

        drawMenuToolTip("Press '" + textHandler.BTN_CONTROL_FORWARD_OK + "' for the main menu.", g);

        drawInfoPanel(g);
    }

}