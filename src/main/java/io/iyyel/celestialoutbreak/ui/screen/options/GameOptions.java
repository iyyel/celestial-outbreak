package io.iyyel.celestialoutbreak.ui.screen.options;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.handler.FileHandler;
import io.iyyel.celestialoutbreak.handler.LogHandler;
import io.iyyel.celestialoutbreak.handler.SoundHandler;
import io.iyyel.celestialoutbreak.ui.screen.AbstractNavigationScreen;
import io.iyyel.celestialoutbreak.ui.screen.component.Button;

import java.awt.*;

public final class GameOptions extends AbstractNavigationScreen {

    private final Button[] buttons;

    private final String[] options = {textHandler.BTN_SOUND_TEXT, textHandler.BTN_GOD_MODE_TEXT};

    private final Color[] textOptionColor;

    private final FileHandler fileHandler = FileHandler.getInstance();
    private final LogHandler logHandler = LogHandler.getInstance();

    public GameOptions(NavStyle navStyle, int btnAmount, GameController gameController) {
        super(navStyle, btnAmount, gameController);
        buttons = new Button[btnAmount];
        textOptionColor = new Color[btnAmount];

        for (int i = 0; i < btnAmount; i++) {
            buttons[i] = new Button(new Point(getHalfWidth(), initialBtnYPos + btnYIncrement * i),
                    new Dimension(240, 50), options[i], true, inputBtnFont,
                    screenFontColor, menuBtnColor, new Point(120, 0), new Point(0, -8), screenWidth, screenHeight);
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
        updateNavAux(selectedIndex);
        updateSelectedButtonColor(buttons);
        updateNavCancel(GameController.State.OPTIONS);
    }

    @Override
    public void render(Graphics2D g) {
        drawScreenTitles(textHandler.TITLE_GAME_OPTIONS_SCREEN, g);

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setFgColor(textOptionColor[i]);
            buttons[i].render(g);
        }

        drawInfoPanel(g);
    }

    @Override
    protected void updateNavAux(int index) {
        if (isAuxPressed(index)) {
            menuAuxClip.play(false);
            switch (index) {
                case 0:
                    String pValue = String.valueOf(!optionsHandler.isSoundEnabled());
                    fileHandler.writePropertyToFile(textHandler.OPTIONS_CONFIG_FILE_CLIENT_PATH, textHandler.PROP_KEY_SOUND_ENABLED, pValue);
                    optionsHandler.reloadProperty(textHandler.PROP_KEY_SOUND_ENABLED, pValue);
                    soundHandler.playStateSound(gameController.getState(), gameController.getPrevState(), true, true);
                    if (optionsHandler.isVerboseLogEnabled()) {
                        if (optionsHandler.isSoundEnabled()) {
                            logHandler.log("Sound has been enabled.", "updateNavUse", LogHandler.LogLevel.INFO, false);
                        } else {
                            logHandler.log("Sound has been disabled.", "updateNavUse", LogHandler.LogLevel.INFO, false);
                        }
                    }
                    break;
                case 1:
                    pValue = String.valueOf(!optionsHandler.isGodModeEnabled());
                    fileHandler.writePropertyToFile(textHandler.OPTIONS_CONFIG_FILE_CLIENT_PATH, textHandler.PROP_KEY_GOD_MODE_ENABLED, pValue);
                    optionsHandler.reloadProperty(textHandler.PROP_KEY_GOD_MODE_ENABLED, pValue);
                    if (optionsHandler.isVerboseLogEnabled()) {
                        if (optionsHandler.isGodModeEnabled()) {
                            logHandler.log("God Mode has been enabled.", "updateNavUse", LogHandler.LogLevel.INFO, false);
                        } else {
                            logHandler.log("God Mode has been disabled.", "updateNavUse", LogHandler.LogLevel.INFO, false);
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
    }

}