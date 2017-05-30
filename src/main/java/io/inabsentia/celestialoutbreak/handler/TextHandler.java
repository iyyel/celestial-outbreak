package io.inabsentia.celestialoutbreak.handler;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * Singleton class.
 */
public class TextHandler {

    public final String TITLE = "Celestial Outbreak";
    public final String VERSION = "v0.01a";
    public final String EMAIL = "inabsentia.io";

    private static final TextHandler instance = new TextHandler();

    private TextHandler() {

    }

    public synchronized static TextHandler getInstance() {
        return instance;
    }

    public final String playBtn = "PLAY";
    public final String scoreBtn = "SCORES";
    public final String settingsBtn = "SETTINGS";
    public final String aboutBtn = "ABOUT";
    public final String quitBtn = "QUIT";

    public final String pauseMsg = "Paused";
    public final String pauseStartMsg = "Press \"p\" to continue the game!";

    public final String spacing = "     ";

    /*
     * Main directory.
     */
    public final String MAIN_DIR = System.getProperty("user.home") + File.separator + TITLE.toLowerCase().replaceAll("\\s+", "") + "-config";

    /*
     * Configuration files.
     */
    public final String JAR_CONFIG_DIR = "/config/";

    /*
     * Log files.
     */
    public final String LOG_FILE_NAME = new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + "_verbose-log.txt";
    public final String LOG_MESSAGE_PREFIX = "[VERBOSE-LOG " + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "]: ";
    public final String LOG_DIR = MAIN_DIR + File.separator + "log";

    /*
     * Level files.
     */
    public final String LEVEL_DIR_NAME = "levels";
    public final String LEVEL_DIR_PATH = MAIN_DIR + File.separator + LEVEL_DIR_NAME;

    public final String LEVEL_CONFIG_FILE_NAME = "levels.config";
    public final String LEVEL_CONFIG_FILE_PATH = LEVEL_DIR_PATH + File.separator + LEVEL_CONFIG_FILE_NAME;

    public final String LEVEL_FILE_NAME_RED = "redlevel.config";
    public final String LEVEL_FILE_NAME_GREEN = "greenlevel.config";
    public final String LEVEL_FILE_NAME_BLUE = "bluelevel.config";
    public final String LEVEL_FILE_NAME_PURPLE = "purplelevel.config";
    public final String LEVEL_FILE_NAME_BORDEAUX = "bordeauxlevel.config";

    public final String bottomPanelString(String levelName, int playerLives, int playerScore, int blocksLeft) {
        return "Level: " + levelName + spacing + spacing + "Lives: " + playerLives + spacing + "Score: " + playerScore + spacing + "Blocks: " + blocksLeft;
    }

    public final String successCopiedFile(String srcFilePath, String destFilePath) {
        return "Successfully copied '" + srcFilePath + "' to '" + destFilePath + "'.";
    }

    public final String successReadProperties(String filePath) {
        return "Read properties from '" + filePath + "' successfully.";
    }

    public final String successReadLines(String filePath) {
        return "Read lines from '" + filePath + "' successfully.";
    }

    public final String successCreatedDir(String dirPath) {
        return "Created directory '" + dirPath + "' successfully.";
    }

}