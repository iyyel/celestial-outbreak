package io.inabsentia.celestialoutbreak.utils;

import java.awt.*;
import java.util.Random;

public class Utils {

    private static final Utils instance = new Utils();
    private final Random random;

    // Game flags
    public final boolean DEV_ENABLED = true;
    public final boolean SOUND_ENABLED = true;

    private Utils() {
        random = new Random();
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

    public static synchronized Utils getInstance() {
        return instance;
    }

}