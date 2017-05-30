package io.inabsentia.celestialoutbreak.utils;

import io.inabsentia.celestialoutbreak.handler.TextHandler;

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

    private final TextHandler textHandler = TextHandler.getInstance();
    private final Random random = new Random();

    /* Game flags */
    public final boolean VERBOSE_ENABLED = true;
    public final boolean SOUND_ENABLED = false;

    private Utils() {
        if (VERBOSE_ENABLED) logMessage("########### NEW APPLICATION INSTANCE AT " + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()) + " ###########");
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

    public void logMessage(String message) {
        message = textHandler.LOG_MESSAGE_PREFIX + message;

        System.err.println(message);

        File logDir = new File(textHandler.LOG_DIR);

        if (!logDir.exists()) {
            try {
                logDir.mkdirs();
            } catch (SecurityException se) {
                se.printStackTrace();
            }
        }

        String fileName = textHandler.LOG_DIR + File.separator + textHandler.LOG_FILE_NAME;
        File file = new File(fileName);

        if (file.exists() && !file.isDirectory()) {
            try (PrintWriter out = new PrintWriter(new FileOutputStream(new File(fileName), true))) {
                out.append(message + "\r\n");
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            try (PrintWriter out = new PrintWriter(fileName)) {
                out.print(message + "\r\n");
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

}