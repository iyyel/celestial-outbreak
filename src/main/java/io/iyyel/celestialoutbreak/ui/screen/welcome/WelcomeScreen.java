package io.iyyel.celestialoutbreak.ui.screen.welcome;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.ui.screen.AbstractNavigationScreen;
import io.iyyel.celestialoutbreak.ui.screen.component.Button;

import java.awt.*;

public final class WelcomeScreen extends AbstractNavigationScreen {

    private final Button[] buttons;
    private final String[] options = {textHandler.BTN_START_TEXT, textHandler.BTN_EXIT_TEXT};
    private final int Y_MULTIPLIER = 3;

    public WelcomeScreen(NavStyle navStyle, int btnAmount, GameController gameController) {
        super(navStyle, btnAmount, gameController);
        buttons = new io.iyyel.celestialoutbreak.ui.screen.component.Button[btnAmount];

        for (int i = 0; i < btnAmount; i++) {
            buttons[i] = new Button(new Point(getHalfWidth(), initialBtnYPos + btnYIncrement * (i + Y_MULTIPLIER)),
                    new Dimension(160, 50), options[i], true, inputBtnFont,
                    screenFontColor, menuBtnColor, new Point(80, 0), new Point(0, -6), gameController);
        }
    }

    @Override
    public void update() {
        super.update();
        updateNavUp();
        updateNavDown();
        updateNavOK(selectedIndex);
        updateSelectedButtonColor(buttons);
        updateNavCancel(GameController.State.OPTIONS);
    }

    @Override
    protected void updateNavUse(int index) {

    }

    @Override
    protected void updateNavOK(int index) {
        if (isButtonOK(index)) {
            super.updateNavOK();
            switch (index) {
                case 0:
                    doProceed();
                    break;
                case 1:
                    gameController.switchState(GameController.State.EXIT);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        drawScreenTitles(textHandler.TITLE_WELCOME_SCREEN, g);
        drawScreenMessage(textHandler.WELCOME_SCREEN_TEXT, g);
        renderButtons(buttons, g);
        drawScreenToolTip(textHandler.WELCOME_SCREEN_TOOLTIP_TEXT, g);
        drawScreenInfoPanel(g);
    }

    private void doProceed() {
        if (playerDAO.getPlayers().isEmpty()) {
            gameController.switchState(GameController.State.PLAYER_CREATE);
        } else {
            gameController.switchState(GameController.State.MAIN);
        }
    }

}