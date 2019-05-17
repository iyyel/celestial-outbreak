package io.iyyel.celestialoutbreak.handler;

import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.*;

public final class LogHandler {

    private final TextHandler textHandler = TextHandler.getInstance();

    private static LogHandler instance;

    public enum LogLevel {
        ERROR,
        FAILURE,
        INFORMATION
    }

    private LogHandler() {

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

    public void log(String text, LogLevel logLevel, boolean isVerboseLog) {
        // if (this.isVerboseLog != isVerboseLog) {
        //     return;
        // }

        text = textHandler.logMsgPrefix(logLevel) + text;
        System.out.println(text);
        writeToLogFile(text);
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

}