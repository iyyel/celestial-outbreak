package io.inabsentia.celestialoutbreak.handler;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * Singleton class.
 */
public class TextHandler {

    private static TextHandler instance;

    private TextHandler() {

    }

    static {
        try {
            instance = new TextHandler();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized static TextHandler getInstance() {
        return instance;
    }

    /*
     * Important game strings.
     */
    public final String TITLE = "Celestial Outbreak";
    public final String VERSION = "v0.12a";
    public final String EMAIL = "inabsentia.io";

    /*
     * Menu button names.
     */
    public final String playBtn = "PLAY";
    public final String scoreBtn = "SCORES";
    public final String settingsBtn = "SETTINGS";
    public final String aboutBtn = "ABOUT";
    public final String exitBtn = "EXIT";

    public final String pauseMsg = "Paused";

    public final String NEW_APP_INSTANCE = "New " + TITLE + " " + VERSION + " instance started at " + getDateTime() + " on " + System.getProperty("os.name");
    public final String NEW_APP_INSTANCE_SUCCESS = "Finished " + TITLE + " " + VERSION + " initialization at " + getDateTime() + " on " + System.getProperty("os.name");

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

    public final String LOG_FILE_NAME = new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + "_log.txt";
    public final String LOG_FILE_PATH = LOG_DIR_PATH + File.separator + LOG_FILE_NAME;

    public final String logMsgPrefix() {
        return "[LOG " + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "]: ";
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
    public final String SETTINGS_CONFIG_FILE_LOCAL_PATH = TextHandler.class.getResource(JAR_CONFIG_DIR + SETTINGS_CONFIG_FILE_NAME).getPath();
    public final String SETTINGS_CONFIG_FILE_CLIENT_PATH = SETTINGS_DIR_PATH + File.separator + SETTINGS_CONFIG_FILE_NAME;

    /*
     * Font paths.
     */
    public final String GAME_FONT_NAME = "neuropol.ttf";
    public final String GAME_FONT_DIR_LOCAL_PATH = "/fonts/";
    public final String GAME_FONT_LOCAL_PATH = TextHandler.class.getResource(GAME_FONT_DIR_LOCAL_PATH + GAME_FONT_NAME).getPath();

    /*
     * Player files.
     */
    public final String PLAYER_DIR_NAME = SETTINGS_DIR_NAME;
    public final String PLAYER_DIR_PATH = SETTINGS_DIR_PATH;

    public final String PLAYER_CONFIG_FILE_NAME = "player.config";
    public final String PLAYER_CONFIG_FILE_CLIENT_PATH = PLAYER_DIR_PATH + File.separator + PLAYER_CONFIG_FILE_NAME;

    /*
     * Level files.
     */
    public final String LEVEL_DIR_NAME = "levels";
    public final String LEVEL_DIR_PATH = MAIN_DIR + File.separator + LEVEL_DIR_NAME;

    public final String LEVEL_CONFIG_FILE_NAME = "levels.config";
    public final String LEVEL_CONFIG_FILE_LOCAL_PATH = TextHandler.class.getResource(JAR_CONFIG_DIR + LEVEL_CONFIG_FILE_NAME).getPath();
    public final String LEVEL_CONFIG_FILE_CLIENT_PATH = LEVEL_DIR_PATH + File.separator + LEVEL_CONFIG_FILE_NAME;

    public final String LEVEL_FILE_NAME_MARS = "mars_level.config";
    public final String LEVEL_FILE_NAME_EARTH = "earth_level.config";
    public final String LEVEL_FILE_NAME_NEPTUNE = "neptune_level.config";
    public final String LEVEL_FILE_NAME_VENUS = "venus_level.config";
    public final String LEVEL_FILE_NAME_JUPITER = "jupiter_level.config";

    /* Level file local paths. */
    public final String LEVEL_FILE_LOCAL_PATH_MARS = TextHandler.class.getResource(JAR_CONFIG_DIR + LEVEL_FILE_NAME_MARS).getPath();
    public final String LEVEL_FILE_LOCAL_PATH_EARTH = TextHandler.class.getResource(JAR_CONFIG_DIR + LEVEL_FILE_NAME_EARTH).getPath();
    public final String LEVEL_FILE_LOCAL_PATH_NEPTUNE = TextHandler.class.getResource(JAR_CONFIG_DIR + LEVEL_FILE_NAME_NEPTUNE).getPath();
    public final String LEVEL_FILE_LOCAL_PATH_VENUS = TextHandler.class.getResource(JAR_CONFIG_DIR + LEVEL_FILE_NAME_VENUS).getPath();
    public final String LEVEL_FILE_LOCAL_PATH_JUPITER = FileHandler.class.getResource(JAR_CONFIG_DIR + LEVEL_FILE_NAME_JUPITER).getPath();

    /* Level file client paths. */
    public final String LEVEL_FILE_CLIENT_PATH_MARS = LEVEL_DIR_PATH + File.separator + LEVEL_FILE_NAME_MARS;
    public final String LEVEL_FILE_CLIENT_PATH_EARTH = LEVEL_DIR_PATH + File.separator + LEVEL_FILE_NAME_EARTH;
    public final String LEVEL_FILE_CLIENT_PATH_NEPTUNE = LEVEL_DIR_PATH + File.separator + LEVEL_FILE_NAME_NEPTUNE;
    public final String LEVEL_FILE_CLIENT_PATH_VENUS = LEVEL_DIR_PATH + File.separator + LEVEL_FILE_NAME_VENUS;
    public final String LEVEL_FILE_CLIENT_PATH_JUPITER = LEVEL_DIR_PATH + File.separator + LEVEL_FILE_NAME_JUPITER;

    /*
     * Level related messages.
     */
    private final String ERR_PREFIX = "[ERROR]: ";

    public final String vChangedLevelMsg(String prevLevel, String newLevel) {
        return "Changed level from '" + prevLevel + "' to '" + newLevel + "'";
    }

    public final String errParsingProperties(String fileName, String errMsg) {
        return ERR_PREFIX + "Failed parsing properties from file '" + fileName + "' cause '" + errMsg + "'";
    }

    public final String vLevelFinishedMsg(String levelType) {
        return "Level '" + levelType + "' finished";
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
        return "Ball collision with BlockList[" + blockListIndex + "]";
    }

    /*
     * Audio related messages.
     */

    public final String AUDIO_CONFIG_DIR_LOCAL_PATH = "/audio/";

    public final String AUDIO_FILE_NAME_MENU = "arpanauts.wav";
    public final String AUDIO_FILE_NAME_PLAY = "digital.wav";
    public final String AUDIO_FILE_NAME_PAUSE = "prologue.wav";
    public final String AUDIO_FILE_NAME_BALL_BOUNCE = "ball_bounce.wav";
    public final String AUDIO_FILE_NAME_BALL_RESET = "reset_ball.wav";

    /* Audio file paths. */
    public final String AUDIO_FILE_PATH_MENU = TextHandler.class.getResource(AUDIO_CONFIG_DIR_LOCAL_PATH + AUDIO_FILE_NAME_MENU).getPath();
    public final String AUDIO_FILE_PATH_PLAY = TextHandler.class.getResource(AUDIO_CONFIG_DIR_LOCAL_PATH + AUDIO_FILE_NAME_PLAY).getPath();
    public final String AUDIO_FILE_PATH_PAUSE = TextHandler.class.getResource(AUDIO_CONFIG_DIR_LOCAL_PATH + AUDIO_FILE_NAME_PAUSE).getPath();
    public final String AUDIO_FILE_PATH_BALL_BOUNCE = TextHandler.class.getResource(AUDIO_CONFIG_DIR_LOCAL_PATH + AUDIO_FILE_NAME_BALL_BOUNCE).getPath();
    public final String AUDIO_FILE_PATH_BALL_RESET = TextHandler.class.getResource(AUDIO_CONFIG_DIR_LOCAL_PATH + AUDIO_FILE_NAME_BALL_RESET).getPath();

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
    public final String successCopiedFile(String srcFilePath, String destFilePath) {
        return "Successfully copied '" + srcFilePath + "' to '" + destFilePath + "'";
    }

    public final String successReadProperties(String filePath) {
        return "Finished reading properties from '" + filePath + "'";
    }

    public final String successReadLines(String filePath) {
        return "Finished reading lines from '" + filePath + "'";
    }

    public final String successReadProperty(String key, String value, String fileName) {
        return "Successfully read property '" + key + ":" + value + "' from '" + fileName + "'";
    }

    public final String successReadLine(String line, String fileName) {
        return "Successfully read line '" + line + "' from '" + fileName + "'";
    }

    public final String successCreatedDir(String dirPath) {
        return "Successfully created directory '" + dirPath + "'";
    }

    public final String successCreatedFile(String filePath) {
        return "Successfully created file '" + filePath + "'";
    }

    public final String errCreatedDir(String dirPath) {
        return "Failed to create directory '" + dirPath + "'";
    }

    public final String performanceMsg(int frames, int updates, double allocatedRam) {
        DecimalFormat df = new DecimalFormat("000");
        return "UPS: " + updates + " - FPS: " + frames + " - RAM: " + df.format(allocatedRam) + "MB";
    }

    private final String getDateTime() {
        return new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
    }

}