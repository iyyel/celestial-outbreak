package io.inabsentia.celestialoutbreak.data.dao;

import io.inabsentia.celestialoutbreak.data.dto.PlayerDTO;
import io.inabsentia.celestialoutbreak.handler.FileHandler;
import io.inabsentia.celestialoutbreak.handler.TextHandler;
import io.inabsentia.celestialoutbreak.utils.Utils;

import java.io.*;

public final class PlayerDAO implements IPlayerDAO {

    private PlayerDTO playerDTO;

    private final Utils utils = Utils.getInstance();
    private final FileHandler fileHandler = FileHandler.getInstance();
    private final TextHandler textHandler = TextHandler.getInstance();

    private static IPlayerDAO instance;

    private PlayerDAO() {

    }

    static {
        try {
            instance = new PlayerDAO();
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate Singleton PlayerDAO!");
        }
    }

    public static synchronized IPlayerDAO getInstance() {
        return instance;
    }

    @Override
    public void loadPlayerDTO() throws PlayerDAOException {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(textHandler.PLAYER_CONFIG_FILE_CLIENT_PATH));
            this.playerDTO = (PlayerDTO) ois.readObject();

            if (utils.isVerboseEnabled())
                fileHandler.writeLog("Successfully loaded '" + textHandler.PLAYER_CONFIG_FILE_NAME + "'!");

        } catch (FileNotFoundException e) {
            if (utils.isVerboseEnabled())
                fileHandler.writeLog("Failed to load '" + textHandler.PLAYER_CONFIG_FILE_NAME + "'. Creating new one!");

            PlayerDTO playerDTO = new PlayerDTO();
            savePlayerDTO(playerDTO);
            this.playerDTO = playerDTO;

        } catch (IOException | ClassNotFoundException e) {
            throw new PlayerDAOException(e.getMessage());
        }
    }

    @Override
    public void savePlayerDTO(PlayerDTO playerDTO) throws PlayerDAOException {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(textHandler.PLAYER_CONFIG_FILE_CLIENT_PATH));
            oos.writeObject(playerDTO);
            oos.close();
        } catch (IOException e) {
            throw new PlayerDAOException(e.getMessage());
        }
    }

    @Override
    public PlayerDTO getPlayerDTO() throws PlayerDAOException {
        if (playerDTO == null)
            throw new PlayerDAOException("PlayerDTO has not been loaded yet!");
        return playerDTO;
    }

}