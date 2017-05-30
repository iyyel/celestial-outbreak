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
        /* Initial level configurations copied to client local config dir. */
        copyFile(FileHandler.class.getResource(textHandler.JAR_CONFIG_DIR + textHandler.LEVEL_FILE_NAME_RED).getPath(), textHandler.LEVEL_DIR_PATH + File.separator + textHandler.LEVEL_FILE_NAME_RED);
        copyFile(FileHandler.class.getResource(textHandler.JAR_CONFIG_DIR + textHandler.LEVEL_FILE_NAME_GREEN).getPath(), textHandler.LEVEL_DIR_PATH + File.separator + textHandler.LEVEL_FILE_NAME_GREEN);
        copyFile(FileHandler.class.getResource(textHandler.JAR_CONFIG_DIR + textHandler.LEVEL_FILE_NAME_BLUE).getPath(), textHandler.LEVEL_DIR_PATH + File.separator + textHandler.LEVEL_FILE_NAME_BLUE);
        copyFile(FileHandler.class.getResource(textHandler.JAR_CONFIG_DIR + textHandler.LEVEL_FILE_NAME_GREEN).getPath(), textHandler.LEVEL_DIR_PATH + File.separator + textHandler.LEVEL_FILE_NAME_GREEN);
        copyFile(FileHandler.class.getResource(textHandler.JAR_CONFIG_DIR + textHandler.LEVEL_FILE_NAME_PURPLE).getPath(), textHandler.LEVEL_DIR_PATH + File.separator + textHandler.LEVEL_FILE_NAME_PURPLE);
        copyFile(FileHandler.class.getResource(textHandler.JAR_CONFIG_DIR + textHandler.LEVEL_FILE_NAME_BORDEAUX).getPath(), textHandler.LEVEL_DIR_PATH + File.separator + textHandler.LEVEL_FILE_NAME_BORDEAUX);
        /* Level config file. */
        copyFile(FileHandler.class.getResource(textHandler.JAR_CONFIG_DIR + textHandler.LEVEL_CONFIG_FILE_NAME).getPath(), textHandler.LEVEL_DIR_PATH + File.separator + textHandler.LEVEL_CONFIG_FILE_NAME);
    }

    public static synchronized FileHandler getInstance() {
        return instance;
    }

    public Map<String, String> readPropertiesFromFile(String filePath) {
        Properties p = new Properties();
        Map<String, String> map = new HashMap<>();

        try (InputStream is = new FileInputStream(filePath)) {
            p.load(is);

            for (String key : p.stringPropertyNames()) {
                String value = p.getProperty(key);
                map.put(key, value);
            }
            is.close();

            if (utils.VERBOSE_ENABLED) utils.logMessage(textHandler.successReadProperties(filePath));
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
            String line = null;
            while ((line = br.readLine()) != null) {
                /* Ignore lines containing comments marked with # */
                if (!line.contains("#")) lineList.add(line);
            }
            if (utils.VERBOSE_ENABLED) utils.logMessage(textHandler.successReadLines(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lineList;
    }

    public void copyFile(String srcFilePath, String destFilePath) {
        File levelDir = new File(textHandler.LEVEL_DIR_PATH);
        if (!levelDir.exists()) {
            try {
                levelDir.mkdirs();
                if (utils.VERBOSE_ENABLED) utils.logMessage(textHandler.successCreatedDir(levelDir.getPath()));
            } catch (SecurityException se) {
                se.printStackTrace();
            }
        }

        File srcFile = new File(srcFilePath);
        File destFile = new File(destFilePath);

        try {
            if (!destFile.exists()) {
                FileUtils.copyFile(srcFile, destFile);
                if (utils.VERBOSE_ENABLED) utils.logMessage(textHandler.successCopiedFile(srcFile.getPath(), destFile.getPath()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}