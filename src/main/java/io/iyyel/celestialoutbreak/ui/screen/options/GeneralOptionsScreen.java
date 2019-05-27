package io.iyyel.celestialoutbreak.ui.screen.options;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.handler.FileHandler;
import io.iyyel.celestialoutbreak.handler.LogHandler;
import io.iyyel.celestialoutbreak.ui.screen.AbstractNavigationScreen;
import io.iyyel.celestialoutbreak.ui.screen.component.Button;

import java.awt.*;

public final class GeneralOptionsScreen extends AbstractNavigationScreen {

    private final Button[] buttons;

    private final String[] options = {textHandler.BTN_SOUND_TEXT, textHandler.BTN_GOD_MODE_TEXT,
            textHandler.BTN_FPS_LOCK_TEXT, textHandler.BTN_ANTI_ALIASING_TEXT};

    private final Color[] textOptionColor;

    private final FileHandler fileHandler = FileHandler.getInstance();
    private final LogHandler logHandler = LogHandler.getInstance();

    public GeneralOptionsScreen(NavStyle navStyle, int btnAmount, GameController gameController) {
        super(navStyle, btnAmount, gameController);
        buttons = new Button[btnAmount];
        textOptionColor = new Color[btnAmount];

        for (int i = 0; i < btnAmount; i++) {
            buttons[i] = new Button(new Point(gameController.getWidth() / 2, initialBtnYPos + btnYIncrement * i),
                    new Dimension(240, 50), options[i], inputBtnFont,
                    screenFontColor, menuBtnColor, new Point(120, 0), new Point(0, -6), gameController);
        }
    }

    @Override
    public void update() {
        super.update();
        updateButtonColors();
        updateNavUp();
        updateNavDown();
        updateNavLeft();
        updateNavRight();
        updateNavUse(selectedIndex);
        updateSelectedButtonColor(buttons);
        updateNavCancel(GameController.State.OPTIONS);
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

    @Override
    protected void updateNavUse(int index) {
        if (isButtonUsed(index)) {
            switch (index) {
                case 0:
                    String pValue = String.valueOf(!optionsHandler.isSoundEnabled());
                    fileHandler.writePropertyToFile(textHandler.OPTIONS_CONFIG_FILE_CLIENT_PATH, textHandler.PROP_KEY_SOUND_ENABLED, pValue);
                    optionsHandler.reloadProperty(textHandler.PROP_KEY_SOUND_ENABLED, pValue);
                    soundHandler.playStateSound(gameController.getState(), gameController.getPrevState(), true, true);
                    if (optionsHandler.isVerboseLogEnabled()) {
                        if (optionsHandler.isSoundEnabled()) {
                            logHandler.log("Sound has been enabled.", LogHandler.LogLevel.INFO, false);
                        } else {
                            logHandler.log("Sound has been disabled.", LogHandler.LogLevel.INFO, false);
                        }
                    }
                    break;
                case 1:
                    pValue = String.valueOf(!optionsHandler.isGodModeEnabled());
                    fileHandler.writePropertyToFile(textHandler.OPTIONS_CONFIG_FILE_CLIENT_PATH, textHandler.PROP_KEY_GOD_MODE_ENABLED, pValue);
                    optionsHandler.reloadProperty(textHandler.PROP_KEY_GOD_MODE_ENABLED, pValue);
                    if (optionsHandler.isVerboseLogEnabled()) {
                        if (optionsHandler.isGodModeEnabled()) {
                            logHandler.log("God Mode has been enabled.", LogHandler.LogLevel.INFO, false);
                        } else {
                            logHandler.log("God Mode has been disabled.", LogHandler.LogLevel.INFO, false);
                        }
                    }
                    break;
                case 2:
                    pValue = String.valueOf(!optionsHandler.isFpsLockEnabled());
                    fileHandler.writePropertyToFile(textHandler.OPTIONS_CONFIG_FILE_CLIENT_PATH, textHandler.PROP_KEY_FPS_LOCK_ENABLED, pValue);
                    optionsHandler.reloadProperty(textHandler.PROP_KEY_FPS_LOCK_ENABLED, pValue);
                    if (optionsHandler.isVerboseLogEnabled()) {
                        if (optionsHandler.isFpsLockEnabled()) {
                            logHandler.log("FPS Lock has been enabled.", LogHandler.LogLevel.INFO, false);
                        } else {
                            logHandler.log("FPS Lock has been disabled.", LogHandler.LogLevel.INFO, false);
                        }
                    }
                    break;
                case 3:
                    pValue = String.valueOf(!optionsHandler.isAntiAliasingEnabled());
                    fileHandler.writePropertyToFile(textHandler.OPTIONS_CONFIG_FILE_CLIENT_PATH, textHandler.PROP_KEY_ANTI_ALIASING_ENABLED, pValue);
                    optionsHandler.reloadProperty(textHandler.PROP_KEY_ANTI_ALIASING_ENABLED, pValue);
                    if (optionsHandler.isVerboseLogEnabled()) {
                        if (optionsHandler.isAntiAliasingEnabled()) {
                            logHandler.log("Anti-aliasing has been enabled.", LogHandler.LogLevel.INFO, false);
                        } else {
                            logHandler.log("Anti-aliasing has been disabled.", LogHandler.LogLevel.INFO, false);
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void updateNavOK(int index) {

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