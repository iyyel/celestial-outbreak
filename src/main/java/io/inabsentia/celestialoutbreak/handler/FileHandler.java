package io.inabsentia.celestialoutbreak.handler;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.*;

public class FileHandler {

    private static FileHandler instance;

    private final TextHandler textHandler = TextHandler.getInstance();

    private FileHandler() {
        initApp();
    }

    static {
        try {
            instance = new FileHandler();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static synchronized FileHandler getInstance() {
        return instance;
    }

    private void initApp() {
        initStandardDirs();
        initConfigFiles();
    }

    private void initStandardDirs() {
        initDir(textHandler.LOG_DIR_PATH);
        initDir(textHandler.SETTINGS_DIR_PATH);
        initDir(textHandler.LEVEL_DIR_PATH);
    }

    private void initConfigFiles() {
        /* Initial level configurations copied to client local config dir. */
        copyFile(textHandler.LEVEL_FILE_LOCAL_PATH_MARS, textHandler.LEVEL_FILE_CLIENT_PATH_MARS);
        copyFile(textHandler.LEVEL_FILE_LOCAL_PATH_EARTH, textHandler.LEVEL_FILE_CLIENT_PATH_EARTH);
        copyFile(textHandler.LEVEL_FILE_LOCAL_PATH_NEPTUNE, textHandler.LEVEL_FILE_CLIENT_PATH_NEPTUNE);
        copyFile(textHandler.LEVEL_FILE_LOCAL_PATH_VENUS, textHandler.LEVEL_FILE_CLIENT_PATH_VENUS);
        copyFile(textHandler.LEVEL_FILE_LOCAL_PATH_JUPITER, textHandler.LEVEL_FILE_CLIENT_PATH_JUPITER);

        /* Level config file. */
        copyFile(textHandler.LEVEL_CONFIG_FILE_LOCAL_PATH, textHandler.LEVEL_CONFIG_FILE_CLIENT_PATH);

        /* Settings config file. */
        copyFile(textHandler.SETTINGS_CONFIG_FILE_LOCAL_PATH, textHandler.SETTINGS_CONFIG_FILE_CLIENT_PATH);
    }

    public Map<String, String> readPropertiesFromFile(String fileName) {
        Properties p = new Properties();
        Map<String, String> map = new HashMap<>();
        writeLogMessage("Reading properties from '" + fileName + "'...");

        try (InputStream is = new FileInputStream(fileName)) {
            p.load(is);

            for (String key : p.stringPropertyNames()) {
                String value = p.getProperty(key);
                map.put(key, value);
                writeLogMessage(textHandler.successReadProperty(key, value, fileName));
            }

            is.close();
            writeLogMessage(textHandler.successReadProperties(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;
    }

    public List<String> readLinesFromFile(String fileName) {
        List<String> lineList = new ArrayList<>();
        writeLogMessage("Reading lines from '" + fileName + "'...");

        try (FileInputStream fis = new FileInputStream(new File(fileName))) {
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String line;
            while ((line = br.readLine()) != null) {
                /* Ignore lines containing comments marked with # */
                if (!line.contains("#") && line.length() > 0) {
                    lineList.add(line);
                    writeLogMessage(textHandler.successReadLine(line, fileName));
                }
            }
            writeLogMessage(textHandler.successReadLines(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lineList;
    }

    private void initDir(String dirPath) {
        File dir = new File(dirPath);
        if (!dir.exists()) {
            try {
                boolean result = dir.mkdirs();
                if (result) writeLogMessage(textHandler.successCreatedDir(dirPath));
                else writeLogMessage(textHandler.errCreatedDir(dirPath));
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
    }

    private void copyFile(String srcFilePath, String destFilePath) {
        File srcFile = new File(srcFilePath);
        File destFile = new File(destFilePath);

        try {
            /*
             * Check to see whether the file already exists at the destination.
             * If it does, we do not want to overwrite it, since the
             * user could have modified the file to their liking.
             */
            if (!destFile.exists()) {
                FileUtils.copyFile(srcFile, destFile);
                writeLogMessage(textHandler.successCopiedFile(srcFile.getPath(), destFile.getPath()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeToFile(String msg, String filePath) {
        File file = new File(filePath);
        if (file.exists() && !file.isDirectory()) {
            try (PrintWriter out = new PrintWriter(new FileOutputStream(file, true))) {
                out.append(msg + "\r\n");
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else if (!file.exists() && !file.isDirectory()) {
            try (PrintWriter out = new PrintWriter(filePath)) {
                out.print(msg + "\r\n");

                String fileMessage = textHandler.logMsgPrefix() + textHandler.successCreatedFile(filePath) + "\r\n";
                // Find a way for this to print to the log file, only if its trying to create a log file.. errhh.
                System.out.print(fileMessage);

                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void writeLogMessage(String msg) {
        msg = textHandler.logMsgPrefix() + msg;
        System.out.println(msg);
        initDir(textHandler.LOG_DIR_PATH);
        writeToFile(msg, textHandler.LOG_FILE_PATH);
    }

}