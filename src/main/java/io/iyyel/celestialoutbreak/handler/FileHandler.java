package io.iyyel.celestialoutbreak.handler;

import io.iyyel.celestialoutbreak.util.FileUtil;

import java.io.*;
import java.nio.file.Files;
import java.util.*;

public final class FileHandler {

    private final TextHandler textHandler = TextHandler.getInstance();
    private final LogHandler logHandler = LogHandler.getInstance();
    private final FileUtil fileUtil = FileUtil.getInstance();

    /* Singleton FileHandler instance. */
    private static final FileHandler instance;

    /*
     * Private constructor so that FileHandler
     * can't be instantiated outside.
     */
    private FileHandler() {
        initFileHandler();
    }

    /*
     * Static block to instantiate the
     * Singleton FileHandler instance.
     */
    static {
        try {
            instance = new FileHandler();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * Getter for the Singleton instance.
     */
    public static synchronized FileHandler getInstance() {
        return instance;
    }

    /*
     * Setup the standard gameController directories and
     * copy the necessary configuration files over
     * to the client machine.
     */
    private void initFileHandler() {
        createStandardDirs();
        copyConfigFiles();
    }

    /*
     * Create the standard gameController directories.
     */
    private void createStandardDirs() {
        fileUtil.createDir(textHandler.LOG_DIR_PATH);
        fileUtil.createDir(textHandler.OPTIONS_DIR_CLIENT_PATH);
        fileUtil.createDir(textHandler.SOUND_DIR_PATH);
        fileUtil.createDir(textHandler.FONT_DIR_PATH);
        fileUtil.createDir(textHandler.LEVEL_DIR_PATH);
        fileUtil.createDir(textHandler.PLAYER_DIR_PATH);
        fileUtil.createDir(textHandler.SCORE_DIR_PATH);
    }

    /*
     * Copy the necessary configuration files
     * over to the client machine.
     */
    private void copyConfigFiles() {
        /* Settings configuration file copied to client machine. */
        fileUtil.copyFile(textHandler.OPTIONS_CONFIG_FILE_LOCAL_PATH, textHandler.OPTIONS_CONFIG_FILE_CLIENT_PATH);

        /* Font */
        fileUtil.copyFile(textHandler.GAME_FONT_FILE_LOCAL_PATH, textHandler.GAME_FONT_FILE_CLIENT_PATH);

        /* Main level configuration file coped to client machine. */
        fileUtil.copyFile(textHandler.LEVEL_CONFIG_FILE_LOCAL_PATH, textHandler.LEVEL_CONFIG_FILE_CLIENT_PATH);

        /* Initial level configurations copied to client local options dir. */
        fileUtil.copyFile(textHandler.LEVEL_FILE_LOCAL_PATH_SUN, textHandler.LEVEL_FILE_CLIENT_PATH_SUN);
        fileUtil.copyFile(textHandler.LEVEL_FILE_LOCAL_PATH_MOON, textHandler.LEVEL_FILE_CLIENT_PATH_MOON);
        fileUtil.copyFile(textHandler.LEVEL_FILE_LOCAL_PATH_MARS, textHandler.LEVEL_FILE_CLIENT_PATH_MARS);
        fileUtil.copyFile(textHandler.LEVEL_FILE_LOCAL_PATH_EARTH, textHandler.LEVEL_FILE_CLIENT_PATH_EARTH);
        fileUtil.copyFile(textHandler.LEVEL_FILE_LOCAL_PATH_NEPTUNE, textHandler.LEVEL_FILE_CLIENT_PATH_NEPTUNE);
        fileUtil.copyFile(textHandler.LEVEL_FILE_LOCAL_PATH_VENUS, textHandler.LEVEL_FILE_CLIENT_PATH_VENUS);
        fileUtil.copyFile(textHandler.LEVEL_FILE_LOCAL_PATH_JUPITER, textHandler.LEVEL_FILE_CLIENT_PATH_JUPITER);
        fileUtil.copyFile(textHandler.LEVEL_FILE_LOCAL_PATH_SATURN, textHandler.LEVEL_FILE_CLIENT_PATH_SATURN);
        fileUtil.copyFile(textHandler.LEVEL_FILE_LOCAL_PATH_BLACKHOLE, textHandler.LEVEL_FILE_CLIENT_PATH_BLACKHOLE);

        /* Sound */
        fileUtil.copyFile(textHandler.SOUND_FILE_LOCAL_PATH_MENU, textHandler.SOUND_FILE_CLIENT_PATH_MENU);
        fileUtil.copyFile(textHandler.SOUND_FILE_LOCAL_PATH_PAUSE, textHandler.SOUND_FILE_CLIENT_PATH_PAUSE);
        fileUtil.copyFile(textHandler.SOUND_FILE_LOCAL_PATH_BALL_HIT, textHandler.SOUND_FILE_CLIENT_PATH_BALL_HIT);
        fileUtil.copyFile(textHandler.SOUND_FILE_LOCAL_PATH_BALL_RESET, textHandler.SOUND_FILE_CLIENT_PATH_BALL_RESET);
        fileUtil.copyFile(textHandler.SOUND_FILE_LOCAL_PATH_BLOCK_DESTROYED, textHandler.SOUND_FILE_CLIENT_PATH_BLOCK_DESTROYED);
        fileUtil.copyFile(textHandler.SOUND_FILE_LOCAL_PATH_MENU_BTN_NAV, textHandler.SOUND_FILE_CLIENT_PATH_MENU_BTN_NAV);
        fileUtil.copyFile(textHandler.SOUND_FILE_LOCAL_PATH_MENU_BTN_AUX, textHandler.SOUND_FILE_CLIENT_PATH_MENU_BTN_AUX);
        fileUtil.copyFile(textHandler.SOUND_FILE_LOCAL_PATH_BAD_ACTION, textHandler.SOUND_FILE_CLIENT_PATH_BAD_ACTION);
        fileUtil.copyFile(textHandler.SOUND_FILE_LOCAL_PATH_POWERUP_SPAWN, textHandler.SOUND_FILE_CLIENT_PATH_POWERUP_SPAWN);
        fileUtil.copyFile(textHandler.SOUND_FILE_LOCAL_PATH_POWERUP_GOOD_COLLIDE, textHandler.SOUND_FILE_CLIENT_PATH_POWERUP_GOOD_COLLIDE);
        fileUtil.copyFile(textHandler.SOUND_FILE_LOCAL_PATH_POWERUP_BAD_COLLIDE, textHandler.SOUND_FILE_CLIENT_PATH_POWERUP_BAD_COLLIDE);

        fileUtil.copyFile(textHandler.SOUND_FILE_LOCAL_PATH_SUN_LEVEL, textHandler.SOUND_FILE_CLIENT_PATH_SUN_LEVEL);
        fileUtil.copyFile(textHandler.SOUND_FILE_LOCAL_PATH_MOON_LEVEL, textHandler.SOUND_FILE_CLIENT_PATH_MOON_LEVEL);
        fileUtil.copyFile(textHandler.SOUND_FILE_LOCAL_PATH_MARS_LEVEL, textHandler.SOUND_FILE_CLIENT_PATH_MARS_LEVEL);
        fileUtil.copyFile(textHandler.SOUND_FILE_LOCAL_PATH_EARTH_LEVEL, textHandler.SOUND_FILE_CLIENT_PATH_EARTH_LEVEL);
        fileUtil.copyFile(textHandler.SOUND_FILE_LOCAL_PATH_NEPTUNE_LEVEL, textHandler.SOUND_FILE_CLIENT_PATH_NEPTUNE_LEVEL);
        fileUtil.copyFile(textHandler.SOUND_FILE_LOCAL_PATH_VENUS_LEVEL, textHandler.SOUND_FILE_CLIENT_PATH_VENUS_LEVEL);
        fileUtil.copyFile(textHandler.SOUND_FILE_LOCAL_PATH_JUPITER_LEVEL, textHandler.SOUND_FILE_CLIENT_PATH_JUPITER_LEVEL);
        fileUtil.copyFile(textHandler.SOUND_FILE_LOCAL_PATH_SATURN_LEVEL, textHandler.SOUND_FILE_CLIENT_PATH_SATURN_LEVEL);
        fileUtil.copyFile(textHandler.SOUND_FILE_LOCAL_PATH_BLACKHOLE_LEVEL, textHandler.SOUND_FILE_CLIENT_PATH_BLACKHOLE_LEVEL);

        /* README.txt */
        fileUtil.copyFile(textHandler.README_FILE_LOCAL_PATH, textHandler.README_FILE_CLIENT_PATH);
    }

    /*
     * Read properties from the file given by the filePath.
     * The properties are returned as a map data structure,
     * where the property name is the key, and the property
     * value is equal to the, well, value.
     */
    public Map<String, String> readPropertiesFromFile(String filePath) {
        Properties p = new Properties();

        Map<String, String> map = new HashMap<>();
        logHandler.log(textHandler.actionReadingPropertiesMsg(filePath), "readPropertiesFromFile", LogHandler.LogLevel.INFO, false);

        try (InputStream is = new FileInputStream(filePath)) {
            p.load(is);

            for (String key : p.stringPropertyNames()) {
                String value = p.getProperty(key);
                value = fileUtil.removeCommentFromLine(value);
                if (value != null) {
                    map.put(key, value);
                    logHandler.log(textHandler.successReadPropertyMsg(key, value, filePath), "readPropertiesFromFile", LogHandler.LogLevel.INFO, false);
                }
            }

            logHandler.log(textHandler.finishReadPropertiesMsg(filePath), "readPropertiesFromFile", LogHandler.LogLevel.INFO, false);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;
    }

    public void writePropertyToFile(String filePath, String pKey, String pValue) {
        File file = new File(filePath);

        if (file.isDirectory()) {
            return;
        }

        if (!file.exists()) {
            return;
        }

        List<String> lines = null;

        try {
            lines = Files.readAllLines(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        int index = 0;

        if (lines == null) {
            logHandler.log("Failed to write property to file: " + filePath, "writePropertyToFile", LogHandler.LogLevel.FAIL, false);
            return;
        }

        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).contains(pKey)) {
                index = i;
                break;
            }
        }

        String newLine = pKey + "=" + pValue;

        lines.set(index, newLine);

        try {
            Files.write(file.toPath(), lines);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /*
     * Read lines from the file given by filePath
     * and return them as a List<String> object.
     */
    public List<String> readLinesFromFile(String filePath) {
        List<String> lineList = new ArrayList<>();
        logHandler.log(textHandler.actionReadingLinesMsg(filePath), "readLinesFromFile", LogHandler.LogLevel.INFO, false);

        try (FileInputStream fis = new FileInputStream(new File(filePath))) {
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String line;
            while ((line = br.readLine()) != null) {
                line = fileUtil.removeCommentFromLine(line);
                if (line != null) {
                    lineList.add(line);
                    logHandler.log(textHandler.successReadLineMsg(line, filePath), "readLinesFromFile", LogHandler.LogLevel.INFO, false);
                }
            }
            logHandler.log(textHandler.finishReadLinesMsg(filePath), "readLinesFromFile", LogHandler.LogLevel.INFO, false);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lineList;
    }

    public String readPropertyFromFile(String pKey, String filePath) {
        Properties p = new Properties();
        String value = "";

        try (InputStream is = new FileInputStream(filePath)) {
            p.load(is);

            for (String key : p.stringPropertyNames()) {
                if (!key.equals(pKey)) {
                    continue;
                }

                value = p.getProperty(key);
                value = fileUtil.removeCommentFromLine(value);

                if (value != null) {
                    logHandler.log(textHandler.successReadPropertyMsg(pKey, value, filePath), "readPropertyFromFile", LogHandler.LogLevel.INFO, false);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return value;
    }

}