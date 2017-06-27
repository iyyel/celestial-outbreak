package io.inabsentia.celestialoutbreak.handler;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * Singleton class.
 */
public class TextHandler {

    public final String TITLE = "Celestial Outbreak";
    public final String VERSION = "v0.11a";
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
    public final String exitBtn = "EXIT";

    public final String pauseMsg = "Paused";

    private final String spacing = "     ";

    public final String NEW_APP_INSTANCE = "New " + TITLE + " " + VERSION + " instance started at " + getDateTime() + " on " + System.getProperty("os.name") + ".";
    public final String NEW_APP_INSTANCE_SUCCESS = "Successfully completed " + TITLE + " " + VERSION + " initialization at " + getDateTime() + ".";

    /*
     * Main directory.
     */
    public final String MAIN_DIR = System.getProperty("user.home") + File.separator + TITLE.toLowerCase().replaceAll("\\s+", "");

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
     * Database.
     */
    public final String DATABASE_DIR_NAME = "db";
    public final String DATABASE_DIR_PATH = MAIN_DIR + File.separator + DATABASE_DIR_NAME;

    public final String DATABASE_CONFIG_FILE_NAME = "db.config";
    public final String DATABASE_CONFIG_FILE_PATH = DATABASE_DIR_PATH + File.separator + DATABASE_CONFIG_FILE_NAME;

    /*
     * Settings files.
     */
    public final String SETTINGS_DIR_NAME = "settings";
    public final String SETTINGS_DIR_PATH = MAIN_DIR + File.separator + SETTINGS_DIR_NAME;

    public final String SETTINGS_CONFIG_FILE_NAME = "settings.config";
    public final String SETTINGS_CONFIG_FILE_PATH = SETTINGS_DIR_PATH + File.separator + SETTINGS_CONFIG_FILE_NAME;

    /* Player file. */
    public final String PLAYER_DIR_NAME = SETTINGS_DIR_NAME;
    public final String PLAYER_DIR_PATH = SETTINGS_DIR_PATH;

    public final String PLAYER_CONFIG_FILE_NAME = "player.config";
    public final String PLAYER_CONFIG_FILE_PATH = PLAYER_DIR_PATH + File.separator + PLAYER_CONFIG_FILE_NAME;

    /*
     * Level files.
     */
    public final String LEVEL_DIR_NAME = "levels";
    public final String LEVEL_DIR_PATH = MAIN_DIR + File.separator + LEVEL_DIR_NAME;

    public final String LEVEL_CONFIG_FILE_NAME = "levels.config";
    public final String LEVEL_CONFIG_FILE_PATH = LEVEL_DIR_PATH + File.separator + LEVEL_CONFIG_FILE_NAME;

    public final String LEVEL_FILE_NAME_MARS = "mars_level.config";
    public final String LEVEL_FILE_NAME_EARTH = "earth_level.config";
    public final String LEVEL_FILE_NAME_NEPTUNE = "neptune_level.config";
    public final String LEVEL_FILE_NAME_VENUS = "venus_level.config";
    public final String LEVEL_FILE_NAME_JUPITER = "jupiter_level.config";

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
     * Menu messages.
     */

    /* SCORES message */


    /* SETTINGS message */
    public final String menuSettingsMsg01 = "All configurable settings can be found in the following directory";
    public final String menuSettingsMsg02 = MAIN_DIR;
    public final String menuSettingsMsg03 = "The levels directory contain individual level configuration files";
    public final String menuSettingsMsg04 = "such as " + LEVEL_FILE_NAME_MARS + ", as well as a main configuration file";
    public final String menuSettingsMsg05 = LEVEL_CONFIG_FILE_NAME + ", which contains all the level file names.";
    public final String menuSettingsMsg06 = "The levels will be encountered starting from top to bottom in this file.";


    /* ABOUT message */


    /* EXIT message */


    /* PAUSE message */


    /* NEW_LEVEL message */


    /* FINISHED_LEVEL message */


    /*
     * Random methods. Clean this up.
     */
    public final String gamePanelString(String levelName, int playerLives, int playerScore, int blocksLeft) {
        return "Planet: " + levelName + spacing + spacing + spacing + spacing + spacing + spacing + spacing + spacing + "Lives: " + playerLives + spacing + "Score: " + playerScore + spacing + "Blocks: " + blocksLeft;
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

    public final String performanceMsg(int frames, int updates, double allocatedRam) {
        DecimalFormat df = new DecimalFormat("000");
        return "UPS: " + updates + " - FPS: " + frames + " - RAM: " + df.format(allocatedRam) + "MB";
    }

    private final String getDateTime() {
        return new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
    }

}