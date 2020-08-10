package io.iyyel.celestialoutbreak.ui.screen.options;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.ui.screen.AbstractNavigationScreen;
import io.iyyel.celestialoutbreak.ui.screen.component.Button;

import java.awt.*;


public final class PlayerOptionsScreen extends AbstractNavigationScreen {

    private final Button[] buttons;

    private final String[] options = {textHandler.BTN_SELECT_PLAYER_TEXT, textHandler.BTN_CREATE_PLAYER_TEXT, textHandler.BTN_DELETE_DELETE_TEXT};

    public PlayerOptionsScreen(NavStyle navStyle, int btnAmount, GameController gameController) {
        super(navStyle, btnAmount, gameController);
        buttons = new Button[btnAmount];

        for (int i = 0; i < btnAmount; i++) {
            buttons[i] = new Button(new Point(gameController.getWidth() / 2, initialBtnYPos + btnYIncrement * i),
                    new Dimension(270, 50), options[i], true, inputBtnFont,
                    screenFontColor, menuBtnColor, new Point(135, 0), new Point(0, -6), screenWidth, screenHeight);
        }
    }

    @Override
    protected void updateNavUse(int index) {

    }

    @Override
    protected void updateNavOK(int index) {
        if (isButtonOK(index)) {
            switch (index) {
                case 0:
                    gameController.switchState(GameController.State.PLAYER_SELECT);
                    break;
                case 1:
                    gameController.switchState(GameController.State.PLAYER_CREATE);
                    break;
                case 2:
                    gameController.switchState(GameController.State.PLAYER_DELETE);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void update() {
        super.update();
        updateNavUp();
        updateNavDown();
        updateNavLeft();
        updateNavRight();
        updateNavOK(selectedIndex);
        updateSelectedButtonColor(buttons);
        updateNavCancel(GameController.State.OPTIONS);
    }

    @Override
    public void render(Graphics2D g) {
        drawScreenTitles(textHandler.TITLE_PLAYER_OPTIONS_SCREEN, g);
        renderButtons(buttons, g);
        drawInfoPanel(g);
    }

}