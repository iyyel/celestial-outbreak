package io.inabsentia.celestialoutbreak.game.handler;

/**
 * Singleton class.
 */
public class TextHandler {

    private static final TextHandler instance = new TextHandler();

    public final String TITLE = "Celestial Outbreak";
    public final String VERSION = "v0.01a";
    public final String EMAIL = "inabsentia.io";

    private TextHandler() {

    }

    public final String playBtn = "PLAY";
    public final String scoreBtn = "SCORES";
    public final String settingsBtn = "SETTINGS";
    public final String aboutBtn = "ABOUT";
    public final String quitBtn = "QUIT";

    public final String pauseMsg = "Paused";
    public final String pauseStartMsg = "Press \"p\" to continue the game!";

    public final String spacing = "     ";

    public String bottomPanelString(String levelName, int playerLives, int playerScore, int blocksLeft) {
        return "Level: " + levelName + spacing + spacing + "Lives: " + playerLives + spacing + "Score: " + playerScore + spacing + "Blocks: " + blocksLeft;
    }

    public synchronized static TextHandler getInstance() {
        return instance;
    }

}