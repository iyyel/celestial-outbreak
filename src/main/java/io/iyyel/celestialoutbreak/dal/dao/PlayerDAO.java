package io.iyyel.celestialoutbreak.dal.dao;

import io.iyyel.celestialoutbreak.dal.dao.contract.IPlayerDAO;
import io.iyyel.celestialoutbreak.dal.dto.PlayerDTO;
import io.iyyel.celestialoutbreak.handler.LogHandler;
import io.iyyel.celestialoutbreak.handler.OptionsHandler;
import io.iyyel.celestialoutbreak.handler.TextHandler;

import java.io.*;
import java.util.List;

public final class PlayerDAO implements IPlayerDAO {

    private PlayerDTO playerDTO;

    private final OptionsHandler optionsHandler = OptionsHandler.getInstance();
    private final LogHandler logHandler = LogHandler.getInstance();
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
            playerDTO = (PlayerDTO) ois.readObject();
            ois.close();
            logHandler.log("Successfully read binary player file '" + textHandler.PLAYER_BIN_FILE_NAME + "'", LogHandler.LogLevel.INFO, true);
        } catch (FileNotFoundException e) {
            logHandler.log("Failed to read binary player file '" + textHandler.PLAYER_BIN_FILE_NAME + "'", LogHandler.LogLevel.FAIL, true);
            createNewPlayerBinFile();
        } catch (IOException | ClassNotFoundException e) {
            throw new PlayerDAOException("Failed to load PlayerDTO: " + e.getMessage());
        }
    }

    @Override
    public void savePlayerDTO() throws PlayerDAOException {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(textHandler.PLAYER_BIN_FILE_CLIENT_PATH));
            oos.writeObject(playerDTO);
            oos.close();
            logHandler.log("Successfully saved binary player file '" + textHandler.PLAYER_BIN_FILE_NAME + "' at '" + textHandler.PLAYER_BIN_FILE_CLIENT_PATH + "'", LogHandler.LogLevel.INFO, true);
        } catch (IOException e) {
            throw new PlayerDAOException("Failed to save PlayerDTO: " + e.getMessage());
        }
    }

    @Override
    public void addPlayer(String name) throws PlayerDAOException {
        if (!checkNameMaxBounds(name)) {
            throw new PlayerDAOMaxNameException("'" + name + "' is too long!");
        }

        if (!checkNameMinBounds(name)) {
            throw new PlayerDAOMinNameException("'" + name + "' is too small!");
        }

        if (isPlayer(name)) {
            throw new PlayerDAOException("'" + name + "' is an existing player!");
        }

        // TODO: Remove magic number here.
        if (playerDTO.getPlayerList().size() >= 25) {
            throw new PlayerDAOLimitException("Player limit reached!");
        }

        playerDTO.getPlayerList().add(name);
    }

    @Override
    public void removePlayer(String name) throws PlayerDAOException {
        if (!isPlayer(name)) {
            throw new PlayerDAOException("'" + name + "' is not an existing player!");
        }
        playerDTO.getPlayerList().remove(name);
    }

    @Override
    public void selectPlayer(String name) throws PlayerDAOException {
        if (!isPlayer(name)) {
            throw new PlayerDAOException("'" + name + "' is not an existing player!");
        }
        playerDTO.setSelectedPlayer(name);
    }

    @Override
    public boolean isPlayer(String name) {
        return playerDTO.getPlayerList().stream().anyMatch(name::equalsIgnoreCase);
    }

    @Override
    public List<String> getPlayerList() {
        return playerDTO.getPlayerList();
    }

    @Override
    public String getSelectedPlayer() throws PlayerDAOException {
        if (playerDTO.getSelectedPlayer() == null) {
            throw new PlayerDAOException("No player is selectedIndex!");
        }
        return playerDTO.getSelectedPlayer();
    }

    private boolean checkNameMinBounds(String name) {
        // TODO: Remove magic number here.
        return name.length() >= 3;
    }

    private boolean checkNameMaxBounds(String name) {
        // TODO: Remove magic number here.
        return name.length() <= 8;
    }

    private void createNewPlayerBinFile() throws PlayerDAOException {
        logHandler.log("Creating empty binary player file '" + textHandler.PLAYER_BIN_FILE_NAME + "'", LogHandler.LogLevel.INFO, true);
        playerDTO = new PlayerDTO();
        savePlayerDTO();
    }

}