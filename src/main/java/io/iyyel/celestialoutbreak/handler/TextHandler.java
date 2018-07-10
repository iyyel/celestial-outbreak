package io.iyyel.celestialoutbreak.handler;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class TextHandler {

    /* Singleton TextHandler instance. */
    private static TextHandler instance;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    /*
     * Private constructor so that TextHandler can't
     * be instantiated from the outside.
     */
    private TextHandler() {

    }

    /*
     * Instantiating the instance object
     * in a static block.
     */
    static {
        try {
            instance = new TextHandler();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    /*
     * Getter method for the Singleton instance.
     */
    public synchronized static TextHandler getInstance() {
        return instance;
    }


    private final String fs = File.separator;

    /*
     * Important gameController strings.
     */
    public final String GAME_TITLE = "Celestial Outbreak";
    public final String GAME_VERSION = "v0.12a";
    public final String AUTHOR_WEBSITE = "iyyel.io";

    /*
     * Menu button names.
     */
    public final String BTN_START_TEXT = "START";
    public final String BTN_PLAY_TEXT = "PLAY";
    public final String BTN_SCORES_TEXT = "SCORES";
    public final String BTN_SETTINGS_TEXT = "SETTINGS";
    public final String BTN_CONTROLS_TEXT = "CONTROLS";
    public final String BTN_ABOUT_TEXT = "ABOUT";
    public final String BTN_EXIT_TEXT = "EXIT";
    public final String BTN_PLAYER_TEXT = "PLAYER";
    public final String BTN_CONFIGURATION_TEXT = "CONFIGURATION";
    public final String BTN_SELECT_TEXT = "SELECT";
    public final String BTN_NEW_TEXT = "NEW";
    public final String BTN_UPDATE_TEXT = "UPDATE";
    public final String BTN_DELETE_TEXT = "DELETE";

    public final String BTN_CONTROL_OK = "z";
    public final String BTN_CONTROL_CANCEL = "x";
    public final String BTN_CONTROL_USE = "Space";
    public final String BTN_CONTROL_PAUSE = "p";

    /*
     * Log announcement messages.
     */
    public final String START_NEW_APP_INSTANCE = "New " + GAME_TITLE + " " + GAME_VERSION +
            " instance started at " + getDateTime() + " on " + System.getProperty("os.name") + ".";

    public final String SUCCESS_NEW_APP_INSTANCE = "Finished " + GAME_TITLE + " " + GAME_VERSION +
            " initialization at " + getDateTime() + " on " + System.getProperty("os.name") + ".";

    /*
     * Client local gameController directory.
     */
    public final String GAME_DIR_NAME = "." + GAME_TITLE.toLowerCase().replaceAll("\\s+", "");
    public final String GAME_DIR_PATH = System.getProperty("user.home") + fs + GAME_DIR_NAME;

    /*
     * Local configuration directories.
     */
    public final String LOCAL_JAR_CONFIG_DIR = "/config";


    /*
     * Log file information.
     */
    public final String LOG_DIR_NAME = "log";
    public final String LOG_DIR_PATH = GAME_DIR_PATH + fs + LOG_DIR_NAME;

    public final String LOG_FILE_NAME = new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + "_log.txt";
    public final String LOG_FILE_PATH = LOG_DIR_PATH + fs + LOG_FILE_NAME;

    public final String logMsgPrefix() {
        return "[LOG " + dateFormat.format(new Date()) + "]: ";
    }

    /*
     * Settings file information.
     */
    public final String SETTINGS_DIR_NAME = "settings";
    public final String SETTINGS_DIR_PATH = GAME_DIR_PATH + fs + SETTINGS_DIR_NAME;
    public final String LOCAL_JAR_SETTINGS_DIR = LOCAL_JAR_CONFIG_DIR + fs + SETTINGS_DIR_NAME;

    public final String SETTINGS_CONFIG_FILE_NAME = "settings.conf";
    public final String SETTINGS_CONFIG_FILE_LOCAL_PATH = TextHandler.class.getResource(LOCAL_JAR_SETTINGS_DIR + fs + SETTINGS_CONFIG_FILE_NAME).getPath();
    public final String SETTINGS_CONFIG_FILE_CLIENT_PATH = SETTINGS_DIR_PATH + fs + SETTINGS_CONFIG_FILE_NAME;

    /*
     * Player binary file information.
     */
    public final String PLAYER_DIR_NAME = "player";
    public final String PLAYER_DIR_PATH = GAME_DIR_PATH + fs + PLAYER_DIR_NAME;

    public final String PLAYER_BIN_FILE_NAME = "player.bin";
    public final String PLAYER_BIN_FILE_CLIENT_PATH = PLAYER_DIR_PATH + fs + PLAYER_BIN_FILE_NAME;

    /*
     * Score binary file information.
     */
    public final String SCORE_DIR_NAME = "score";
    public final String SCORE_DIR_PATH = GAME_DIR_PATH + fs + SCORE_DIR_NAME;

    public final String SCORE_BIN_FILE_NAME = "score.bin";
    public final String SCORE_BIN_FILE_CLIENT_PATH = SCORE_DIR_PATH + fs + SCORE_BIN_FILE_NAME;

    /*
     * Local font file information.
     */
    public final String GAME_FONT_NAME = "neuropol.ttf";
    public final String GAME_FONT_DIR_LOCAL_PATH = fs + "fonts" + fs;
    public final String GAME_FONT_LOCAL_PATH = TextHandler.class.getResource(GAME_FONT_DIR_LOCAL_PATH + GAME_FONT_NAME).getPath();

    /*
     * Level file information.
     */
    public final String LEVEL_DIR_NAME = "level";
    public final String LEVEL_DIR_PATH = GAME_DIR_PATH + fs + LEVEL_DIR_NAME;
    public final String LOCAL_JAR_LEVEL_DIR = LOCAL_JAR_CONFIG_DIR + fs + LEVEL_DIR_NAME;

    public final String LEVEL_CONFIG_FILE_NAME = "levels.conf";
    public final String LEVEL_CONFIG_FILE_LOCAL_PATH = TextHandler.class.getResource(LOCAL_JAR_LEVEL_DIR + fs + LEVEL_CONFIG_FILE_NAME).getPath();
    public final String LEVEL_CONFIG_FILE_CLIENT_PATH = LEVEL_DIR_PATH + fs + LEVEL_CONFIG_FILE_NAME;

    /* Standard level configuration file names. */
    public final String LEVEL_FILE_NAME_MARS = "mars_level.conf";
    public final String LEVEL_FILE_NAME_EARTH = "earth_level.conf";
    public final String LEVEL_FILE_NAME_NEPTUNE = "neptune_level.conf";
    public final String LEVEL_FILE_NAME_VENUS = "venus_level.conf";
    public final String LEVEL_FILE_NAME_JUPITER = "jupiter_level.conf";

    /* Local level file paths. */
    public final String LEVEL_FILE_LOCAL_PATH_MARS = TextHandler.class.getResource(LOCAL_JAR_LEVEL_DIR + fs + LEVEL_FILE_NAME_MARS).getPath();
    public final String LEVEL_FILE_LOCAL_PATH_EARTH = TextHandler.class.getResource(LOCAL_JAR_LEVEL_DIR + fs + LEVEL_FILE_NAME_EARTH).getPath();
    public final String LEVEL_FILE_LOCAL_PATH_NEPTUNE = TextHandler.class.getResource(LOCAL_JAR_LEVEL_DIR + fs + LEVEL_FILE_NAME_NEPTUNE).getPath();
    public final String LEVEL_FILE_LOCAL_PATH_VENUS = TextHandler.class.getResource(LOCAL_JAR_LEVEL_DIR + fs + LEVEL_FILE_NAME_VENUS).getPath();
    public final String LEVEL_FILE_LOCAL_PATH_JUPITER = TextHandler.class.getResource(LOCAL_JAR_LEVEL_DIR + fs + LEVEL_FILE_NAME_JUPITER).getPath();

    /* Client level file paths. */
    public final String LEVEL_FILE_CLIENT_PATH_MARS = LEVEL_DIR_PATH + fs + LEVEL_FILE_NAME_MARS;
    public final String LEVEL_FILE_CLIENT_PATH_EARTH = LEVEL_DIR_PATH + fs + LEVEL_FILE_NAME_EARTH;
    public final String LEVEL_FILE_CLIENT_PATH_NEPTUNE = LEVEL_DIR_PATH + fs + LEVEL_FILE_NAME_NEPTUNE;
    public final String LEVEL_FILE_CLIENT_PATH_VENUS = LEVEL_DIR_PATH + fs + LEVEL_FILE_NAME_VENUS;
    public final String LEVEL_FILE_CLIENT_PATH_JUPITER = LEVEL_DIR_PATH + fs + LEVEL_FILE_NAME_JUPITER;

    /*
     * Sound file information.
     */
    public final String SOUND_CONFIG_DIR_LOCAL_PATH = fs + "sound";

    public final String SOUND_FILE_NAME_MENU = "menu.wav";
    public final String SOUND_FILE_NAME_PLAY = "play.wav";
    public final String SOUND_FILE_NAME_PAUSE = "pause.wav";
    public final String SOUND_FILE_NAME_BALL_BOUNCE = "ball_bounce.wav";
    public final String SOUND_FILE_NAME_BALL_RESET = "ball_reset.wav";
    public final String SOUND_FILE_NAME_MENU_BTN_SELECTION = "menu_btn_selection.wav";

    /* Local sound file paths. */
    public final String SOUND_FILE_PATH_MENU = TextHandler.class.getResource(SOUND_CONFIG_DIR_LOCAL_PATH + fs + SOUND_FILE_NAME_MENU).getPath();
    public final String SOUND_FILE_PATH_PLAY = TextHandler.class.getResource(SOUND_CONFIG_DIR_LOCAL_PATH + fs + SOUND_FILE_NAME_PLAY).getPath();
    public final String SOUND_FILE_PATH_PAUSE = TextHandler.class.getResource(SOUND_CONFIG_DIR_LOCAL_PATH + fs + SOUND_FILE_NAME_PAUSE).getPath();
    public final String SOUND_FILE_PATH_BALL_BOUNCE = TextHandler.class.getResource(SOUND_CONFIG_DIR_LOCAL_PATH + fs + SOUND_FILE_NAME_BALL_BOUNCE).getPath();
    public final String SOUND_FILE_PATH_BALL_RESET = TextHandler.class.getResource(SOUND_CONFIG_DIR_LOCAL_PATH + fs + SOUND_FILE_NAME_BALL_RESET).getPath();
    public final String SOUND_FILE_PATH_MENU_BTN_SELECTION = TextHandler.class.getResource(SOUND_CONFIG_DIR_LOCAL_PATH + fs + SOUND_FILE_NAME_MENU_BTN_SELECTION).getPath();

    /*
     * Property names.
     */

    /*
     * Utils properties.
     */
    public final String PROP_VERBOSE_LOG_ENABLED = "VERBOSE_LOG_ENABLED";
    public final String PROP_SOUND_ENABLED = "SOUND_ENABLED";
    public final String PROP_GOD_MODE_ENABLED = "GOD_MODE_ENABLED";
    public final String PROP_FIRST_RUN_ENABLED = "FIRST_RUN_ENABLED";
    public final String PROP_FPS_LOCK_ENABLED = "FPS_LOCK_ENABLED";
    public final String PROP_ANTI_ALIASING_ENABLED = "ANTI_ALIASING_ENABLED";

    /*
     * Menu color properties.
     */
    public final String PROP_MENU_FONT_COLOR_HEX = "MenuFontColorHex";
    public final String PROP_MENU_BTN_COLOR_HEX = "MenuBtnColorHex";
    public final String PROP_SELECTED_BTN_COLOR_HEX = "MenuSelectedBtnColorHex";

    /*
     * Level properties.
     */
    public final String PROP_LEVEL_NAME = "LevelName";
    public final String PROP_LEVEL_DESC = "LevelDesc";
    public final String PROP_LEVEL_COLOR_HEX = "LevelColorHex";

    /* Paddle. */
    public final String PROP_PADDLE_POS_X_OFFSET = "LevelPaddlePosXOffset";
    public final String PROP_PADDLE_POS_Y_OFFSET = "LevelPaddlePosYOffset";
    public final String PROP_PADDLE_WIDTH = "LevelPaddleWidth";
    public final String PROP_PADDLE_HEIGHT = "LevelPaddleHeight";
    public final String PROP_PADDLE_SPEED = "LevelPaddleSpeed";
    public final String PROP_PADDLE_COLOR_HEX = "LevelPaddleColorHex";

    /* Ball. */
    public final String PROP_BALL_POS_X_OFFSET = "LevelBallPosXOffset";
    public final String PROP_BALL_POS_Y_OFFSET = "LevelBallPosYOffset";
    public final String PROP_BALL_SIZE = "LevelBallSize";
    public final String PROP_BALL_SPEED = "LevelBallSpeed";
    public final String PROP_BALL_COLOR_HEX = "LevelBallColorHex";

    /* BlockList. */
    public final String PROP_BLOCKLIST_BLOCK_AMOUNT = "LevelBlockListBlockAmount";
    public final String PROP_BLOCKLIST_POS_X = "LevelBlockListXPos";
    public final String PROP_BLOCKLIST_POS_Y = "LevelBlockListYPos";
    public final String PROP_BLOCKLIST_BLOCK_WIDTH = "LevelBlockListBlockWidth";
    public final String PROP_BLOCKLIST_BLOCK_HEIGHT = "LevelBlockListBlockHeight";
    public final String PROP_BLOCKLIST_BLOCK_SPACING = "LevelBlockListBlockSpacing";

    /* GamePanel. */
    public final String PROP_GAME_PANEL_COLOR_HEX = "LevelGamePanelColorHex";

    /*
     * Menu messages.
     */

    /* SCORES_MENU messages. */

    /* CONTROLS_MENU messages. */
    public final String menuControlsMsg01 = "Movement & Navigation:";
    public final String menuControlsMsg02 = "WASD/Arrow keys";
    public final String menuControlsMsg03 = "Confirm/OK:";
    public final String menuControlsMsg04 = "z";
    public final String menuControlsMsg05 = "Cancel/Back:";
    public final String menuControlsMsg06 = "x";
    public final String menuControlsMsg07 = "Select/Use:";
    public final String menuControlsMsg08 = "Space";

    /* SETTINGS_MENU messages. */
    public final String menuSettingsMsg01 = "All of the configuration related files can be found in the";
    public final String menuSettingsMsg02 = "following directory '" + GAME_DIR_PATH + "'";
    public final String menuSettingsMsg03 = "general settings can be found in the settings.settings file,";
    public final String menuSettingsMsg04 = "in the settings directory.";
    public final String menuSettingsMsg05 = "Level specific settings can be found in the various level files";
    public final String menuSettingsMsg06 = "in the levels directory, e.g. mars_level.settings.";
    public final String menuSettingsMsg07 = "Levels are played in order from the levels.settings file.";
    public final String menuSettingsMsg08 = "If creating custom levels, make sure to add them to this file.";
    public final String menuSettingsMsg09 = "If more information is needed, visit: https://goo.gl/JxxeL6";

    /* ABOUT_MENU messages. */


    /* EXIT_MENU messages. */


    /* PAUSE_SCREEN messages. */
    public final String MENU_MSG_PAUSED = "Paused";

    /* NEW_LEVEL messages. */


    /* FINISHED_LEVEL messages. */

    /*
     * Success messages.
     */
    public final String successCopiedFileMsg(String srcFilePath, String destFilePath) {
        return "Successfully copied '" + srcFilePath + "' to '" + destFilePath + "'";
    }

    public final String successReadPropertyMsg(String key, String value, String fileName) {
        return "Successfully read property '" + key + ":" + value + "' from '" + fileName + "'";
    }

    public final String successReadLineMsg(String line, String fileName) {
        return "Successfully read line '" + line + "' from '" + fileName + "'";
    }

    public final String successCreatedFileMsg(String filePath) {
        return "Successfully created file '" + filePath + "'";
    }

    public final String successCreatedDirMsg(String dirPath) {
        return "Successfully created directory '" + dirPath + "'";
    }

    /*
     * Finished messages.
     */
    public final String finishReadPropertiesMsg(String filePath) {
        return "Finished reading properties from '" + filePath + "'";
    }

    public final String finishReadLinesMsg(String filePath) {
        return "Finished reading lines from '" + filePath + "'";
    }

    /*
     * Error messages.
     */
    private final String ERR_PREFIX = "[ERROR] ";

    public final String errorCreatingDirMsg(String dirPath) {
        return ERR_PREFIX + "Failed to create directory '" + dirPath + "'";
    }

    public final String errorCreatingDirMsg(String dirPath, String errMsg) {
        return ERR_PREFIX + "Failed to create directory '" + dirPath + "' cause '" + errMsg + "'";
    }

    public final String errorWritingToFileMsg(String filePath, String errMsg) {
        return ERR_PREFIX + "Failed writing to '" + filePath + "' cause '" + errMsg + "'";
    }

    public final String errorCopyingFileMsg(String srcFilePath, String destFilePath, String errMsg) {
        return ERR_PREFIX + "Failed to copy '" + srcFilePath + "' to '" + destFilePath + "' cause '" + errMsg + "'";
    }

    public final String errorParsingPropertiesMsg(String filePath, String errMsg) {
        return ERR_PREFIX + "Failed parsing properties from file '" + filePath + "' cause '" + errMsg + "'";
    }

    public final String errorCreatingAudioClipMsg(String filePath, String errMsg) {
        return ERR_PREFIX + "Failed to create AudioClip from '" + filePath + "' cause '" + errMsg + "'";
    }

    /*
     * Action messages.
     */
    public final String actionReadingLinesMsg(String filePath) {
        return "Reading lines from '" + filePath + "'...";
    }

    public final String actionReadingPropertiesMsg(String filePath) {
        return "Reading properties from '" + filePath + "'...";
    }

    /*
     * Verbose mode messages.
     */

    /* Level. */
    public final String vChangedLevelMsg(String prevLevel, String newLevel) {
        return "Changed level from '" + prevLevel + "' to '" + newLevel + "'";
    }

    public final String vLevelFinishedMsg(String levelType) {
        return "Level '" + levelType + "' finished";
    }

    /* Ball. */
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

    private final DecimalFormat df = new DecimalFormat("000");

    /* Application. */
    public final String vPerformanceMsg(int frames, int updates, double allocatedRam) {
        return "UPS: " + updates + " - FPS: " + frames + " - RAM: " + df.format(allocatedRam) + "MB";
    }

    /*
     * Private helper methods.
     */
    private final String getDateTime() {
        return new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
    }

}