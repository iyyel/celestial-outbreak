package io.iyyel.celestialoutbreak.data.dao;

import io.iyyel.celestialoutbreak.data.dao.interfaces.IPlayerDAO;
import io.iyyel.celestialoutbreak.data.dto.PlayerDTO;
import io.iyyel.celestialoutbreak.handler.LogHandler;
import io.iyyel.celestialoutbreak.handler.TextHandler;

import java.io.*;
import java.util.List;

public final class PlayerDAO implements IPlayerDAO {

    private PlayerDTO playerDTO;

    private final LogHandler logHandler = LogHandler.getInstance();
    private final TextHandler textHandler = TextHandler.getInstance();

    private static final IPlayerDAO instance;

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
            ObjectInputStream ois = new ObjectInputStream(
                    new FileInputStream(textHandler.PLAYER_BIN_FILE_CLIENT_PATH));
            playerDTO = (PlayerDTO) ois.readObject();
            ois.close();
            logHandler.log(textHandler.successReadPlayerBinary(), "loadPlayerDTO",
                    LogHandler.LogLevel.INFO, true);
        } catch (FileNotFoundException e) {
            logHandler.log(textHandler.errorReadPlayerBinary(), "loadPlayerDTO",
                    LogHandler.LogLevel.FAIL, true);
            createNewPlayerBinFile();
        } catch (IOException | ClassNotFoundException e) {
            logHandler.log(textHandler.errorOccurred("Error occurred while loading player binary file", e),
                    "loadPlayerDTO", LogHandler.LogLevel.ERROR, false);
            throw new PlayerDAOException("Failed to load PlayerDTO: " + e.getMessage());
        }
    }

    @Override
    public void savePlayerDTO() throws PlayerDAOException {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(
                    new FileOutputStream(textHandler.PLAYER_BIN_FILE_CLIENT_PATH));
            oos.writeObject(playerDTO);
            oos.close();
            logHandler.log(textHandler.successSavePlayerBinary(), "savePlayerDTO",
                    LogHandler.LogLevel.INFO, true);
        } catch (IOException e) {
            logHandler.log(textHandler.errorOccurred("Error occurred during saving of player binary file", e),
                    "savePlayerDTO", LogHandler.LogLevel.ERROR, false);
            throw new PlayerDAOException("Failed to save PlayerDTO: " + e.getMessage());
        }
    }

    @Override
    public void addPlayer(String player) throws PlayerDAOException {
        if (!checkPlayerMaxBounds(player)) {
            throw new PlayerDAOMaxNameException("'" + player + "' is too long!");
        }

        if (!checkPlayerMinBounds(player)) {
            throw new PlayerDAOMinNameException("'" + player + "' is too small!");
        }

        if (isPlayer(player)) {
            throw new PlayerDAOException("'" + player + "' is an existing player!");
        }

        if (playerDTO.getPlayerCount() >= textHandler.MAX_PLAYERS) {
            logHandler.log("Player limit (" + textHandler.MAX_PLAYERS + ") reached. Remove some players.",
                    "addPlayer", LogHandler.LogLevel.FAIL, false);
            throw new PlayerDAOLimitException("Player limit reached!");
        }

        playerDTO.addPlayer(player);
    }

    @Override
    public void removePlayer(String player) throws PlayerDAOException {
        if (!isPlayer(player)) {
            logHandler.log("'" + player + "' is not an existing player!", "removePlayer", LogHandler.LogLevel.FAIL, false);
            throw new PlayerDAOException("'" + player + "' is not an existing player!");
        }
        playerDTO.removePlayer(player);
    }

    @Override
    public void selectPlayer(String player) throws PlayerDAOException {
        if (!isPlayer(player)) {
            logHandler.log("'" + player + "' is not an existing player!", "selectPlayer", LogHandler.LogLevel.FAIL, false);
            throw new PlayerDAOException("'" + player + "' is not an existing player!");
        }
        playerDTO.setSelectedPlayer(player);
    }

    @Override
    public boolean isPlayer(String player) {
        return playerDTO.getPlayers().stream().anyMatch(player::equalsIgnoreCase);
    }

    @Override
    public List<String> getPlayers() {
        return playerDTO.getPlayers();
    }

    @Override
    public String getSelectedPlayer() throws PlayerDAOException {
        if (playerDTO.getSelectedPlayer() == null) {
            logHandler.log("No player is selectedIndex!", "getSelectedPlayer", LogHandler.LogLevel.FAIL, false);
            throw new PlayerDAOException("No player is selectedIndex!");
        }
        return playerDTO.getSelectedPlayer();
    }

    private boolean checkPlayerMinBounds(String player) {
        return player.length() >= textHandler.PLAYER_NAME_MIN_LENGTH;
    }

    private boolean checkPlayerMaxBounds(String player) {
        return player.length() <= textHandler.PLAYER_NAME_MAX_LENGTH;
    }

    private void createNewPlayerBinFile() throws PlayerDAOException {
        logHandler.log("Creating empty binary player file '" + textHandler.PLAYER_BIN_FILE_NAME + "'","createNewPlayerBinFile", LogHandler.LogLevel.INFO, true);
        playerDTO = new PlayerDTO();
        savePlayerDTO();
    }

}