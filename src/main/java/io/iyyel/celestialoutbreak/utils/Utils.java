package io.iyyel.celestialoutbreak.utils;

import io.iyyel.celestialoutbreak.data.dao.PlayerDAO;
import io.iyyel.celestialoutbreak.data.dao.interfaces.IPlayerDAO;
import io.iyyel.celestialoutbreak.handler.FileHandler;
import io.iyyel.celestialoutbreak.handler.TextHandler;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Random;

public final class Utils {

    private static Utils instance;

    private final TextHandler textHandler = TextHandler.getInstance();
    private final FileHandler fileHandler = FileHandler.getInstance();

    private final Random random = new Random();

    /* GameController flags */
    private boolean isVerboseLogEnabled = true; // verbose logging and info in the game
    private boolean isSoundEnabled = true; // sound on or off
    private boolean isGodModeEnabled = false; // GOD MODE :) // fast paced? endless life?
    private boolean isFirstRunEnabled = true; // first ever executing the game?
    private boolean isFpsLockEnabled = true; // lock game to 60 fps and not ASAP performance.
    private boolean isAntiAliasingEnabled = true; // speaks for itself :)

    /* Menu colors */
    private Color menuFontColor;
    private Color menuBtnColor;
    private Color menuBtnSelectedColor;
    private Color menuBtnPlayerSelectedColor;
    private Color menuBtnPlayerDeletedColor;

    private Utils() {
        initGameProperties();
    }

    static {
        try {
            instance = new Utils();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static synchronized Utils getInstance() {
        return instance;
    }

    public Color generatePastelColor(final float luminance, final float sat) {
        final float hue = random.nextFloat();
        final float saturation = (random.nextInt(2000) + 1000) / sat;
        final Color color = Color.getHSBColor(hue, saturation, luminance);
        return color;
    }

    public Font getGameFont() {
        Font gameFont = null;
        try {
            gameFont = Font.createFont(Font.TRUETYPE_FONT, new File(textHandler.GAME_FONT_FILE_CLIENT_PATH));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(textHandler.GAME_FONT_FILE_CLIENT_PATH)));
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
        return gameFont;
    }

    private void initGameProperties() {
        Map<String, String> map = fileHandler.readPropertiesFromFile(textHandler.OPTIONS_CONFIG_FILE_CLIENT_PATH);

        // Verbose logging
        this.isVerboseLogEnabled = parseProp(map, textHandler.PROP_VERBOSE_LOG_ENABLED);
        if (isVerboseLogEnabled) {
            fileHandler.writeLog("Verbose logging enabled.");
        } else {
            fileHandler.writeLog("Verbose logging disabled.");
        }

        // Sound
        this.isSoundEnabled = parseProp(map, textHandler.PROP_SOUND_ENABLED);
        if (isSoundEnabled) {
            fileHandler.writeLog("Sound enabled.");
        } else {
            fileHandler.writeLog("Sound disabled.");
        }

        // God mode
        this.isGodModeEnabled = parseProp(map, textHandler.PROP_GOD_MODE_ENABLED);
        if (isGodModeEnabled) {
            fileHandler.writeLog("God Mode enabled.");
        } else {
            fileHandler.writeLog("God Mode disabled.");
        }

        // First run
        this.isFirstRunEnabled = parseProp(map, textHandler.PROP_FIRST_RUN_ENABLED);
        if (isFirstRunEnabled) {
            fileHandler.writeLog("First Run enabled.");
        } else {
            fileHandler.writeLog("First Run disabled.");
        }

        // FPS lock
        this.isFpsLockEnabled = parseProp(map, textHandler.PROP_FPS_LOCK_ENABLED);
        if (isFpsLockEnabled) {
            fileHandler.writeLog("FPS lock enabled.");
        } else {
            fileHandler.writeLog("FPS lock disabled.");
        }

        // Anti aliasing
        this.isAntiAliasingEnabled = parseProp(map, textHandler.PROP_ANTI_ALIASING_ENABLED);
        if (isAntiAliasingEnabled) {
            fileHandler.writeLog("Anti-aliasing enabled.");
        } else {
            fileHandler.writeLog("Anti-aliasing disabled.");
        }

        this.menuFontColor = new Color(Integer.decode(map.get(textHandler.PROP_MENU_FONT_COLOR_HEX)));
        fileHandler.writeLog("menuFontColor: " + menuFontColor);

        this.menuBtnColor = new Color(Integer.decode(map.get(textHandler.PROP_MENU_BTN_COLOR_HEX)));
        fileHandler.writeLog("menuBtnColor: " + menuBtnColor);

        this.menuBtnSelectedColor = new Color(Integer.decode(map.get(textHandler.PROP_MENU_BTN_SELECTED_COLOR_HEX)));
        fileHandler.writeLog("menuBtnSelectedColor: " + menuBtnSelectedColor);

        this.menuBtnPlayerSelectedColor = new Color(Integer.decode(map.get(textHandler.PROP_MENU_BTN_PLAYER_SELECTED_COLOR_HEX)));
        fileHandler.writeLog("menuBtnPlayerSelectedColor: " + menuBtnPlayerSelectedColor);

        this.menuBtnPlayerDeletedColor = new Color(Integer.decode(map.get(textHandler.PROP_MENU_BTN_PLAYER_DELETED_COLOR_HEX)));
        fileHandler.writeLog("menuBtnPlayerDeletedColor: " + menuBtnPlayerDeletedColor);
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

    public boolean isFirstRunEnabled() {
        return isFirstRunEnabled;
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

    private boolean parseProp(Map<String, String> map, String prop) {
        return Boolean.parseBoolean(map.get(prop));
    }

    public void createDemoPlayers(PlayerDAO playerDAO) {
        try {
            playerDAO.addPlayer("Fluffy");
            playerDAO.selectPlayer("Fluffy");
            playerDAO.addPlayer("Rocket");
            playerDAO.addPlayer("Jackson");
            playerDAO.addPlayer("Sally");
            playerDAO.addPlayer("Emil");
            playerDAO.addPlayer("Dimitri");
            playerDAO.addPlayer("Jake");
            playerDAO.addPlayer("Veronica");
            playerDAO.addPlayer("Mike");
            playerDAO.addPlayer("Clarke");
            playerDAO.addPlayer("Mustafa");
            playerDAO.addPlayer("WoW");
            playerDAO.addPlayer("Iyyel");
            playerDAO.addPlayer("Musti");
            playerDAO.addPlayer("Hassan");
            playerDAO.addPlayer("Uweuweu");
            playerDAO.addPlayer("Cododa");
            playerDAO.addPlayer("Troels");
            playerDAO.addPlayer("Behnia");
            playerDAO.addPlayer("Thomas");
            playerDAO.addPlayer("Daniel");
            playerDAO.addPlayer("Kevin");
            playerDAO.addPlayer("Alice");
            playerDAO.addPlayer("Ryan");
            playerDAO.addPlayer("Rackman");
        } catch (IPlayerDAO.PlayerDAOException e) {
            e.printStackTrace();
        }
    }

}