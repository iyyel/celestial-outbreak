package io.iyyel.celestialoutbreak.handler;

import java.awt.*;
import java.util.Map;

public final class OptionsHandler {

    private static OptionsHandler instance;

    private final TextHandler textHandler = TextHandler.getInstance();
    private final FileHandler fileHandler = FileHandler.getInstance();

    /* Game Option flags */
    private boolean isVerboseLogEnabled = true; // verbose logging and info in the game
    private boolean isSoundEnabled = true; // sound on or off
    private boolean isGodModeEnabled = false; // GOD MODE :) // fast paced? endless life?
    private boolean isFpsLockEnabled = true; // lock game to 60 fps and not ASAP performance.
    private boolean isAntiAliasingEnabled = true; // speaks for itself :)

    /* Menu colors */
    private Color menuFontColor;
    private Color menuBtnColor;
    private Color menuBtnSelectedColor;
    private Color menuBtnPlayerSelectedColor;
    private Color menuBtnPlayerDeletedColor;

    static {
        try {
            instance = new OptionsHandler();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private OptionsHandler() {
        initGameOptions();
    }

    public synchronized static OptionsHandler getInstance() {
        return instance;
    }

    private void initGameOptions() {
        Map<String, String> pMap = fileHandler.readPropertiesFromFile(textHandler.OPTIONS_CONFIG_FILE_CLIENT_PATH);
        loadGameOptions(pMap);
        loadColorOptions(pMap);
    }

    private void loadGameOptions(Map<String, String> pMap) {
        // Verbose logging
        this.isVerboseLogEnabled = parseProp(pMap, textHandler.PROP_VERBOSE_LOG_ENABLED);
        fileHandler.writeLog(textHandler.getGamePropertyLogString(isVerboseLogEnabled, "Verbose logging"));

        // Sound
        this.isSoundEnabled = parseProp(pMap, textHandler.PROP_SOUND_ENABLED);
        fileHandler.writeLog(textHandler.getGamePropertyLogString(isSoundEnabled, "Sound"));

        // God mode
        this.isGodModeEnabled = parseProp(pMap, textHandler.PROP_GOD_MODE_ENABLED);
        fileHandler.writeLog(textHandler.getGamePropertyLogString(isGodModeEnabled, "God Mode"));

        // FPS lock
        this.isFpsLockEnabled = parseProp(pMap, textHandler.PROP_FPS_LOCK_ENABLED);
        fileHandler.writeLog(textHandler.getGamePropertyLogString(isSoundEnabled, "FPS Lock"));

        // Anti aliasing
        this.isAntiAliasingEnabled = parseProp(pMap, textHandler.PROP_ANTI_ALIASING_ENABLED);
        fileHandler.writeLog(textHandler.getGamePropertyLogString(isSoundEnabled, "Anti-aliasing"));
    }

    private void loadColorOptions(Map<String, String> pMap) {
        this.menuFontColor = getPropertyColor(pMap, textHandler.PROP_MENU_FONT_COLOR_HEX);
        fileHandler.writeLog(textHandler.getMenuColorLogString(textHandler.PROP_MENU_FONT_COLOR_HEX, menuFontColor));

        this.menuBtnColor = getPropertyColor(pMap, textHandler.PROP_MENU_BTN_COLOR_HEX);
        fileHandler.writeLog(textHandler.getMenuColorLogString(textHandler.PROP_MENU_BTN_COLOR_HEX, menuBtnColor));

        this.menuBtnSelectedColor = getPropertyColor(pMap, textHandler.PROP_MENU_BTN_SELECTED_COLOR_HEX);
        fileHandler.writeLog(textHandler.getMenuColorLogString(textHandler.PROP_MENU_BTN_SELECTED_COLOR_HEX, menuBtnSelectedColor));

        this.menuBtnPlayerSelectedColor = getPropertyColor(pMap, textHandler.PROP_MENU_BTN_PLAYER_SELECTED_COLOR_HEX);
        fileHandler.writeLog(textHandler.getMenuColorLogString(textHandler.PROP_MENU_BTN_PLAYER_SELECTED_COLOR_HEX, menuBtnPlayerSelectedColor));

        this.menuBtnPlayerDeletedColor = getPropertyColor(pMap, textHandler.PROP_MENU_BTN_PLAYER_DELETED_COLOR_HEX);
        fileHandler.writeLog(textHandler.getMenuColorLogString(textHandler.PROP_MENU_BTN_PLAYER_DELETED_COLOR_HEX, menuBtnPlayerDeletedColor));
    }

    private boolean parseProp(Map<String, String> map, String pKey) {
        return Boolean.parseBoolean(map.get(pKey));
    }

    private Color getPropertyColor(Map<String, String> map, String pKey) {
        return new Color(Integer.decode(map.get(pKey)));
    }

    public boolean isVerboseLogEnabled() {
        return isVerboseLogEnabled;
    }

    public boolean isSoundEnabled() {
        return isSoundEnabled;
    }

    public boolean isGodModeEnabled() {
        return isGodModeEnabled;
    }

    public boolean isFpsLockEnabled() {
        return isFpsLockEnabled;
    }

    public boolean isAntiAliasingEnabled() {
        return isAntiAliasingEnabled;
    }

    public Color getMenuFontColor() {
        return menuFontColor;
    }

    public Color getMenuBtnColor() {
        return menuBtnColor;
    }

    public Color getMenuBtnSelectedColor() {
        return menuBtnSelectedColor;
    }

    public Color getMenuBtnPlayerSelectedColor() {
        return menuBtnPlayerSelectedColor;
    }

    public Color getMenuBtnPlayerDeletedColor() {
        return menuBtnPlayerDeletedColor;
    }

}