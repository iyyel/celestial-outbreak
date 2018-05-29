package io.inabsentia.celestialoutbreak.data.dao;

import io.inabsentia.celestialoutbreak.handler.FileHandler;
import io.inabsentia.celestialoutbreak.handler.TextHandler;
import io.inabsentia.celestialoutbreak.utils.Utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public final class PlayerDAO implements IPlayerDAO {

    private List<String> playerList;

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
    public void loadPlayerList() throws PlayerDAOException {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(textHandler.PLAYER_CONFIG_FILE_CLIENT_PATH));
            playerList = (List<String>) ois.readObject();

            if (utils.isVerboseEnabled())
                fileHandler.writeLog("Successfully loaded 'players.bin'.");

        } catch (FileNotFoundException e) {
            /* If the file is not found, create it with an empty list. */
            playerList = new ArrayList<>();
            writePlayerList();

            if (utils.isVerboseEnabled())
                fileHandler.writeLog("Player list empty! Created empty player list.");
        } catch (IOException | ClassNotFoundException e) {
            if (utils.isVerboseEnabled())
                fileHandler.writeLog("Failed to load player list: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void savePlayerList() throws PlayerDAOException {
        writePlayerList();
    }

    @Override
    public void createPlayer(String playerName) throws PlayerDAOException {
        if (isExistingPlayer(playerName))
            throw new PlayerDAOException(playerName + " is already a existing Player!");

        checkPlayerNameBounds(playerName);

        /* Add new player */
        playerList.add(playerName);
    }

    @Override
    public boolean isExistingPlayer(String playerName) throws PlayerDAOException {
        return playerList.contains(playerName);
    }

    @Override
    public List<String> getPlayerList() throws PlayerDAOException {
        return playerList;
    }

    @Override
    public void updatePlayer(String oldPlayerName, String newPlayerName) throws PlayerDAOException {
        if (!isExistingPlayer(oldPlayerName))
            throw new PlayerDAOException(oldPlayerName + " is not an existing Player!");

        checkPlayerNameBounds(oldPlayerName);
        checkPlayerNameBounds(newPlayerName);

        /* Remove old, add new. */
        playerList.remove(oldPlayerName);
        playerList.add(newPlayerName);
    }

    @Override
    public void deletePlayer(String playerName) throws PlayerDAOException {
        playerList.remove(playerName);
    }

    private void writePlayerList() throws PlayerDAOException {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(textHandler.PLAYER_CONFIG_FILE_CLIENT_PATH));
            oos.writeObject(playerList);
            oos.close();
        } catch (IOException e) {
            throw new PlayerDAOException(e.getMessage());
        }
    }

    private void checkPlayerNameBounds(String playerName) throws PlayerDAOException {
        if (playerName.length() < 3)
            throw new PlayerDAOException("Player name must be 3 characters or more!");

        if (playerName.length() > 8)
            throw new PlayerDAOException("Player name must be 8 characters or less!");
    }

}