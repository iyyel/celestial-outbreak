package io.iyyel.celestialoutbreak.handler;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.exception.ExceptionUtils;

import java.io.*;
import java.nio.file.Files;
import java.util.*;

public final class FileHandler {

    /* Singleton FileHandler instance. */
    private static FileHandler instance;

    private final TextHandler textHandler = TextHandler.getInstance();

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
        createDir(textHandler.SETTINGS_DIR_PATH);
        createDir(textHandler.SOUND_DIR_PATH);
        createDir(textHandler.GAME_FONT_DIR_PATH);
        createDir(textHandler.LEVEL_DIR_PATH);
        createDir(textHandler.PLAYER_DIR_PATH);
        createDir(textHandler.SCORE_DIR_PATH);
    }

    /*
     * Copy the necessary configuration files
     * over to the client machine.
     */
    private void copyConfigFiles() {
        /* Initial level configurations copied to client local settings dir. */
        copyFile(textHandler.LEVEL_FILE_LOCAL_PATH_MARS, textHandler.LEVEL_FILE_CLIENT_PATH_MARS);
        copyFile(textHandler.LEVEL_FILE_LOCAL_PATH_EARTH, textHandler.LEVEL_FILE_CLIENT_PATH_EARTH);
        copyFile(textHandler.LEVEL_FILE_LOCAL_PATH_NEPTUNE, textHandler.LEVEL_FILE_CLIENT_PATH_NEPTUNE);
        copyFile(textHandler.LEVEL_FILE_LOCAL_PATH_VENUS, textHandler.LEVEL_FILE_CLIENT_PATH_VENUS);
        copyFile(textHandler.LEVEL_FILE_LOCAL_PATH_JUPITER, textHandler.LEVEL_FILE_CLIENT_PATH_JUPITER);

        /* Main level configuration file coped to client machine. */
        copyFile(textHandler.LEVEL_CONFIG_FILE_LOCAL_PATH, textHandler.LEVEL_CONFIG_FILE_CLIENT_PATH);

        /* Settings configuration file copied to client machine. */
        copyFile(textHandler.SETTINGS_CONFIG_FILE_LOCAL_PATH, textHandler.SETTINGS_CONFIG_FILE_CLIENT_PATH);

        /* Font */
        copyFile(textHandler.GAME_FONT_FILE_LOCAL_PATH, textHandler.GAME_FONT_FILE_CLIENT_PATH);

        /* Sound */
        copyFile(textHandler.SOUND_FILE_LOCAL_PATH_MENU, textHandler.SOUND_FILE_CLIENT_PATH_MENU);
        copyFile(textHandler.SOUND_FILE_LOCAL_PATH_PLAY, textHandler.SOUND_FILE_CLIENT_PATH_PLAY);
        copyFile(textHandler.SOUND_FILE_LOCAL_PATH_PAUSE, textHandler.SOUND_FILE_CLIENT_PATH_PAUSE);
        copyFile(textHandler.SOUND_FILE_LOCAL_PATH_BALL_HIT, textHandler.SOUND_FILE_CLIENT_PATH_BALL_HIT);
        copyFile(textHandler.SOUND_FILE_LOCAL_PATH_BALL_RESET, textHandler.SOUND_FILE_CLIENT_PATH_BALL_RESET);
        copyFile(textHandler.SOUND_FILE_LOCAL_PATH_MENU_BTN_NAV, textHandler.SOUND_FILE_CLIENT_PATH_MENU_BTN_NAV);
        copyFile(textHandler.SOUND_FILE_LOCAL_PATH_MENU_BTN_USE, textHandler.SOUND_FILE_CLIENT_PATH_MENU_BTN_USE);
        copyFile(textHandler.SOUND_FILE_LOCAL_PATH_BAD_ACTION, textHandler.SOUND_FILE_CLIENT_PATH_BAD_ACTION);
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
        writeLog(textHandler.actionReadingPropertiesMsg(filePath));

        try (InputStream is = new FileInputStream(filePath)) {
            p.load(is);

            for (String key : p.stringPropertyNames()) {
                String value = p.getProperty(key);
                map.put(key, value);
                writeLog(textHandler.successReadPropertyMsg(key, value, filePath));
            }

            writeLog(textHandler.finishReadPropertiesMsg(filePath));
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
        writeLog(textHandler.actionReadingLinesMsg(filePath));

        try (FileInputStream fis = new FileInputStream(new File(filePath))) {
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String line;
            while ((line = br.readLine()) != null) {
                /*
                 * Ignore lines containing comments starting with #
                 * and only add the line if the length of it is greater than zero,
                 * to make sure that there actually is a worthy line to read.
                 */
                if (!line.contains("#") && line.length() > 0) {
                    lineList.add(line);
                    writeLog(textHandler.successReadLineMsg(line, filePath));
                }
            }
            writeLog(textHandler.finishReadLinesMsg(filePath));
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
                if (result)
                    writeLog(textHandler.successCreatedDirMsg(dirPath));
                else
                    writeLog(textHandler.errorCreatingDirMsg(dirPath));
            } catch (SecurityException e) {
                writeLog(textHandler.errorCreatingDirMsg(dirPath, ExceptionUtils.getStackTrace(e)));
            }
        }
    }

    /*
     * Copies the file at srcFilePath to
     * the path at destFilePath, if it does
     * not already exist at the destFilePath.
     */
    private void copyFile(String srcFilePath, String destFilePath) {
        File srcFile = new File(srcFilePath);
        File destFile = new File(destFilePath);

        try {
            /*
             * Check to see whether the file already exists at the destination.
             * If it does, we do not want to overwrite it, since the
             * user could have modified the file to their liking.
             */
            if (!destFile.exists()) {
                FileUtils.copyFile(srcFile, destFile);
                writeLog(textHandler.successCopiedFileMsg(srcFile.getPath(), destFile.getPath()));
            }
        } catch (IOException e) {
            writeLog(textHandler.errorCopyingFileMsg(srcFilePath, destFilePath, ExceptionUtils.getStackTrace(e)));
        }
    }

    /*
     * Writes the content of msg into the
     * file given by filePath.
     * Creates the file anyway if it doesn't
     * already exist.
     */
    public void writeToFile(String msg, String filePath) {
        File file = new File(filePath);
        if (file.isDirectory()) return;

        try {
            if (file.exists()) {
                try (PrintWriter out = new PrintWriter(new FileOutputStream(file, true))) {
                    out.append(msg + "\r\n");
                }
            } else {
                try (PrintWriter out = new PrintWriter(filePath)) {
                    out.print(msg + "\r\n");
                }
                writeLog(textHandler.successCreatedFileMsg(filePath));
            }
        } catch (IOException e) {
            writeLog(textHandler.errorWritingToFileMsg(filePath, ExceptionUtils.getStackTrace(e)));
        }
    }

    /*
     * Outputs the content of msg into the console
     * and writes it to the gameController's log file.
     */
    public void writeLog(String msg) {
        msg = textHandler.logMsgPrefix() + msg;
        System.out.println(msg);
        writeToFile(msg, textHandler.LOG_FILE_PATH);
    }

}