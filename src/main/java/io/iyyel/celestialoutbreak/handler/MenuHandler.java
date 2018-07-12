package io.iyyel.celestialoutbreak.handler;

public final class MenuHandler {

    /*
     * MenuHandler singleton instance.
     */
    private static MenuHandler instance;

    /*
     * Static block
     */
    static {
        try {
            instance = new MenuHandler();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    /*
     * Private constructor.
     */
    private MenuHandler() {

    }

    /*
     * Getter for the Singleton instance.
     */
    public static synchronized MenuHandler getInstance() {
        return instance;
    }

}