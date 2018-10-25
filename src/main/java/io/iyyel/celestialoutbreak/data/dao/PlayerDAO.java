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
            playerDTO = (PlayerDTO) ois.readObject();
            ois.close();

            if (utils.isVerboseLogEnabled()) {
                fileHandler.writeLog("Successfully read binary player file '" + textHandler.PLAYER_BIN_FILE_NAME + "'");
            }
        } catch (FileNotFoundException e) {
            if (utils.isVerboseLogEnabled()) {
                fileHandler.writeLog("Failed to read binary player file '" + textHandler.PLAYER_BIN_FILE_NAME + "'");
            }

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

            if (utils.isVerboseLogEnabled()) {
                fileHandler.writeLog("Successfully saved binary player file '" + textHandler.PLAYER_BIN_FILE_NAME + "' at '" + textHandler.PLAYER_BIN_FILE_CLIENT_PATH + "'");
            }
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
    public boolean isPlayer(String name) throws PlayerDAOException {
        return playerDTO.getPlayerList().stream().anyMatch(name::equalsIgnoreCase);
    }

    @Override
    public List<String> getPlayerList() {
        return playerDTO.getPlayerList();
    }

    @Override
    public String getSelectedPlayer() throws PlayerDAOException {
        if (playerDTO.getSelectedPlayer() == null) {
            throw new PlayerDAOException("No player is selected!");
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
        if (utils.isVerboseLogEnabled()) {
            fileHandler.writeLog("Creating empty binary player file '" + textHandler.PLAYER_BIN_FILE_NAME + "'");
        }

        playerDTO = new PlayerDTO();
        savePlayerDTO();
    }

}