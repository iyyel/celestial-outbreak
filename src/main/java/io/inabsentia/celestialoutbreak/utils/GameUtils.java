package io.inabsentia.celestialoutbreak.utils;

import io.inabsentia.celestialoutbreak.handler.FileHandler;
import io.inabsentia.celestialoutbreak.handler.TextHandler;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Random;

public final class GameUtils {

    private static GameUtils instance;

    private final TextHandler textHandler = TextHandler.getInstance();
    private final FileHandler fileHandler = FileHandler.getInstance();

    private final Random random = new Random();

    /* GameController flags */
    private boolean isVerboseEnabled = true;
    private boolean isSoundEnabled = true;
    private boolean isGodModeEnabled = false;

    private GameUtils() {
        initGameProperties();
    }

    static {
        try {
            instance = new GameUtils();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static synchronized GameUtils getInstance() {
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
            gameFont = Font.createFont(Font.TRUETYPE_FONT, new File(textHandler.GAME_FONT_LOCAL_PATH));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(textHandler.GAME_FONT_LOCAL_PATH)));
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
        return gameFont;
    }

    private void initGameProperties() {
        Map<String, String> map = fileHandler.readPropertiesFromFile(textHandler.SETTINGS_CONFIG_FILE_CLIENT_PATH);

        this.isVerboseEnabled = Boolean.parseBoolean(map.get(textHandler.PROP_VERBOSE_ENABLED));
        if (isVerboseEnabled)
            fileHandler.writeLog("Verbose logging enabled!");

        this.isSoundEnabled = Boolean.parseBoolean(map.get(textHandler.PROP_SOUND_ENABLED));
        if (isSoundEnabled)
            fileHandler.writeLog("Sound enabled!");

        this.isGodModeEnabled = Boolean.parseBoolean(map.get(textHandler.PROP_GOD_MODE_ENABLED));
        if (isGodModeEnabled)
            fileHandler.writeLog("God Mode enabled. Go crazy!");
    }

    public boolean isVerboseEnabled() {
        return isVerboseEnabled;
    }

    public boolean isSoundEnabled() {
        return isSoundEnabled;
    }

    public boolean isGodModeEnabled() {
        return isGodModeEnabled;
    }

}