package io.iyyel.celestialoutbreak.ui.screen.main;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.ui.screen.AbstractNavigationScreen;
import io.iyyel.celestialoutbreak.ui.screen.component.Button;

import java.awt.*;

public final class OptionsScreen extends AbstractNavigationScreen {

    private String[] options = {textHandler.BTN_PLAYER_OPTIONS_TEXT, textHandler.BTN_GENERAL_OPTIONS_TEXT,
            textHandler.BTN_CONFIGURATION_OPTIONS_TEXT};

    private final Button[] buttons;

    public OptionsScreen(NavStyle navStyle, int btnAmount, GameController gameController) {
        super(navStyle, btnAmount, gameController);
        buttons = new Button[btnAmount];

        for (int i = 0; i < btnAmount; i++) {
            buttons[i] = new Button(new Point(gameController.getWidth() / 2, initialBtnYPos + btnYIncrement * i),
                    new Dimension(390, 50), options[i], inputBtnFont,
                    screenFontColor, menuBtnColor, new Point(195, 0), new Point(0, -6), gameController);
        }
    }

    @Override
    protected void updateButtonUse(int index) {
        if (isButtonUsed(index)) {
            switch (index) {
                case 0:
                    // Player options
                    gameController.switchState(GameController.State.PLAYER_OPTIONS);
                    break;
                case 1:
                    // Game options
                    gameController.switchState(GameController.State.GENERAL_OPTIONS);
                    break;
                case 2:
                    // Configuration options
                    gameController.switchState(GameController.State.CONFIG_OPTIONS);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void update() {
        super.update();
        updateNavCancel(GameController.State.MAIN);
        updateNavUp();
        updateNavDown();
        updateButtonUse(selected);
        updateSelectedButtonColor(buttons);
    }

    @Override
    public void render(Graphics2D g) {
        drawScreenTitles(textHandler.TITLE_OPTIONS_SCREEN, g);
        renderButtons(buttons, g);
        drawScreenInfoPanel(g);
    }

}