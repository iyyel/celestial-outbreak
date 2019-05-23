package io.iyyel.celestialoutbreak.ui.screen.options;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.ui.screen.AbstractNavigationScreen;
import io.iyyel.celestialoutbreak.ui.screen.component.Button;

import java.awt.*;


public final class GeneralOptionsScreen extends AbstractNavigationScreen {

    private final Button[] buttons;

    private final String[] options = {"Sound", "God Mode", "FPS Lock", "Anti-aliasing"};

    private final Color[] textOptionColor;

    public GeneralOptionsScreen(NavStyle navStyle, int btnAmount, GameController gameController) {
        super(navStyle, btnAmount, gameController);
        buttons = new Button[btnAmount];
        textOptionColor = new Color[btnAmount];

        for (int i = 0; i < btnAmount; i++) {
            buttons[i] = new Button(new Point(gameController.getWidth() / 2 , initialBtnYPos + btnYIncrement * i),
                    new Dimension(240, 50), options[i], inputBtnFont,
                    screenFontColor, menuBtnColor, new Point(120, 0), new Point(0, -6), gameController);
        }
    }

    @Override
    protected void triggerButton(int index) {

    }

    @Override
    protected void navigateForward(GameController.State state) {
        super.navigateForward(state);
    }

    @Override
    protected void navigateBackward(GameController.State state) {
        super.navigateBackward(state);
    }

    @Override
    public void update() {
        decInputTimer();
        updateButtonColors();
        updateNavigation();
        updateSelectedButtonColor(buttons);
        navigateBackward(GameController.State.OPTIONS);
    }

    @Override
    public void render(Graphics2D g) {
        drawScreenTitles(textHandler.TITLE_GENERAL_OPTIONS_SCREEN, g);

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setFontColor(textOptionColor[i]);
            buttons[i].render(g);
        }

        drawScreenToolTip("Press '" + textHandler.BTN_CONTROL_USE + "' to toggle an option or '" + textHandler.BTN_CONTROL_BACK_CANCEL + "' to go back.", g);
        drawScreenInfoPanel(g);
    }

    private void updateButtonColors() {
        if (optionsHandler.isSoundEnabled()) {
            textOptionColor[0] = menuBtnPlayerSelectedColor;
        } else {
            textOptionColor[0] = menuBtnPlayerDeletedColor;
        }

        if (optionsHandler.isGodModeEnabled()) {
            textOptionColor[1] = menuBtnPlayerSelectedColor;
        } else {
            textOptionColor[1] = menuBtnPlayerDeletedColor;
        }

        if (optionsHandler.isFpsLockEnabled()) {
            textOptionColor[2] = menuBtnPlayerSelectedColor;
        } else {
            textOptionColor[2] = menuBtnPlayerDeletedColor;
        }

        if (optionsHandler.isAntiAliasingEnabled()) {
            textOptionColor[3] = menuBtnPlayerSelectedColor;
        } else {
            textOptionColor[3] = menuBtnPlayerDeletedColor;
        }
    }

}