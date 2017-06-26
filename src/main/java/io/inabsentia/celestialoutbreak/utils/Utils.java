package io.inabsentia.celestialoutbreak.utils;

import java.awt.*;
import java.util.Random;

public class Utils {

    private static final Utils instance = new Utils();

    private final Random random = new Random();

    /* Game flags */
    private boolean isVerboseEnabled = true;
    private boolean isSoundEnabled = true;
    private boolean isGodModeEnabled = false;

    private Utils() {

    }

    public static synchronized Utils getInstance() {
        return instance;
    }

    public Color generatePastelColor(final float luminance, final float sat) {
        final float hue = random.nextFloat();
        final float saturation = (random.nextInt(2000) + 1000) / sat;
        final Color color = Color.getHSBColor(hue, saturation, luminance);
        return color;
    }

    public void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean isVerboseEnabled() {
        return isVerboseEnabled;
    }

    public void setVerboseEnabled(boolean verboseEnabled) {
        this.isVerboseEnabled = verboseEnabled;
    }

    public boolean isSoundEnabled() {
        return isSoundEnabled;
    }

    public void setSoundEnabled(boolean soundEnabled) {
        this.isSoundEnabled = soundEnabled;
    }

    public boolean isGodModeEnabled() {
        return isGodModeEnabled;
    }

    public void setGodModeEnabled(boolean isGodModeEnabled) {
        this.isGodModeEnabled = isGodModeEnabled;
    }

}