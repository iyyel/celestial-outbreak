package io.inabsentia.celestialoutbreak.handler;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.exception.ExceptionUtils;

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
        createStandardDirs();
        copyConfigFiles();
    }

    private void createStandardDirs() {
        createDir(textHandler.LOG_DIR_PATH);
        createDir(textHandler.SETTINGS_DIR_PATH);
        createDir(textHandler.LEVEL_DIR_PATH);
    }

    private void copyConfigFiles() {
        /* Initial level configurations copied to client local config dir. */
        copyFile(textHandler.LEVEL_FILE_LOCAL_PATH_MARS, textHandler.LEVEL_FILE_CLIENT_PATH_MARS);
        copyFile(textHandler.LEVEL_FILE_LOCAL_PATH_EARTH, textHandler.LEVEL_FILE_CLIENT_PATH_EARTH);
        copyFile(textHandler.LEVEL_FILE_LOCAL_PATH_NEPTUNE, textHandler.LEVEL_FILE_CLIENT_PATH_NEPTUNE);
        copyFile(textHandler.LEVEL_FILE_LOCAL_PATH_VENUS, textHandler.LEVEL_FILE_CLIENT_PATH_VENUS);
        copyFile(textHandler.LEVEL_FILE_LOCAL_PATH_JUPITER, textHandler.LEVEL_FILE_CLIENT_PATH_JUPITER);

        /* Main level configuration file coped to client machine. */
        copyFile(textHandler.LEVEL_CONFIG_FILE_LOCAL_PATH, textHandler.LEVEL_CONFIG_FILE_CLIENT_PATH);

        /* Settings configuration file copied to client machine. */
        copyFile(textHandler.SETTINGS_CONFIG_FILE_LOCAL_PATH, textHandler.SETTINGS_CONFIG_FILE_CLIENT_PATH);
    }

    public Map<String, String> readPropertiesFromFile(String filePath) {
        Properties p = new Properties();
        Map<String, String> map = new HashMap<>();
        writeLogMsg(textHandler.readingPropertiesMsg(filePath));

        try (InputStream is = new FileInputStream(filePath)) {
            p.load(is);

            for (String key : p.stringPropertyNames()) {
                String value = p.getProperty(key);
                map.put(key, value);
                writeLogMsg(textHandler.successReadPropertyMsg(key, value, filePath));
            }

            writeLogMsg(textHandler.finishReadPropertiesMsg(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;
    }

    public List<String> readLinesFromFile(String filePath) {
        List<String> lineList = new ArrayList<>();
        writeLogMsg(textHandler.readingLinesMsg(filePath));

        try (FileInputStream fis = new FileInputStream(new File(filePath))) {
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String line;
            while ((line = br.readLine()) != null) {
                /*
                 * Ignore lines containing comments starting with #
                 * and only add the line if the length of it is greater than zero,
                 * to make sure that there actually is a worthy line to read.
                 */
                if (!line.contains("#") && line.length() > 0) {
                    lineList.add(line);
                    writeLogMsg(textHandler.successReadLineMsg(line, filePath));
                }
            }
            writeLogMsg(textHandler.successReadLinesMsg(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lineList;
    }

    private void createDir(String dirPath) {
        File dir = new File(dirPath);

        if (!dir.exists()) {
            try {
                boolean result = dir.mkdirs();
                if (result)
                    writeLogMsg(textHandler.successCreatedDirMsg(dirPath));
                else
                    writeLogMsg(textHandler.errCreatingDirMsg(dirPath));
            } catch (SecurityException e) {
                writeLogMsg(textHandler.errCreatingDirMsg(dirPath, ExceptionUtils.getStackTrace(e)));
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
                writeLogMsg(textHandler.successCopiedFileMsg(srcFile.getPath(), destFile.getPath()));
            }
        } catch (IOException e) {
            writeLogMsg(textHandler.errCopyingFileMsg(srcFilePath, destFilePath, ExceptionUtils.getStackTrace(e)));
        }
    }

    public void writeToFile(String msg, String filePath) {
        File file = new File(filePath);
        if (file.isDirectory()) return;

        try {
            if (file.exists()) {
                try (PrintWriter out = new PrintWriter(new FileOutputStream(file, true))) {
                    out.append(msg + "\r\n");
                }
            } else {
                try (PrintWriter out = new PrintWriter(filePath)) {
                    out.print(msg + "\r\n");
                }
                writeLogMsg(textHandler.successCreatedFileMsg(filePath));
            }
        } catch (IOException e) {
            writeLogMsg(textHandler.errWritingToFileMsg(filePath, ExceptionUtils.getStackTrace(e)));
        }
    }

    public void writeLogMsg(String msg) {
        msg = textHandler.logMsgPrefix() + msg;
        System.out.println(msg);
        createDir(textHandler.LOG_DIR_PATH);
        writeToFile(msg, textHandler.LOG_FILE_PATH);
    }

}