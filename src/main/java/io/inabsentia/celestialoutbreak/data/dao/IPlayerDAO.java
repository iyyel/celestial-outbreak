package io.inabsentia.celestialoutbreak.data.dao;

import io.inabsentia.celestialoutbreak.data.dto.PlayerDTO;

public interface IPlayerDAO {

    void loadPlayerDTO() throws PlayerDAOException;
    void savePlayerDTO(PlayerDTO playerDTO) throws PlayerDAOException;
    PlayerDTO getPlayerDTO() throws PlayerDAOException;

    class PlayerDAOException extends Exception {

        public PlayerDAOException(String msg) {
            super(msg);
        }

    }

}