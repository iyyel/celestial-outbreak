package io.inabsentia.celestialoutbreak.data.dao;

import java.util.List;

public interface IPlayerDAO {

    void loadPlayerList() throws PlayerDAOException;
    void savePlayerList() throws PlayerDAOException;
    void createPlayer(String playerName) throws PlayerDAOException;
    boolean isExistingPlayer(String playerName) throws PlayerDAOException;
    List<String> getPlayerList() throws PlayerDAOException;
    void updatePlayer(String oldPlayerName, String newPlayerName) throws PlayerDAOException;
    void deletePlayer(String playerName) throws PlayerDAOException;

    class PlayerDAOException extends Exception {

        public PlayerDAOException(String msg) {
            super(msg);
        }

    }

}