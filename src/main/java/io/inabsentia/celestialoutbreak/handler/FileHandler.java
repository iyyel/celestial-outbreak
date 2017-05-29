package io.inabsentia.celestialoutbreak.handler;

import io.inabsentia.celestialoutbreak.utils.Utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class FileHandler {

    private final static FileHandler instance = new FileHandler();

    private final Utils utils = Utils.getInstance();

    private FileHandler() {

    }

    public Map<String, String> loadFromFile(String fileName) {
        Properties p = new Properties();
        Map<String, String> map = new HashMap<>();
        String path = FileHandler.class.getResource("/config/" + fileName).getPath();

        try (InputStream is = new FileInputStream(path)) {
            p.load(is);

            for (String key : p.stringPropertyNames()) {
                String value = p.getProperty(key);
                map.put(key, value);
            }
            is.close();

            if (utils.DEV_ENABLED) utils.logMessage("Loaded from '" + fileName + "' successfully.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return map;
    }

    public static synchronized FileHandler getInstance() {
        return instance;
    }

}