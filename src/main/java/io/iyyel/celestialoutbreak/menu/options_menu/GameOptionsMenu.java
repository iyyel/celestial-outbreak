package io.iyyel.celestialoutbreak.menu.options_menu;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.handler.FileHandler;
import io.iyyel.celestialoutbreak.menu.AbstractMenu;

import java.awt.*;

public final class GameOptionsMenu extends AbstractMenu {

    private final Rectangle isSoundEnabledRect, isGodModeEnabledRect, isFpsLockedEnabledRect, isAntiAliasingEnabledRect;
    private String[] options = {"Sound", "God Mode", "FPS Lock", "Anti-aliasing"};

    private Color[] rectColors;
    private Color[] playerNameColors;
    private int selected = 0;

    private final FileHandler fileHandler = FileHandler.getInstance();

    public GameOptionsMenu(GameController gameController) {
        super(gameController);

        int initialBtnYPos = 240;
        int btnYIncrement = 80;

        /* Option buttons */
        isSoundEnabledRect = new Rectangle(gameController.getWidth() / 2 - 120, initialBtnYPos, 240, 50);
        isGodModeEnabledRect = new Rectangle(gameController.getWidth() / 2 - 120, initialBtnYPos + btnYIncrement, 240, 50);
        isFpsLockedEnabledRect = new Rectangle(gameController.getWidth() / 2 - 120, initialBtnYPos + btnYIncrement * 2, 240, 50);
        isAntiAliasingEnabledRect = new Rectangle(gameController.getWidth() / 2 - 120, initialBtnYPos + btnYIncrement * 3, 240, 50);

        rectColors = new Color[options.length];
        playerNameColors = new Color[options.length];

        for (Color c : rectColors)
            c = menuBtnColor;
    }

    @Override
    public void update() {
        decInputTimer();

        updateButtonColors();

        if (inputHandler.isCancelPressed() && isInputAvailable()) {
            resetInputTimer();
            selected = 0;
            menuUseClip.play(false);
            gameController.switchState(GameController.State.OPTIONS_MENU);
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
                            fileHandler.writePropertyToFile(textHandler.OPTIONS_CONFIG_FILE_CLIENT_PATH, textHandler.PROP_SOUND_ENABLED, pValue);
                            optionsHandler.reloadProperty(textHandler.PROP_SOUND_ENABLED, pValue);
                            soundHandler.playStateSound(gameController.getState(), gameController.getPrevState(), true, true);
                            if (optionsHandler.isVerboseLogEnabled()) {
                                if (optionsHandler.isSoundEnabled()) {
                                    fileHandler.writeLog("Sound has been enabled.");
                                } else {
                                    fileHandler.writeLog("Sound has been disabled.");
                                }
                            }
                            break;
                        case 1:
                            pValue = String.valueOf(!optionsHandler.isGodModeEnabled());
                            fileHandler.writePropertyToFile(textHandler.OPTIONS_CONFIG_FILE_CLIENT_PATH, textHandler.PROP_GOD_MODE_ENABLED, pValue);
                            optionsHandler.reloadProperty(textHandler.PROP_GOD_MODE_ENABLED, pValue);
                            if (optionsHandler.isVerboseLogEnabled()) {
                                if (optionsHandler.isGodModeEnabled()) {
                                    fileHandler.writeLog("God Mode has been enabled.");
                                } else {
                                    fileHandler.writeLog("God Mode has been disabled.");
                                }
                            }
                            break;
                        case 2:
                            pValue = String.valueOf(!optionsHandler.isFpsLockEnabled());
                            fileHandler.writePropertyToFile(textHandler.OPTIONS_CONFIG_FILE_CLIENT_PATH, textHandler.PROP_FPS_LOCK_ENABLED, pValue);
                            optionsHandler.reloadProperty(textHandler.PROP_FPS_LOCK_ENABLED, pValue);
                            if (optionsHandler.isVerboseLogEnabled()) {
                                if (optionsHandler.isFpsLockEnabled()) {
                                    fileHandler.writeLog("FPS Lock has been enabled.");
                                } else {
                                    fileHandler.writeLog("FPS Lock has been disabled.");
                                }
                            }
                            break;
                        case 3:
                            pValue = String.valueOf(!optionsHandler.isAntiAliasingEnabled());
                            fileHandler.writePropertyToFile(textHandler.OPTIONS_CONFIG_FILE_CLIENT_PATH, textHandler.PROP_ANTI_ALIASING_ENABLED, pValue);
                            optionsHandler.reloadProperty(textHandler.PROP_ANTI_ALIASING_ENABLED, pValue);
                            if (optionsHandler.isVerboseLogEnabled()) {
                                if (optionsHandler.isAntiAliasingEnabled()) {
                                    fileHandler.writeLog("Anti-aliasing has been enabled.");
                                } else {
                                    fileHandler.writeLog("Anti-aliasing has been disabled.");
                                }
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
        drawMenuTitle(g);

        /* Show submenu title */
        drawSubmenuTitle(textHandler.TITLE_GAME_OPTIONS_SCREEN, g);

        /* Render buttons  */
        g.setFont(inputBtnFont);

        /* isSoundEnabled button */
        g.setColor(playerNameColors[0]);
        drawCenterString(options[0], isSoundEnabledRect.y + BTN_Y_OFFSET, g, inputBtnFont);
        g.setColor(rectColors[0]);
        g.draw(isSoundEnabledRect);

        /* isGodModeEnabled button */
        g.setColor(playerNameColors[1]);
        drawCenterString(options[1], isGodModeEnabledRect.y + BTN_Y_OFFSET, g, inputBtnFont);
        g.setColor(rectColors[1]);
        g.draw(isGodModeEnabledRect);

        /* isFpsLockedEnabled button */
        g.setColor(playerNameColors[2]);
        drawCenterString(options[2], isFpsLockedEnabledRect.y + BTN_Y_OFFSET, g, inputBtnFont);
        g.setColor(rectColors[2]);
        g.draw(isFpsLockedEnabledRect);

        /* isAntiAliasingEnabled button */
        g.setColor(playerNameColors[3]);
        drawCenterString(options[3], isAntiAliasingEnabledRect.y + BTN_Y_OFFSET, g, inputBtnFont);
        g.setColor(rectColors[3]);
        g.draw(isAntiAliasingEnabledRect);

        drawInfoPanel(g);
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