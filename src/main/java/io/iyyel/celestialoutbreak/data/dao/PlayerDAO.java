package io.iyyel.celestialoutbreak.data.dao;

import io.iyyel.celestialoutbreak.data.dao.interfaces.IPlayerDAO;
import io.iyyel.celestialoutbreak.data.dto.PlayerDTO;
import io.iyyel.celestialoutbreak.handler.FileHandler;
import io.iyyel.celestialoutbreak.handler.TextHandler;
import io.iyyel.celestialoutbreak.utils.Utils;

import java.io.*;
import java.util.List;

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
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(textHandler.PLAYER_BIN_FILE_CLIENT_PATH));
            this.playerDTO = (PlayerDTO) ois.readObject();

            if (utils.isVerboseLogEnabled())
                fileHandler.writeLog("Successfully loaded '" + textHandler.PLAYER_BIN_FILE_NAME + "'!");

        } catch (FileNotFoundException e) {
            if (utils.isVerboseLogEnabled())
                fileHandler.writeLog("Failed to load '" + textHandler.PLAYER_BIN_FILE_NAME + "'. Creating new one!");

            playerDTO = new PlayerDTO();
            savePlayerDTO();

        } catch (IOException | ClassNotFoundException e) {
            throw new PlayerDAOException(e.getMessage());
        }
    }

    @Override
    public void savePlayerDTO() throws PlayerDAOException {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(textHandler.PLAYER_BIN_FILE_CLIENT_PATH));
            oos.writeObject(playerDTO);
            oos.close();
        } catch (IOException e) {
            throw new PlayerDAOException(e.getMessage());
        }
    }

    @Override
    public void addPlayer(String name) throws PlayerDAOException, PlayerDAOMaxNameException, PlayerDAOMinNameException {
        if (!checkNameMaxBounds(name))
            throw new PlayerDAOMaxNameException("'" + name + "' is too long!");

        if (!checkNameMinBounds(name))
            throw new PlayerDAOMinNameException("'" + name + "' is too small!");

        if (containsPlayer(name))
            throw new PlayerDAOException("'" + name + "' is an existing player!");

        playerDTO.getPlayerList().add(name);
    }

    @Override
    public void removePlayer(String name) throws PlayerDAOException {
        playerDTO.getPlayerList().remove(name);
    }

    @Override
    public void selectPlayer(String name) throws PlayerDAOException {
        if (!containsPlayer(name))
            throw new PlayerDAOException("'" + name + "' is not an existing player!");

        playerDTO.setSelectedPlayer(name);
    }

    @Override
    public List<String> getPlayerList() {
        return playerDTO.getPlayerList();
    }

    @Override
    public String getSelectedPlayer() throws PlayerDAOException {
        if (playerDTO.getSelectedPlayer() == null)
            throw new PlayerDAOException("No player is selected!");

        return playerDTO.getSelectedPlayer();
    }

    private boolean containsPlayer(String name) {
        return playerDTO.getPlayerList().stream().anyMatch(name::equalsIgnoreCase);
    }

    private boolean checkNameMinBounds(String name) {
        return name.length() >= 3;
    }

    private boolean checkNameMaxBounds(String name) {
        return name.length() <= 8;
    }

}