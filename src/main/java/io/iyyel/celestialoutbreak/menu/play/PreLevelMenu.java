package io.iyyel.celestialoutbreak.menu.play;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.controller.GameController.State;
import io.iyyel.celestialoutbreak.handler.LevelHandler;
import io.iyyel.celestialoutbreak.level.Level;
import io.iyyel.celestialoutbreak.menu.AbstractMenu;

import java.awt.*;

public final class PreLevelMenu extends AbstractMenu {

    private final LevelHandler levelHandler = LevelHandler.getInstance();

    public PreLevelMenu(GameController gameController) {
        super(gameController);
    }

    @Override
    public void update() {
        decInputTimer();

        if (inputHandler.isOKPressed() && isInputAvailable()) {
            resetInputTimer();
            soundHandler.stopAllSound();
            levelHandler.getActiveLevel().playSound();
            utils.startTimer();
            gameController.switchState(State.PLAY);
        }

    }

    @Override
    public void render(Graphics2D g) {
        Level activeLevel = levelHandler.getActiveLevel();

        g.setColor(menuFontColor);
        drawCenterString(textHandler.GAME_TITLE, 100, g, titleFont);

        drawSubmenuTitle(activeLevel.getName(), g);
        drawCenterString(activeLevel.getDesc(), gameController.getHeight() / 2, g, msgFont);

        drawMenuToolTip("Press '" + textHandler.BTN_CONTROL_FORWARD_OK + "' to start.", g);

        drawInfoPanel(g);
    }

}