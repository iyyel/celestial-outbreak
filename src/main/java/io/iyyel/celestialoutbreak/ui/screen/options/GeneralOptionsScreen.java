package io.iyyel.celestialoutbreak.ui.screen.options;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.handler.PropertyHandler;
import io.iyyel.celestialoutbreak.handler.LogHandler;
import io.iyyel.celestialoutbreak.ui.screen.AbstractScreen;
import io.iyyel.celestialoutbreak.ui.screen.component.Button;

import java.awt.*;

public final class GeneralOptionsScreen extends AbstractScreen {

    private final Button isSoundEnabledRect, isGodModeEnabledRect, isFpsLockedEnabledRect, isAntiAliasingEnabledRect;
    private String[] options = {"Sound", "God Mode", "FPS Lock", "Anti-aliasing"};

    private Color[] rectColors;
    private Color[] playerNameColors;
    private int selected = 0;

    private final PropertyHandler propertyHandler = PropertyHandler.getInstance();
    private final LogHandler logHandler = LogHandler.getInstance();

    public GeneralOptionsScreen(GameController gameController) {
        super(gameController);


        int initialBtnYPos = 240;
        int btnYIncrement = 80;

        rectColors = new Color[options.length];
        playerNameColors = new Color[options.length];

        for (int i = 0; i < rectColors.length; i++) {
            rectColors[i] = menuBtnColor;
        }

        /* Option buttons */
        isSoundEnabledRect = new Button(new Point(gameController.getWidth() / 2, initialBtnYPos),
                new Dimension(240, 50), options[0], inputBtnFont,
                screenFontColor, rectColors[0], new Point(120, 0), new Point(0, -6), gameController);

        isGodModeEnabledRect = new Button(new Point(gameController.getWidth() / 2, initialBtnYPos + btnYIncrement),
                new Dimension(240, 50), options[1], inputBtnFont,
                screenFontColor, rectColors[1], new Point(120, 0), new Point(0, -6), gameController);

        isFpsLockedEnabledRect = new Button(new Point(gameController.getWidth() / 2, initialBtnYPos + btnYIncrement * 2),
                new Dimension(240, 50), options[2], inputBtnFont,
                screenFontColor, rectColors[2], new Point(120, 0), new Point(0, -6), gameController);

        isAntiAliasingEnabledRect = new Button(new Point(gameController.getWidth() / 2, initialBtnYPos + btnYIncrement * 3),
                new Dimension(240, 50), options[3], inputBtnFont,
                screenFontColor, rectColors[3], new Point(120, 0), new Point(0, -6), gameController);
    }

    @Override
    public void update() {
        decInputTimer();

        updateButtonColors();

        if (inputHandler.isCancelPressed() && isInputAvailable()) {
            resetInputTimer();
            selected = 0;
            menuUseClip.play(false);
            gameController.switchState(GameController.State.OPTIONS);
        }

        if (inputHandler.isDownPressed() && selected < options.length - 1 && isInputAvailable()) {
            resetInputTimer();
            selected++;
            menuNavClip.play(false);
        }

        if (inputHandler.isUpPressed() && selected > 0 && isInputAvailable()) {
            resetInputTimer();
            selected--;
            menuNavClip.play(false);
        }

        for (int i = 0, n = options.length; i < n; i++) {
            if (selected == i) {
                rectColors[i] = menuSelectedBtnColor;

                if (inputHandler.isUsePressed() && isInputAvailable()) {
                    menuUseClip.play(false);
                    resetInputTimer();

                    switch (i) {
                        case 0:
                            String pValue = String.valueOf(!optionsHandler.isSoundEnabled());
                            propertyHandler.writePropertyToFile(textHandler.OPTIONS_CONFIG_FILE_CLIENT_PATH, textHandler.PROP_KEY_SOUND_ENABLED, pValue);
                            optionsHandler.reloadProperty(textHandler.PROP_KEY_SOUND_ENABLED, pValue);
                            soundHandler.playStateSound(gameController.getState(), gameController.getPrevState(), true, true);

                            if (optionsHandler.isSoundEnabled()) {
                                logHandler.log("Sound has been enabled.", LogHandler.LogLevel.INFORMATION, true);
                            } else {
                                logHandler.log("Sound has been disabled.", LogHandler.LogLevel.INFORMATION, true);
                            }

                            break;
                        case 1:
                            pValue = String.valueOf(!optionsHandler.isGodModeEnabled());
                            propertyHandler.writePropertyToFile(textHandler.OPTIONS_CONFIG_FILE_CLIENT_PATH, textHandler.PROP_KEY_GOD_MODE_ENABLED, pValue);
                            optionsHandler.reloadProperty(textHandler.PROP_KEY_GOD_MODE_ENABLED, pValue);

                            if (optionsHandler.isGodModeEnabled()) {
                                logHandler.log("God Mode has been enabled.", LogHandler.LogLevel.INFORMATION, true);
                            } else {
                                logHandler.log("God Mode has been disabled.", LogHandler.LogLevel.INFORMATION, true);
                            }

                            break;
                        case 2:
                            pValue = String.valueOf(!optionsHandler.isFpsLockEnabled());
                            propertyHandler.writePropertyToFile(textHandler.OPTIONS_CONFIG_FILE_CLIENT_PATH, textHandler.PROP_KEY_FPS_LOCK_ENABLED, pValue);
                            optionsHandler.reloadProperty(textHandler.PROP_KEY_FPS_LOCK_ENABLED, pValue);

                            if (optionsHandler.isFpsLockEnabled()) {
                                logHandler.log("FPS Lock has been enabled.", LogHandler.LogLevel.INFORMATION, true);
                            } else {
                                logHandler.log("FPS Lock has been disabled.", LogHandler.LogLevel.INFORMATION, true);
                            }

                            break;
                        case 3:
                            pValue = String.valueOf(!optionsHandler.isAntiAliasingEnabled());
                            propertyHandler.writePropertyToFile(textHandler.OPTIONS_CONFIG_FILE_CLIENT_PATH, textHandler.PROP_KEY_ANTI_ALIASING_ENABLED, pValue);
                            optionsHandler.reloadProperty(textHandler.PROP_KEY_ANTI_ALIASING_ENABLED, pValue);

                            if (optionsHandler.isAntiAliasingEnabled()) {
                                logHandler.log("Anti-aliasing has been enabled.", LogHandler.LogLevel.INFORMATION, true);
                            } else {
                                logHandler.log("Anti-aliasing has been disabled.", LogHandler.LogLevel.INFORMATION, true);
                            }

                            break;
                        default:
                            break;
                    }
                }
            } else {
                rectColors[i] = menuBtnColor;
            }
        }

    }

    @Override
    public void render(Graphics2D g) {
        /* Render game title */
        drawScreenTitle(g);
        drawScreenSubtitle(textHandler.TITLE_GENERAL_OPTIONS_SCREEN, g);

        isSoundEnabledRect.setColor(rectColors[0]);
        isSoundEnabledRect.setFontColor(playerNameColors[0]);
        isSoundEnabledRect.render(g);

        isGodModeEnabledRect.setColor(rectColors[1]);
        isGodModeEnabledRect.setFontColor(playerNameColors[1]);
        isGodModeEnabledRect.render(g);

        isFpsLockedEnabledRect.setColor(rectColors[2]);
        isFpsLockedEnabledRect.setFontColor(playerNameColors[2]);
        isFpsLockedEnabledRect.render(g);

        isAntiAliasingEnabledRect.setColor(rectColors[3]);
        isAntiAliasingEnabledRect.setFontColor(playerNameColors[3]);
        isAntiAliasingEnabledRect.render(g);

        drawScreenToolTip("Press '" + textHandler.BTN_CONTROL_USE + "' to toggle an option or '" + textHandler.BTN_CONTROL_BACK_CANCEL + "' to go back.", g);

        drawScreenInfoPanel(g);
    }

    private void updateButtonColors() {
        if (optionsHandler.isSoundEnabled()) {
            playerNameColors[0] = menuBtnPlayerSelectedColor;
        } else {
            playerNameColors[0] = menuBtnPlayerDeletedColor;
        }

        if (optionsHandler.isGodModeEnabled()) {
            playerNameColors[1] = menuBtnPlayerSelectedColor;
        } else {
            playerNameColors[1] = menuBtnPlayerDeletedColor;
        }

        if (optionsHandler.isFpsLockEnabled()) {
            playerNameColors[2] = menuBtnPlayerSelectedColor;
        } else {
            playerNameColors[2] = menuBtnPlayerDeletedColor;
        }

        if (optionsHandler.isAntiAliasingEnabled()) {
            playerNameColors[3] = menuBtnPlayerSelectedColor;
        } else {
            playerNameColors[3] = menuBtnPlayerDeletedColor;
        }
    }

}