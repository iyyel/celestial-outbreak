package io.iyyel.celestialoutbreak.handler;

import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.*;
import java.util.Properties;

public final class LogHandler {

    private final TextHandler textHandler = TextHandler.getInstance();

    private static LogHandler instance;

    private boolean isVerboseLogEnabled;

    public enum LogLevel {
        ERROR,
        FAIL,
        INFORMATION
    }

    private LogHandler() {
        createLogDir();
        isVerboseLogEnabled = readIsVerboseLogEnabled();
    }

    static {
        try {
            instance = new LogHandler();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static synchronized LogHandler getInstance() {
        return instance;
    }


    public void log(String msg, LogLevel logLevel, boolean isVerboseLog) {
        if (this.isVerboseLogEnabled != isVerboseLog) {
            return;
        }

        msg = textHandler.logMsgPrefix(logLevel) + msg;
        System.out.println(msg);
        writeToLogFile(msg);
    }

    private void writeToLogFile(String msg) {
        String filePath = textHandler.LOG_FILE_PATH;
        File file = new File(filePath);

        if (file.isDirectory()) {
            return;
        }

        try {
            if (file.exists()) {
                try (PrintWriter out = new PrintWriter(new FileOutputStream(file, true))) {
                    out.append(msg).append("\r\n");
                }
            } else {
                try (PrintWriter out = new PrintWriter(filePath)) {
                    out.print(msg + "\r\n");
                }
                log(textHandler.successCreatedFileMsg(filePath), LogHandler.LogLevel.INFORMATION, false);
            }
        } catch (IOException e) {
            log(textHandler.errorWritingToFileMsg(filePath, ExceptionUtils.getStackTrace(e)), LogHandler.LogLevel.ERROR, false);
        }
    }

    private void createLogDir() {
        String dirPath = textHandler.LOG_DIR_PATH;
        File dir = new File(dirPath);
        if (!dir.exists()) {
            try {
                boolean result = dir.mkdirs();
                if (result) {
                    log(textHandler.successCreatedDirMsg(dirPath), LogHandler.LogLevel.INFORMATION, false);
                } else {
                    log(textHandler.errorCreatingDirMsg(dirPath), LogHandler.LogLevel.FAIL, false);
                }
            } catch (SecurityException e) {
                log(textHandler.errorCreatingDirMsg(dirPath, ExceptionUtils.getStackTrace(e)), LogHandler.LogLevel.ERROR, false);
            }
        }
    }

    private boolean readIsVerboseLogEnabled() {
        String filePathClient = textHandler.OPTIONS_CONFIG_FILE_CLIENT_PATH;
        String filePathLocal = getClass().getResource(textHandler.OPTIONS_CONFIG_FILE_LOCAL_PATH).getPath();
        String pKey = textHandler.PROP_KEY_VERBOSE_LOG_ENABLED;

        try {
            isVerboseLogEnabled = readProperty(pKey, filePathClient);
        } catch (IOException e) {
            try {
                isVerboseLogEnabled = readProperty(pKey, filePathLocal);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return false;
    }

    private boolean readProperty(String pKey, String filePath) throws IOException {
        Properties p = new Properties();
        String value = "";

        try (InputStream is = new FileInputStream(filePath)) {
            p.load(is);

            for (String key : p.stringPropertyNames()) {
                if (!key.equals(pKey)) {
                    continue;
                }

                value = p.getProperty(key);
                value = removeComments(value);

                if (value != null) {
                    log(textHandler.successReadPropertyMsg(pKey, value, filePath), LogHandler.LogLevel.INFORMATION, false);
                }
            }

        } catch (IOException e) {
            throw new IOException(e);
        }

        return Boolean.valueOf(value);
    }


    /*
     * Remove comments from String line.
     */
    private String removeComments(String line) {
        if (line.trim().length() == 0 || line.trim().charAt(0) == '#') {
            return null;
        }

        String reversed = new StringBuilder(line).reverse().toString();

        int commentIndex = reversed.indexOf('#');

        if (commentIndex == 0) {
            return null;
        }

        if (commentIndex == -1) {
            return line;
        }

        reversed = reversed.substring(commentIndex + 1);

        int firstAlphabeticIndex = -1;

        for (int i = 0; i < reversed.length(); i++) {
            char c = reversed.charAt(i);
            if (!Character.isWhitespace(c)) {
                firstAlphabeticIndex = i;
                break;
            }
        }

        if (firstAlphabeticIndex == -1) {
            return new StringBuilder(reversed).reverse().toString();
        }

        reversed = reversed.substring(firstAlphabeticIndex);

        line = new StringBuilder(reversed).reverse().toString();

        return line;
    }

}

/*

Find IsVerboseLogEnabled.
1. Findes options.config på klient siden?
	1.1 Ja
		1.1.1 Læs IsVerboseLogEnabled fra klient siden.
        1.2 Nej
		1.2.1 Læs IsVerboseLogEnabled fra lokal siden.

 // log(textHandler.LOG_SEPARATOR, LogHandler.LogLevel.INFORMATION, false);
    // log(textHandler.GAME_INIT_STARTED, LogHandler.LogLevel.INFORMATION, false);

 */