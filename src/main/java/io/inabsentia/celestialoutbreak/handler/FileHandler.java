package io.inabsentia.celestialoutbreak.handler;

import io.inabsentia.celestialoutbreak.utils.Utils;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.*;

public class FileHandler {

    private final static FileHandler instance = new FileHandler();

    private final Utils utils = Utils.getInstance();
    private final TextHandler textHandler = TextHandler.getInstance();

    private FileHandler() {
        initApp();
    }

    public static synchronized FileHandler getInstance() {
        return instance;
    }

    private void initApp() {
        writeLogMessage(textHandler.NEW_APP_INSTANCE);
        initStandardDirs();
        initConfigFiles();
        initSettingFlags();
        writeLogMessage(textHandler.NEW_APP_INSTANCE_SUCCESS);
    }

    private void initStandardDirs() {
        initDir(textHandler.LOG_DIR_PATH);
        initDir(textHandler.SETTINGS_DIR_PATH);
        initDir(textHandler.LEVEL_DIR_PATH);
    }

    private void initConfigFiles() {
        /* Initial level configurations copied to client local config dir. */
        copyFile(FileHandler.class.getResource(textHandler.JAR_CONFIG_DIR + textHandler.LEVEL_FILE_NAME_MARS).getPath(), textHandler.LEVEL_DIR_PATH + File.separator + textHandler.LEVEL_FILE_NAME_MARS);
        copyFile(FileHandler.class.getResource(textHandler.JAR_CONFIG_DIR + textHandler.LEVEL_FILE_NAME_EARTH).getPath(), textHandler.LEVEL_DIR_PATH + File.separator + textHandler.LEVEL_FILE_NAME_EARTH);
        copyFile(FileHandler.class.getResource(textHandler.JAR_CONFIG_DIR + textHandler.LEVEL_FILE_NAME_NEPTUNE).getPath(), textHandler.LEVEL_DIR_PATH + File.separator + textHandler.LEVEL_FILE_NAME_NEPTUNE);
        copyFile(FileHandler.class.getResource(textHandler.JAR_CONFIG_DIR + textHandler.LEVEL_FILE_NAME_EARTH).getPath(), textHandler.LEVEL_DIR_PATH + File.separator + textHandler.LEVEL_FILE_NAME_EARTH);
        copyFile(FileHandler.class.getResource(textHandler.JAR_CONFIG_DIR + textHandler.LEVEL_FILE_NAME_VENUS).getPath(), textHandler.LEVEL_DIR_PATH + File.separator + textHandler.LEVEL_FILE_NAME_VENUS);
        copyFile(FileHandler.class.getResource(textHandler.JAR_CONFIG_DIR + textHandler.LEVEL_FILE_NAME_JUPITER).getPath(), textHandler.LEVEL_DIR_PATH + File.separator + textHandler.LEVEL_FILE_NAME_JUPITER);

        /* Level config file. */
        copyFile(FileHandler.class.getResource(textHandler.JAR_CONFIG_DIR + textHandler.LEVEL_CONFIG_FILE_NAME).getPath(), textHandler.LEVEL_CONFIG_FILE_PATH);

        /* Settings config file. */
        copyFile(FileHandler.class.getResource(textHandler.JAR_CONFIG_DIR + textHandler.SETTINGS_CONFIG_FILE_NAME).getPath(), textHandler.SETTINGS_CONFIG_FILE_PATH);
    }

    private void initSettingFlags() {
        Map<String, String> map = readPropertiesFromFile(textHandler.SETTINGS_CONFIG_FILE_PATH);
        utils.setVerboseEnabled(Boolean.parseBoolean(map.get("VERBOSE_ENABLED")));
        utils.setSoundEnabled(Boolean.parseBoolean(map.get("SOUND_ENABLED")));
        utils.setGodModeEnabled(Boolean.parseBoolean(map.get("GOD_MODE_ENABLED")));
    }

    public Map<String, String> readPropertiesFromFile(String fileName) {
        Properties p = new Properties();
        Map<String, String> map = new HashMap<>();

        try (InputStream is = new FileInputStream(fileName)) {
            p.load(is);

            for (String key : p.stringPropertyNames()) {
                String value = p.getProperty(key);
                map.put(key, value);
                if (utils.isVerboseEnabled()) writeLogMessage("Successfully read property '" + key + ":" + value + "' from '" + fileName + "'.");
            }

            is.close();
            writeLogMessage(textHandler.successReadProperties(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;
    }

    public List<String> readLinesFromFile(String fileName) {
        List<String> lineList = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(new File(fileName))) {
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String line;
            while ((line = br.readLine()) != null) {
                /* Ignore lines containing comments marked with # */
                if (!line.contains("#")) {
                    lineList.add(line);
                    if (utils.isVerboseEnabled()) writeLogMessage("Successfully read line '" + line + "' from '" + fileName + "'.");
                }
            }
            writeLogMessage(textHandler.successReadLines(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lineList;
    }

    /*
     * Returns true if the directory gets created and false it it does not.
     */
    private boolean initDir(String dirPath) {
        File dir = new File(dirPath);
        if (!dir.exists()) {
            try {
                dir.mkdirs();
                writeLogMessage(textHandler.successCreatedDir(dirPath));
                return true;
            } catch (SecurityException se) {
                se.printStackTrace();
            }
        }
        return false;
    }

    private void copyFile(String srcFilePath, String destFilePath) {
        File srcFile = new File(srcFilePath);
        File destFile = new File(destFilePath);

        try {
            /*
             * Check to see whether the file already exists at the destination.
             * If it does, we do not want to overwrite it, since the
             * user could have modified the file for their liking.
             */
            if (!destFile.exists()) {
                FileUtils.copyFile(srcFile, destFile);
                writeLogMessage(textHandler.successCopiedFile(srcFile.getPath(), destFile.getPath()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeToFile(String message, String filePath) {
        File file = new File(filePath);
        if (file.exists() && !file.isDirectory()) {
            try (PrintWriter out = new PrintWriter(new FileOutputStream(file, true))) {
                out.append(message + "\r\n");
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else if (!file.exists() && !file.isDirectory()) {
            try (PrintWriter out = new PrintWriter(filePath)) {
                out.print(message + "\r\n");

                String fileMessage = textHandler.logMsgPrefix() + textHandler.successCreatedFile(filePath) + "\r\n";
                out.print(fileMessage);
                System.err.print(fileMessage);

                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void writeLogMessage(String message) {
        message = textHandler.logMsgPrefix() + message;
        System.err.println(message);
        initDir(textHandler.LOG_DIR_PATH);
        writeToFile(message, textHandler.LOG_FILE_PATH);
    }

}