package io.inabsentia.celestialoutbreak.handler;

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

    public synchronized static TextHandler getInstance() {
        return instance;
    }

    public final String playBtn = "PLAY";
    public final String scoreBtn = "SCORES";
    public final String langBtn = "LANGUAGE";
    public final String aboutBtn = "ABOUT";
    public final String quitBtn = "QUIT";

    public final String pauseMsg = "Paused";
    public final String pauseStartMsg = "Press \"p\" to start the game!";

    public String blocksLeft(int blocksLeft) {
        return "Blocks: " + blocksLeft;
    }

}