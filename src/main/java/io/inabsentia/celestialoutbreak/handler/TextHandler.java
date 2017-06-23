package io.inabsentia.celestialoutbreak.handler;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * Singleton class.
 */
public class TextHandler {

    public final String TITLE = "Celestial Outbreak";
    public final String VERSION = "v0.10a";
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

    private final String spacing = "     ";

    public final String NEW_APP_INSTANCE = "New " + TITLE + " " + VERSION + " instance started at " + getDateTime() + ".";
    public final String NEW_APP_INSTANCE_SUCCESS = "Successfully completed application initialization at " + getDateTime() + ".";

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
    public final String LOG_DIR_NAME = "log";
    public final String LOG_DIR_PATH = MAIN_DIR + File.separator + LOG_DIR_NAME;

    public final String LOG_FILE_NAME = new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + "_verbose-log.txt";
    public final String LOG_FILE_PATH = LOG_DIR_PATH + File.separator + LOG_FILE_NAME;

    public final String logMsgPrefix() {
        return "[VERBOSE-LOG " + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "]: ";
    }

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

    /*
     * Level related messages.
     */
    private final String ERR_PREFIX = "[ERROR]: ";

    public final String vChangedLevelMsg(String prevLevel, String newLevel) {
        return "Changed level from '" + prevLevel + "' to '" + newLevel + "'.";
    }

    public final String errParsingLevelSettings(String fileName, String exceptionMessage) {
        return ERR_PREFIX + "Failed parsing level settings from file '" + fileName + "' cause: '" + exceptionMessage + "'.";
    }

    public final String vLevelFinishedMsg(String levelType) {
        return "Level '" + levelType + "' finished.";
    }

    /* Ball related messages */
    public final String vBallTouchedYAxisBottomMsg = "Ball touched bottom y-axis.";
    public final String vBallTouchedYAxisTopMsg = "Ball touched top y-axis.";
    public final String vBallTouchedXAxisLeftMsg = "Ball touched left x-axis.";
    public final String vBallTouchedXAxisRightMsg = "Ball touched right x-axis.";

    public final String vBallPaddleCollisionMsg(int paddleCollisionTimer) {
        return "Ball collision with Paddle. Changed paddleCollisionTimer: " + paddleCollisionTimer;
    }

    public final String vBallBlockListCollisionMsg(int blockListIndex) {
        return "Ball collision with BlockList[" + blockListIndex + "].";
    }

    /*
     * Settings files.
     */
    public final String SETTINGS_DIR_NAME = "settings";
    public final String SETTINGS_DIR_PATH = MAIN_DIR + File.separator + SETTINGS_DIR_NAME;

    public final String SETTINGS_CONFIG_FILE_NAME = "settings.config";
    public final String SETTINGS_CONFIG_FILE_PATH = SETTINGS_DIR_PATH + File.separator + SETTINGS_CONFIG_FILE_NAME;

    /*
     * Random methods. Clean this up.
     */
    public final String bottomPanelString(String levelName, int playerLives, int playerScore, int blocksLeft) {
        return "Level: " + levelName + spacing + spacing + "Lives: " + playerLives + spacing + "Score: " + playerScore + spacing + "Blocks: " + blocksLeft;
    }

    public final String successCopiedFile(String srcFilePath, String destFilePath) {
        return "Successfully copied '" + srcFilePath + "' to '" + destFilePath + "'.";
    }

    public final String successReadProperties(String filePath) {
        return "Successfully read properties from '" + filePath + "'.";
    }

    public final String successReadLines(String filePath) {
        return "Successfully read lines from '" + filePath + "'.";
    }

    public final String successCreatedDir(String dirPath) {
        return "Successfully created directory '" + dirPath + "'.";
    }

    public final String successCreatedFile(String filePath) {
        return "Successfully created file '" + filePath + "'.";
    }

    public final String performanceMessage(int frames, int updates) {
        return "UPS: " + updates + " FPS: " + frames;
    }

    private final String getDateTime() {
        return new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
    }

}