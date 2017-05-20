package io.inabsentia.celestialoutbreak.utils;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Utils {

    private static final Utils instance = new Utils();
    private final Random random;

    /* Game flags */
    public final boolean DEV_ENABLED = true;
    public final boolean SOUND_ENABLED = true;

    private Utils() {
        random = new Random();

        if (DEV_ENABLED) {
            logMessage("########### NEW APPLICATION INSTANCE AT " + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()) + " ###########");
        }
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

    public void logMessage(String message) {
        String messagePrefix = "[DEV-LOG " + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "]: ";
        message = messagePrefix + message;

        System.err.println(message);

        // Terrible way to use the path. However, it works for now. o_o..
        String fileName = "./log/" + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + "_dev-log.txt";
        File file = new File(fileName);

        if (file.exists() && !file.isDirectory()) {
            try (PrintWriter out = new PrintWriter(new FileOutputStream(new File(fileName), true))) {
                out.append(message + "\n");
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            try (PrintWriter out = new PrintWriter(fileName)) {
                out.println(message);
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static synchronized Utils getInstance() {
        return instance;
    }

}