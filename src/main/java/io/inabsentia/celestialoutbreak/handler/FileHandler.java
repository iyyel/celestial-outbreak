package io.inabsentia.celestialoutbreak.handler;

import io.inabsentia.celestialoutbreak.utils.Utils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class FileHandler {

    private final static FileHandler instance = new FileHandler();

    private final Utils utils = Utils.getInstance();
    private final boolean DEV_ENABLED = utils.DEV_ENABLED;

    private FileHandler() {

    }

    /* Convenience method */
    public void saveToFile(Map<String, String> map, String path) {
        Properties p = new Properties();

        for (String key : map.keySet()) {
            String value = map.get(key);
            p.setProperty(key, value);
        }

        try (OutputStream os = new FileOutputStream(path)) {
            p.store(os, path);
            os.close();

            if (DEV_ENABLED) {
                utils.logMessage("Saved to file '" + path + "' successfully.");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

            if (DEV_ENABLED) {
                utils.logMessage("Loaded from '" + fileName + "' successfully.");
            }
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