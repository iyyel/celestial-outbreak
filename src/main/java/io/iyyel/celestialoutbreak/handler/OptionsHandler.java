package io.iyyel.celestialoutbreak.handler;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public final class OptionsHandler {

    private static final OptionsHandler instance;

    private final TextHandler textHandler = TextHandler.getInstance();
    private final LogHandler logHandler = LogHandler.getInstance();
    private final FileHandler fileHandler = FileHandler.getInstance();

    /* Property Game Options map */
    private final Map<String, Boolean> gamePropMap = new HashMap<String, Boolean>() {
        {
            put(textHandler.PROP_KEY_VERBOSE_LOG_ENABLED, false);
            put(textHandler.PROP_KEY_SOUND_ENABLED, false);
            put(textHandler.PROP_KEY_GOD_MODE_ENABLED, false);
            put(textHandler.PROP_KEY_FPS_LOCK_ENABLED, false);
            put(textHandler.PROP_KEY_ANTI_ALIASING_ENABLED, false);
        }
    };

    /* MenuColor Property map */
    private final Map<String, Color> menuColorPropMap = new HashMap<String, Color>() {
        {
            put(textHandler.PROP_KEY_MENU_FONT_COLOR_HEX, Color.BLACK);
            put(textHandler.PROP_KEY_MENU_BTN_COLOR_HEX, Color.BLACK);
            put(textHandler.PROP_KEY_MENU_BTN_SELECTED_COLOR_HEX, Color.BLACK);
            put(textHandler.PROP_KEY_MENU_BTN_PLAYER_SELECTED_COLOR_HEX, Color.BLACK);
            put(textHandler.PROP_KEY_MENU_BTN_PLAYER_DELETED_COLOR_HEX, Color.BLACK);
        }
    };

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
        for (String pKey : gamePropMap.keySet()) {
            // Put the actual value of 'pKey' inside gamePropMap
            boolean pValue = parseGameOptionProperty(pMap, pKey);
            gamePropMap.put(pKey, pValue);
            // Log
            logHandler.log(textHandler.getGamePropertyLogString(pValue, pKey), "loadGameOptions", LogHandler.LogLevel.INFO, false);
        }
    }

    private void loadColorOptions(Map<String, String> pMap) {
        for (String pKey : menuColorPropMap.keySet()) {
            // Put the actual value of 'pKey' inside menuColorPropMap
            Color color = parseMenuColorProperty(pMap, pKey);
            menuColorPropMap.put(pKey, color);
            // Log
            logHandler.log(textHandler.getMenuColorLogString(pKey, color), "loadColorOptions", LogHandler.LogLevel.INFO, false);
        }
    }

    private boolean parseGameOptionProperty(Map<String, String> map, String pKey) {
        return Boolean.parseBoolean(map.get(pKey));
    }

    private Color parseMenuColorProperty(Map<String, String> map, String pKey) {
        return new Color(Integer.decode(map.get(pKey)));
    }

    public void reloadProperty(String pKey, String pValue) {
        gamePropMap.put(pKey, Boolean.parseBoolean(pValue));
    }

    public boolean isVerboseLogEnabled() {
        return gamePropMap.get(textHandler.PROP_KEY_VERBOSE_LOG_ENABLED);
    }

    public boolean isSoundEnabled() {
        return gamePropMap.get(textHandler.PROP_KEY_SOUND_ENABLED);
    }

    public boolean isGodModeEnabled() {
        return gamePropMap.get(textHandler.PROP_KEY_GOD_MODE_ENABLED);
    }

    public boolean isFpsLockEnabled() {
        return gamePropMap.get(textHandler.PROP_KEY_FPS_LOCK_ENABLED);
    }

    public boolean isAntiAliasingEnabled() {
        return gamePropMap.get(textHandler.PROP_KEY_ANTI_ALIASING_ENABLED);
    }

    public Color getMenuFontColor() {
        return menuColorPropMap.get(textHandler.PROP_KEY_MENU_FONT_COLOR_HEX);
    }

    public Color getMenuBtnColor() {
        return menuColorPropMap.get(textHandler.PROP_KEY_MENU_BTN_COLOR_HEX);
    }

    public Color getMenuBtnSelectedColor() {
        return menuColorPropMap.get(textHandler.PROP_KEY_MENU_BTN_SELECTED_COLOR_HEX);
    }

    public Color getMenuBtnPlayerSelectedColor() {
        return menuColorPropMap.get(textHandler.PROP_KEY_MENU_BTN_PLAYER_SELECTED_COLOR_HEX);
    }

    public Color getMenuBtnPlayerDeletedColor() {
        return menuColorPropMap.get(textHandler.PROP_KEY_MENU_BTN_PLAYER_DELETED_COLOR_HEX);
    }

}