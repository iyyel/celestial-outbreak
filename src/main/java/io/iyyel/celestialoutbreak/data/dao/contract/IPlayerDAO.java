package io.iyyel.celestialoutbreak.data.dao.contract;

import java.util.List;

public interface IPlayerDAO {

    void loadPlayerDTO() throws PlayerDAOException;
    void savePlayerDTO() throws PlayerDAOException;

    void addPlayer(String name) throws PlayerDAOException;
    void removePlayer(String name) throws PlayerDAOException;
    void selectPlayer(String name) throws PlayerDAOException;
    boolean isPlayer(String name);
    List<String> getPlayerList();
    String getSelectedPlayer() throws PlayerDAOException;

    class PlayerDAOException extends Exception {
        public PlayerDAOException(String msg) {
            super(msg);
        }
    }

    class PlayerDAOMinNameException extends PlayerDAOException {
        public PlayerDAOMinNameException(String msg) {
            super(msg);
        }
    }

    class PlayerDAOMaxNameException extends PlayerDAOException {
        public PlayerDAOMaxNameException(String msg) {
            super(msg);
        }
    }

    class PlayerDAOLimitException extends PlayerDAOException {
        public PlayerDAOLimitException(String msg) {
            super(msg);
        }
    }

}