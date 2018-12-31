package io.iyyel.celestialoutbreak.handler;

import java.awt.*;
import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class TextHandler {

    /* Log date format. */
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    /* Singleton TextHandler instance. */
    private static TextHandler instance;

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

    /*
     * File separators.
     */
    private final String fs = File.separator;
    private final String fs_jar = "/";

    /*
     * Important game strings.
     */
    public final String GAME_TITLE = "Celestial Outbreak";
    public final String GAME_VERSION = "v0.1b";
    public final String AUTHOR_WEBSITE = "iyyel.io";
    public final String GITHUB_URL = "github.com/iyyel/celestialoutbreak";

    public final String USER_HOME = System.getProperty("user.home");
    public final String USER_OS = System.getProperty("os.name");
    public final String USER_JRE_ARCH = System.getProperty("os.arch");

    /*
     * Menu button names.
     */
    public final String BTN_START_TEXT = "START";
    public final String BTN_PLAY_TEXT = "PLAY";
    public final String BTN_SCORES_TEXT = "SCORES";
    public final String BTN_OPTIONS_TEXT = "OPTIONS";
    public final String BTN_CONTROLS_TEXT = "CONTROLS";
    public final String BTN_ABOUT_TEXT = "ABOUT";
    public final String BTN_EXIT_TEXT = "EXIT";
    public final String BTN_GAME_OPTIONS_TEXT = "GAME OPTIONS";
    public final String BTN_PLAYER_OPTIONS_TEXT = "PLAYER OPTIONS";
    public final String BTN_CONFIGURATION_OPTIONS_TEXT = "CONFIGURATION OPTIONS";
    public final String BTN_SELECT_PLAYER_TEXT = "SELECT PLAYER";
    public final String BTN_CREATE_PLAYER_TEXT = "CREATE PLAYER";
    public final String BTN_DELETE_DELETE_TEXT = "DELETE PLAYER";

    public final String BTN_CONTROL_MOV_NAV = "WASD/Arrow keys";
    public final String BTN_CONTROL_FORWARD_OK = "z";
    public final String BTN_CONTROL_BACK_CANCEL = "x";
    public final String BTN_CONTROL_USE = "Space";
    public final String BTN_CONTROL_PAUSE = "p";

    /*
     * Menu title names.
     */
    public final String TITLE_WELCOME_MENU = "WELCOME";
    public final String TITLE_SCORES_MENU = "SCORES";
    public final String TITLE_CONTROLS_MENU = "CONTROLS";
    public final String TITLE_OPTIONS_MENU = "OPTIONS";
    public final String TITLE_ABOUT_SCREEN = "ABOUT";
    public final String TITLE_EXIT_SCREEN = "EXIT";

    public final String TITLE_PLAYER_OPTIONS_MENU = "PLAYER OPTIONS";
    public final String TITLE_CONFIGURATION_OPTIONS_MENU = "CONFIGURATION OPTIONS";

    public final String TITLE_SELECT_PLAYER_SCREEN = "SELECT PLAYER";
    public final String TITLE_CREATE_PLAYER_SCREEN = "CREATE PLAYER";
    public final String TITLE_DELETE_PLAYER_SCREEN = "DELETE PLAYER";

    public final String TITLE_GAME_OPTIONS_SCREEN = "GAME OPTIONS";

    public final String TITLE_PAUSE_SCREEN = "PAUSE";

    /*
     * Log announcement messages.
     */
    public final String GAME_INIT_STARTED = "Started " + GAME_TITLE + " " + GAME_VERSION +
            " initialization at " + getDateTime() + " on " + USER_OS + " with " + USER_JRE_ARCH + " JRE";

    public final String GAME_INIT_FINISHED = "Finished " + GAME_TITLE + " " + GAME_VERSION +
            " initialization at " + getDateTime() + " on " + USER_OS + " with " + USER_JRE_ARCH + " JRE";

    /*
     * Client local gameController directory.
     */
    public final String GAME_TOP_DIR_NAME = "." + AUTHOR_WEBSITE;
    public final String GAME_DIR_NAME = GAME_TITLE.toLowerCase().replaceAll("\\s+", "");
    public final String GAME_DIR_PATH = USER_HOME + fs + GAME_TOP_DIR_NAME + fs + GAME_DIR_NAME;

    /*
     * Local configuration directories.
     */
    public final String LOCAL_JAR_CONFIG_DIR = fs_jar + "config";

    /*
     * Log file information.
     */
    public final String LOG_DIR_NAME = "log";
    public final String LOG_DIR_PATH = GAME_DIR_PATH + fs + LOG_DIR_NAME;

    public final String LOG_FILE_NAME = new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + "_log.txt";
    public final String LOG_FILE_PATH = LOG_DIR_PATH + fs + LOG_FILE_NAME;

    public final String LOG_SEPARATOR = "########################################################################" +
            "#########################################################################";

    public final String logMsgPrefix() {
        return "[LOG " + dateFormat.format(new Date()) + "]: ";
    }

    /*
     * Options file information.
     */
    public final String OPTIONS_DIR_NAME = "options";
    public final String OPTIONS_DIR_CLIENT_PATH = GAME_DIR_PATH + fs + OPTIONS_DIR_NAME;
    public final String OPTIONS_DIR_LOCAL_PATH = LOCAL_JAR_CONFIG_DIR + fs_jar + OPTIONS_DIR_NAME;

    public final String OPTIONS_CONFIG_FILE_NAME = "options.conf";
    public final String OPTIONS_CONFIG_FILE_LOCAL_PATH = getClass().getResource(OPTIONS_DIR_LOCAL_PATH + fs_jar + OPTIONS_CONFIG_FILE_NAME).getPath();
    public final String OPTIONS_CONFIG_FILE_CLIENT_PATH = OPTIONS_DIR_CLIENT_PATH + fs + OPTIONS_CONFIG_FILE_NAME;

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
    public final String FONT_DIR_NAME = "fonts";
    public final String FONT_DIR_PATH = GAME_DIR_PATH + fs + FONT_DIR_NAME;
    public final String LOCAL_JAR_FONT_DIR = LOCAL_JAR_CONFIG_DIR + fs_jar + FONT_DIR_NAME;

    /* gameFont */
    public final String GAME_FONT_FILE_NAME = "neuropol.ttf";
    public final String GAME_FONT_FILE_LOCAL_PATH = getClass().getResource(LOCAL_JAR_FONT_DIR + fs_jar + GAME_FONT_FILE_NAME).getPath();
    public final String GAME_FONT_FILE_CLIENT_PATH = FONT_DIR_PATH + fs + GAME_FONT_FILE_NAME;

    /* panelFont */
    public final String PANEL_FONT_FILE_NAME = "courier.ttf";
    public final String PANEL_FONT_FILE_LOCAL_PATH = getClass().getResource(LOCAL_JAR_FONT_DIR + fs_jar + PANEL_FONT_FILE_NAME).getPath();
    public final String PANEL_FONT_FILE_CLIENT_PATH = FONT_DIR_PATH + fs + PANEL_FONT_FILE_NAME;

    /*
     * Level file information.
     */
    public final String LEVEL_DIR_NAME = "level";
    public final String LEVEL_DIR_PATH = GAME_DIR_PATH + fs + LEVEL_DIR_NAME;
    public final String LOCAL_JAR_LEVEL_DIR = LOCAL_JAR_CONFIG_DIR + fs_jar + LEVEL_DIR_NAME;

    public final String LEVEL_CONFIG_FILE_NAME = "levels.conf";
    public final String LEVEL_CONFIG_FILE_LOCAL_PATH = getClass().getResource(LOCAL_JAR_LEVEL_DIR + fs_jar + LEVEL_CONFIG_FILE_NAME).getPath();
    public final String LEVEL_CONFIG_FILE_CLIENT_PATH = LEVEL_DIR_PATH + fs + LEVEL_CONFIG_FILE_NAME;

    /* Standard level configuration file names. */
    public final String LEVEL_FILE_NAME_MARS = "mars_level.conf";
    public final String LEVEL_FILE_NAME_EARTH = "earth_level.conf";
    public final String LEVEL_FILE_NAME_NEPTUNE = "neptune_level.conf";
    public final String LEVEL_FILE_NAME_VENUS = "venus_level.conf";
    public final String LEVEL_FILE_NAME_JUPITER = "jupiter_level.conf";

    /* Local level file paths. */
    public final String LEVEL_FILE_LOCAL_PATH_MARS = getClass().getResource(LOCAL_JAR_LEVEL_DIR + fs_jar + LEVEL_FILE_NAME_MARS).getPath();
    public final String LEVEL_FILE_LOCAL_PATH_EARTH = getClass().getResource(LOCAL_JAR_LEVEL_DIR + fs_jar + LEVEL_FILE_NAME_EARTH).getPath();
    public final String LEVEL_FILE_LOCAL_PATH_NEPTUNE = getClass().getResource(LOCAL_JAR_LEVEL_DIR + fs_jar + LEVEL_FILE_NAME_NEPTUNE).getPath();
    public final String LEVEL_FILE_LOCAL_PATH_VENUS = getClass().getResource(LOCAL_JAR_LEVEL_DIR + fs_jar + LEVEL_FILE_NAME_VENUS).getPath();
    public final String LEVEL_FILE_LOCAL_PATH_JUPITER = getClass().getResource(LOCAL_JAR_LEVEL_DIR + fs_jar + LEVEL_FILE_NAME_JUPITER).getPath();

    /* Client level file paths. */
    public final String LEVEL_FILE_CLIENT_PATH_MARS = LEVEL_DIR_PATH + fs + LEVEL_FILE_NAME_MARS;
    public final String LEVEL_FILE_CLIENT_PATH_EARTH = LEVEL_DIR_PATH + fs + LEVEL_FILE_NAME_EARTH;
    public final String LEVEL_FILE_CLIENT_PATH_NEPTUNE = LEVEL_DIR_PATH + fs + LEVEL_FILE_NAME_NEPTUNE;
    public final String LEVEL_FILE_CLIENT_PATH_VENUS = LEVEL_DIR_PATH + fs + LEVEL_FILE_NAME_VENUS;
    public final String LEVEL_FILE_CLIENT_PATH_JUPITER = LEVEL_DIR_PATH + fs + LEVEL_FILE_NAME_JUPITER;

    /*
     * Sound file information.
     */
    public final String SOUND_DIR_NAME = "sound";
    public final String SOUND_DIR_PATH = GAME_DIR_PATH + fs + SOUND_DIR_NAME;
    public final String LOCAL_JAR_SOUND_DIR = LOCAL_JAR_CONFIG_DIR + fs_jar + SOUND_DIR_NAME;

    public final String SOUND_FILE_NAME_MENU = "menu.wav";               // final
    public final String SOUND_FILE_NAME_PLAY = "play.wav";               // not sure
    public final String SOUND_FILE_NAME_PAUSE = "pause.wav";             // not sure
    public final String SOUND_FILE_NAME_BALL_HIT = "ball_hit.wav";
    public final String SOUND_FILE_NAME_BALL_RESET = "ball_reset.wav";
    public final String SOUND_FILE_NAME_MENU_BTN_NAV = "menu_nav.wav";
    public final String SOUND_FILE_NAME_MENU_BTN_USE = "menu_use.wav";
    public final String SOUND_FILE_NAME_BAD_ACTION = "bad_action.wav";

    /* Local sound file paths. */
    public final String SOUND_FILE_LOCAL_PATH_MENU = getClass().getResource(LOCAL_JAR_SOUND_DIR + fs_jar + SOUND_FILE_NAME_MENU).getPath();
    public final String SOUND_FILE_LOCAL_PATH_PLAY = getClass().getResource(LOCAL_JAR_SOUND_DIR + fs_jar + SOUND_FILE_NAME_PLAY).getPath();
    public final String SOUND_FILE_LOCAL_PATH_PAUSE = getClass().getResource(LOCAL_JAR_SOUND_DIR + fs_jar + SOUND_FILE_NAME_PAUSE).getPath();
    public final String SOUND_FILE_LOCAL_PATH_BALL_HIT = getClass().getResource(LOCAL_JAR_SOUND_DIR + fs_jar + SOUND_FILE_NAME_BALL_HIT).getPath();
    public final String SOUND_FILE_LOCAL_PATH_BALL_RESET = getClass().getResource(LOCAL_JAR_SOUND_DIR + fs_jar + SOUND_FILE_NAME_BALL_RESET).getPath();
    public final String SOUND_FILE_LOCAL_PATH_MENU_BTN_NAV = getClass().getResource(LOCAL_JAR_SOUND_DIR + fs_jar + SOUND_FILE_NAME_MENU_BTN_NAV).getPath();
    public final String SOUND_FILE_LOCAL_PATH_MENU_BTN_USE = getClass().getResource(LOCAL_JAR_SOUND_DIR + fs_jar + SOUND_FILE_NAME_MENU_BTN_USE).getPath();
    public final String SOUND_FILE_LOCAL_PATH_BAD_ACTION = getClass().getResource(LOCAL_JAR_SOUND_DIR + fs_jar + SOUND_FILE_NAME_BAD_ACTION).getPath();

    /* Client sound file paths. */
    public final String SOUND_FILE_CLIENT_PATH_MENU = SOUND_DIR_PATH + fs + SOUND_FILE_NAME_MENU;
    public final String SOUND_FILE_CLIENT_PATH_PLAY = SOUND_DIR_PATH + fs + SOUND_FILE_NAME_PLAY;
    public final String SOUND_FILE_CLIENT_PATH_PAUSE = SOUND_DIR_PATH + fs + SOUND_FILE_NAME_PAUSE;
    public final String SOUND_FILE_CLIENT_PATH_BALL_HIT = SOUND_DIR_PATH + fs + SOUND_FILE_NAME_BALL_HIT;
    public final String SOUND_FILE_CLIENT_PATH_BALL_RESET = SOUND_DIR_PATH + fs + SOUND_FILE_NAME_BALL_RESET;
    public final String SOUND_FILE_CLIENT_PATH_MENU_BTN_NAV = SOUND_DIR_PATH + fs + SOUND_FILE_NAME_MENU_BTN_NAV;
    public final String SOUND_FILE_CLIENT_PATH_MENU_BTN_USE = SOUND_DIR_PATH + fs + SOUND_FILE_NAME_MENU_BTN_USE;
    public final String SOUND_FILE_CLIENT_PATH_BAD_ACTION = SOUND_DIR_PATH + fs + SOUND_FILE_NAME_BAD_ACTION;

    /*
     * README.txt file information.
     */
    public final String README_FILE_NAME = "README.txt";
    public final String README_FILE_DIR_PATH = GAME_DIR_PATH;
    public final String LOCAL_JAR_README_FILE_DIR = LOCAL_JAR_CONFIG_DIR;
    public final String README_FILE_LOCAL_PATH = getClass().getResource(LOCAL_JAR_README_FILE_DIR + fs_jar + README_FILE_NAME).getPath();
    public final String README_FILE_CLIENT_PATH = README_FILE_DIR_PATH + fs + README_FILE_NAME;

    /*
     * Property names.
     */

    /*
     * Utils properties.
     */
    public final String PROP_VERBOSE_LOG_ENABLED = "VERBOSE_LOG_ENABLED";
    public final String PROP_SOUND_ENABLED = "SOUND_ENABLED";
    public final String PROP_GOD_MODE_ENABLED = "GOD_MODE_ENABLED";
    public final String PROP_FPS_LOCK_ENABLED = "FPS_LOCK_ENABLED";
    public final String PROP_ANTI_ALIASING_ENABLED = "ANTI_ALIASING_ENABLED";

    /*
     * Menu color properties.
     */
    public final String PROP_MENU_FONT_COLOR_HEX = "MENU_FONT_COLOR_HEX";
    public final String PROP_MENU_BTN_COLOR_HEX = "MENU_BTN_COLOR_HEX";
    public final String PROP_MENU_BTN_SELECTED_COLOR_HEX = "MENU_BTN_SELECTED_COLOR_HEX";
    public final String PROP_MENU_BTN_PLAYER_DELETED_COLOR_HEX = "MENU_BTN_PLAYER_DELETED_COLOR_HEX";
    public final String PROP_MENU_BTN_PLAYER_SELECTED_COLOR_HEX = "MENU_BTN_PLAYER_SELECTED_COLOR_HEX";

    public String getMenuColorLogString(String pKey, Color color) {
        return pKey + ": (" + color.getRed() + ", " + color.getGreen() + ", " + color.getBlue() + ")";
    }

    public String getGamePropertyLogString(boolean prop, String prefix) {
        if (prop) {
            return prefix + " enabled.";
        }
        return prefix + " disabled.";
    }

    /*
     * Level properties.
     */
    public final String PROP_LEVEL_NAME = "LEVEL_NAME";
    public final String PROP_LEVEL_DESC = "LEVEL_DESCRIPTION";
    public final String PROP_LEVEL_COLOR_HEX = "LEVEL_COLOR_HEX";

    /* Paddle. */
    public final String PROP_PADDLE_POS_X_OFFSET = "LEVEL_PADDLE_POS_X_OFFSET";
    public final String PROP_PADDLE_POS_Y_OFFSET = "LEVEL_PADDLE_POS_Y_OFFSET";
    public final String PROP_PADDLE_WIDTH = "LEVEL_PADDLE_WIDTH";
    public final String PROP_PADDLE_HEIGHT = "LEVEL_PADDLE_HEIGHT";
    public final String PROP_PADDLE_SPEED = "LEVEL_PADDLE_SPEED";
    public final String PROP_PADDLE_COLOR_HEX = "LEVEL_PADDLE_COLOR_HEX";

    /* Ball. */
    public final String PROP_BALL_POS_X_OFFSET = "LEVEL_BALL_POS_X_OFFSET";
    public final String PROP_BALL_POS_Y_OFFSET = "LEVEL_BALL_POS_Y_OFFSET";
    public final String PROP_BALL_SIZE = "LEVEL_BALL_SIZE";
    public final String PROP_BALL_SPEED = "LEVEL_BALL_SPEED";
    public final String PROP_BALL_COLOR_HEX = "LEVEL_BALL_COLOR_HEX";

    /* BlockList. */
    public final String PROP_BLOCKLIST_POS_X = "LEVEL_BLOCKLIST_X_POS";
    public final String PROP_BLOCKLIST_POS_Y = "LEVEL_BLOCKLIST_Y_POS";
    public final String PROP_BLOCKLIST_BLOCK_HITPOINTS = "LEVEL_BLOCKLIST_BLOCK_HITPOINTS";
    public final String PROP_BLOCKLIST_BLOCK_AMOUNT = "LEVEL_BLOCKLIST_BLOCK_AMOUNT";
    public final String PROP_BLOCKLIST_BLOCK_WIDTH = "LEVEL_BLOCKLIST_BLOCK_WIDTH";
    public final String PROP_BLOCKLIST_BLOCK_HEIGHT = "LEVEL_BLOCKLIST_BLOCK_HEIGHT";
    public final String PROP_BLOCKLIST_BLOCK_SPACING = "LEVEL_BLOCKLIST_BLOCK_SPACING";

    /* GamePanel. */
    public final String PROP_GAME_PANEL_COLOR_HEX = "LEVEL_GAME_PANEL_COLOR_HEX";

    /*
     * Menu messages.
     */

    /* SCORES_MENU messages. */

    /* CONTROLS_MENU messages. */
    public final String menuControlsMsg01 = "Movement/Navigation:";
    public final String menuControlsMsg02 = BTN_CONTROL_MOV_NAV;
    public final String menuControlsMsg03 = "Forward/OK:";
    public final String menuControlsMsg04 = BTN_CONTROL_FORWARD_OK;
    public final String menuControlsMsg05 = "Back/Cancel:";
    public final String menuControlsMsg06 = BTN_CONTROL_BACK_CANCEL;
    public final String menuControlsMsg07 = "Use:";
    public final String menuControlsMsg08 = BTN_CONTROL_USE;
    public final String menuControlsMsg09 = "Pause:";
    public final String menuControlsMsg10 = BTN_CONTROL_PAUSE;

    /* OPTIONS messages. */
    public final String menuConfigMsg01 = "All of the configuration related files can be found in the";
    public final String menuConfigMsg02 = GAME_TOP_DIR_NAME + fs + GAME_DIR_NAME + " directory in the OS home directory.";
    public final String menuConfigMsg03 = "General options can be found in the " + OPTIONS_CONFIG_FILE_NAME + " file";
    public final String menuConfigMsg04 = "in the " + OPTIONS_DIR_NAME + " directory.";
    public final String menuConfigMsg05 = "Level specific options can be found in the various level files";
    public final String menuConfigMsg06 = "in the " + LEVEL_DIR_NAME + " directory, e.g. " + LEVEL_FILE_NAME_MARS + ".";
    public final String menuConfigMsg07 = "Levels are played in order from the " + LEVEL_CONFIG_FILE_NAME + " file.";
    public final String menuConfigMsg08 = "If creating custom levels, make sure to add them to this file.";
    public final String menuConfigMsg09 = "If more information is needed, please visit";
    public final String menuConfigMsg10 = GITHUB_URL + ".";

    /* ABOUT_MENU messages. */
    public final String aboutMenuMsg01 = "Celestial Outbreak is an open source, Breakout-like game with";
    public final String aboutMenuMsg02 = "focus on game customization.";
    public final String aboutMenuMsg03 = "The game is a software development side project and the";
    public final String aboutMenuMsg04 = "latest version of the game can be seen and downloaded at";
    public final String aboutMenuMsg05 = GITHUB_URL + ".";
    public final String aboutMenuMsg06 = "The game plays like your typical Breakout game, with a few";
    public final String aboutMenuMsg07 = "additions such as power ups, custom levels and player scores.";
    public final String aboutMenuMsg08 = "If any help is needed, please look at the " + TITLE_CONTROLS_MENU + " and";
    public final String aboutMenuMsg09 = TITLE_CONFIGURATION_OPTIONS_MENU + " menus for more information.";

    /* EXIT_MENU messages. */


    /* PAUSE messages. */
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
        return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
    }

}