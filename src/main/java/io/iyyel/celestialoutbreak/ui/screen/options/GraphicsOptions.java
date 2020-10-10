package io.iyyel.celestialoutbreak.ui.screen.options;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.handler.FileHandler;
import io.iyyel.celestialoutbreak.handler.LogHandler;
import io.iyyel.celestialoutbreak.ui.screen.AbstractNavigationScreen;
import io.iyyel.celestialoutbreak.ui.screen.component.Button;

import java.awt.*;

public final class GraphicsOptions extends AbstractNavigationScreen {

    private final Button[] buttons;

    private final String[] options = {textHandler.BTN_FPS_LOCK_TEXT, textHandler.BTN_ANTI_ALIASING_TEXT};

    private final Color[] textOptionColor;

    private final FileHandler fileHandler = FileHandler.getInstance();
    private final LogHandler logHandler = LogHandler.getInstance();

    public GraphicsOptions(NavStyle navStyle, int btnAmount, GameController gameController) {
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
        drawScreenTitles(textHandler.TITLE_GRAPHICS_OPTIONS_SCREEN, g);

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
                    String pValue = String.valueOf(!optionsHandler.isFpsLockEnabled());
                    fileHandler.writePropertyToFile(textHandler.OPTIONS_CONFIG_FILE_CLIENT_PATH, textHandler.PROP_KEY_FPS_LOCK_ENABLED, pValue);
                    optionsHandler.reloadProperty(textHandler.PROP_KEY_FPS_LOCK_ENABLED, pValue);
                    if (optionsHandler.isFpsLockEnabled()) {
                        logHandler.log("FPS Lock has been enabled.", "updateNavUse", LogHandler.LogLevel.INFO, true);
                    } else {
                        logHandler.log("FPS Lock has been disabled.", "updateNavUse", LogHandler.LogLevel.INFO, true);
                    }
                    break;
                case 1:
                    pValue = String.valueOf(!optionsHandler.isAntiAliasingEnabled());
                    fileHandler.writePropertyToFile(textHandler.OPTIONS_CONFIG_FILE_CLIENT_PATH, textHandler.PROP_KEY_ANTI_ALIASING_ENABLED, pValue);
                    optionsHandler.reloadProperty(textHandler.PROP_KEY_ANTI_ALIASING_ENABLED, pValue);
                    if (optionsHandler.isAntiAliasingEnabled()) {
                        logHandler.log("Anti-aliasing has been enabled.", "updateNavUse", LogHandler.LogLevel.INFO, true);
                    } else {
                        logHandler.log("Anti-aliasing has been disabled.", "updateNavUse", LogHandler.LogLevel.INFO, true);
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
        if (optionsHandler.isFpsLockEnabled()) {
            textOptionColor[0] = menuBtnPlayerSelectedColor;
        } else {
            textOptionColor[0] = menuBtnPlayerDeletedColor;
        }

        if (optionsHandler.isAntiAliasingEnabled()) {
            textOptionColor[1] = menuBtnPlayerSelectedColor;
        } else {
            textOptionColor[1] = menuBtnPlayerDeletedColor;
        }
    }

}