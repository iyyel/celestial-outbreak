package io.iyyel.celestialoutbreak.handler;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.*;
import java.nio.file.Files;
import java.util.*;

public final class FileHandler {

    /* Singleton FileHandler instance. */
    private static FileHandler instance;

    private final TextHandler textHandler = TextHandler.getInstance();
    private final LogHandler logHandler = LogHandler.getInstance();

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
        createDir(textHandler.LOG_DIR_PATH);
        createDir(textHandler.OPTIONS_DIR_CLIENT_PATH);
        createDir(textHandler.SOUND_DIR_PATH);
        createDir(textHandler.FONT_DIR_PATH);
        createDir(textHandler.LEVEL_DIR_PATH);
        createDir(textHandler.PLAYER_DIR_PATH);
        createDir(textHandler.SCORE_DIR_PATH);
    }

    /*
     * Copy the necessary configuration files
     * over to the client machine.
     */
    private void copyConfigFiles() {
        /* Initial level configurations copied to client local options dir. */
        copyFile(textHandler.LEVEL_FILE_LOCAL_PATH_SUN, textHandler.LEVEL_FILE_CLIENT_PATH_SUN);
        copyFile(textHandler.LEVEL_FILE_LOCAL_PATH_MOON, textHandler.LEVEL_FILE_CLIENT_PATH_MOON);
        copyFile(textHandler.LEVEL_FILE_LOCAL_PATH_MARS, textHandler.LEVEL_FILE_CLIENT_PATH_MARS);
        copyFile(textHandler.LEVEL_FILE_LOCAL_PATH_EARTH, textHandler.LEVEL_FILE_CLIENT_PATH_EARTH);
        copyFile(textHandler.LEVEL_FILE_LOCAL_PATH_NEPTUNE, textHandler.LEVEL_FILE_CLIENT_PATH_NEPTUNE);
        copyFile(textHandler.LEVEL_FILE_LOCAL_PATH_VENUS, textHandler.LEVEL_FILE_CLIENT_PATH_VENUS);
        copyFile(textHandler.LEVEL_FILE_LOCAL_PATH_JUPITER, textHandler.LEVEL_FILE_CLIENT_PATH_JUPITER);

        /* Main level configuration file coped to client machine. */
        copyFile(textHandler.LEVEL_CONFIG_FILE_LOCAL_PATH, textHandler.LEVEL_CONFIG_FILE_CLIENT_PATH);

        /* Settings configuration file copied to client machine. */
        copyFile(textHandler.OPTIONS_CONFIG_FILE_LOCAL_PATH, textHandler.OPTIONS_CONFIG_FILE_CLIENT_PATH);

        /* Font */
        copyFile(textHandler.GAME_FONT_FILE_LOCAL_PATH, textHandler.GAME_FONT_FILE_CLIENT_PATH);
        copyFile(textHandler.PANEL_FONT_FILE_LOCAL_PATH, textHandler.PANEL_FONT_FILE_CLIENT_PATH);

        /* Sound */
        copyFile(textHandler.SOUND_FILE_LOCAL_PATH_MENU, textHandler.SOUND_FILE_CLIENT_PATH_MENU);
        copyFile(textHandler.SOUND_FILE_LOCAL_PATH_PAUSE, textHandler.SOUND_FILE_CLIENT_PATH_PAUSE);
        copyFile(textHandler.SOUND_FILE_LOCAL_PATH_BALL_HIT, textHandler.SOUND_FILE_CLIENT_PATH_BALL_HIT);
        copyFile(textHandler.SOUND_FILE_LOCAL_PATH_BALL_RESET, textHandler.SOUND_FILE_CLIENT_PATH_BALL_RESET);
        copyFile(textHandler.SOUND_FILE_LOCAL_PATH_BLOCK_DESTROYED, textHandler.SOUND_FILE_CLIENT_PATH_BLOCK_DESTROYED);
        copyFile(textHandler.SOUND_FILE_LOCAL_PATH_MENU_BTN_NAV, textHandler.SOUND_FILE_CLIENT_PATH_MENU_BTN_NAV);
        copyFile(textHandler.SOUND_FILE_LOCAL_PATH_MENU_BTN_USE, textHandler.SOUND_FILE_CLIENT_PATH_MENU_BTN_USE);
        copyFile(textHandler.SOUND_FILE_LOCAL_PATH_BAD_ACTION, textHandler.SOUND_FILE_CLIENT_PATH_BAD_ACTION);

        copyFile(textHandler.SOUND_FILE_LOCAL_PATH_SUN_LEVEL, textHandler.SOUND_FILE_CLIENT_PATH_SUN_LEVEL);
        copyFile(textHandler.SOUND_FILE_LOCAL_PATH_MOON_LEVEL, textHandler.SOUND_FILE_CLIENT_PATH_MOON_LEVEL);
        copyFile(textHandler.SOUND_FILE_LOCAL_PATH_MARS_LEVEL, textHandler.SOUND_FILE_CLIENT_PATH_MARS_LEVEL);
        copyFile(textHandler.SOUND_FILE_LOCAL_PATH_EARTH_LEVEL, textHandler.SOUND_FILE_CLIENT_PATH_EARTH_LEVEL);
        copyFile(textHandler.SOUND_FILE_LOCAL_PATH_NEPTUNE_LEVEL, textHandler.SOUND_FILE_CLIENT_PATH_NEPTUNE_LEVEL);
        copyFile(textHandler.SOUND_FILE_LOCAL_PATH_VENUS_LEVEL, textHandler.SOUND_FILE_CLIENT_PATH_VENUS_LEVEL);
        copyFile(textHandler.SOUND_FILE_LOCAL_PATH_JUPITER_LEVEL, textHandler.SOUND_FILE_CLIENT_PATH_JUPITER_LEVEL);

        /* README.txt */
        copyFile(textHandler.README_FILE_LOCAL_PATH, textHandler.README_FILE_CLIENT_PATH);
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
        logHandler.log(textHandler.actionReadingPropertiesMsg(filePath), LogHandler.LogLevel.INFORMATION, false);

        try (InputStream is = new FileInputStream(filePath)) {
            p.load(is);

            for (String key : p.stringPropertyNames()) {
                String value = p.getProperty(key);
                value = removeComments(value);
                if (value != null) {
                    map.put(key, value);
                    logHandler.log(textHandler.successReadPropertyMsg(key, value, filePath), LogHandler.LogLevel.INFORMATION, false);
                }
            }

            logHandler.log(textHandler.finishReadPropertiesMsg(filePath), LogHandler.LogLevel.INFORMATION, false);
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
            logHandler.log("FAILED TO WRITE PROPERTY TO FILE: " + filePath, LogHandler.LogLevel.FAILURE, false);
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
        logHandler.log(textHandler.actionReadingLinesMsg(filePath), LogHandler.LogLevel.INFORMATION, false);

        try (FileInputStream fis = new FileInputStream(new File(filePath))) {
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String line;
            while ((line = br.readLine()) != null) {
                line = removeComments(line);
                if (line != null) {
                    lineList.add(line);
                    logHandler.log(textHandler.successReadLineMsg(line, filePath), LogHandler.LogLevel.INFORMATION, false);
                }
            }
            logHandler.log(textHandler.finishReadLinesMsg(filePath), LogHandler.LogLevel.INFORMATION, false);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lineList;
    }

    /*
     * Creates the directory given by dirPath
     * if it does not already exist.
     * If it does exist, nothing happens.
     */
    private void createDir(String dirPath) {
        File dir = new File(dirPath);
        if (!dir.exists()) {
            try {
                boolean result = dir.mkdirs();
                if (result) {
                    logHandler.log(textHandler.successCreatedDirMsg(dirPath), LogHandler.LogLevel.INFORMATION, false);
                } else {
                    logHandler.log(textHandler.errorCreatingDirMsg(dirPath), LogHandler.LogLevel.FAILURE, false);
                }
            } catch (SecurityException e) {
                logHandler.log(textHandler.errorCreatingDirMsg(dirPath, ExceptionUtils.getStackTrace(e)), LogHandler.LogLevel.ERROR, false);
            }
        }
    }

    /*
     * Copies the file at srcFilePath to
     * the path at destFilePath, if it does
     * not already exist at the destFilePath.
     */
    private void copyFile(String srcFilePath, String destFilePath) {
        InputStream srcIs = getClass().getResourceAsStream(srcFilePath);
        File destFile = new File(destFilePath);

        try {
            /*
             * Check to see whether the file already exists at the destination.
             * If it does, we do not want to overwrite it, since the
             * user could have modified the file to their liking.
             */
            if (!destFile.exists()) {
                FileUtils.copyInputStreamToFile(srcIs, destFile);
                logHandler.log(textHandler.successCopiedFileMsg(srcFilePath, destFile.getPath()), LogHandler.LogLevel.INFORMATION, false);
            }
        } catch (IOException e) {
            logHandler.log(textHandler.errorCopyingFileMsg(srcFilePath, destFilePath, ExceptionUtils.getStackTrace(e)), LogHandler.LogLevel.ERROR, false);
        }
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
                value = removeComments(value);

                if (value != null) {
                    logHandler.log(textHandler.successReadPropertyMsg(pKey, value, filePath), LogHandler.LogLevel.INFORMATION, false);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return value;
    }

    /*
     * Remove comments from String line.
     */
    private String removeComments(String line) {
        if (line.trim().length() == 0 || line.trim().charAt(0) == '#') {
            return null;
        }

        String reversed = new StringBuilder(line).reverse().toString();

        int commentIndex = reversed.indexOf('#');

        if (commentIndex == 0) {
            return null;
        }

        if (commentIndex == -1) {
            return line;
        }

        reversed = reversed.substring(commentIndex + 1);

        int firstAlphabeticIndex = -1;

        for (int i = 0; i < reversed.length(); i++) {
            char c = reversed.charAt(i);
            if (!Character.isWhitespace(c)) {
                firstAlphabeticIndex = i;
                break;
            }
        }

        if (firstAlphabeticIndex == -1) {
            return new StringBuilder(reversed).reverse().toString();
        }

        reversed = reversed.substring(firstAlphabeticIndex);

        line = new StringBuilder(reversed).reverse().toString();

        return line;
    }

}