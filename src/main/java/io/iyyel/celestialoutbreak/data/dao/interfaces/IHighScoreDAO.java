package io.iyyel.celestialoutbreak.data.dao.interfaces;

import io.iyyel.celestialoutbreak.data.dto.HighScoreDTO;

import java.util.List;

public interface IHighScoreDAO {

    void loadHighScoreList() throws HighScoreDAOException;
    void saveHighScoreList() throws HighScoreDAOException;

    boolean isHighScore(HighScoreDTO dto) throws HighScoreDAOException;
    HighScoreDTO getHighScore(String player, String levelName) throws HighScoreDAOException;
    HighScoreDTO getHighScore(String levelName) throws HighScoreDAOException;
    void addHighScore(HighScoreDTO dto) throws HighScoreDAOException;
    void removeScore(HighScoreDTO dto) throws HighScoreDAOException;
    void removeScore(int index) throws HighScoreDAOException;
    List<HighScoreDTO> getScores()  throws HighScoreDAOException;

    class HighScoreDAOException extends Exception {
        public HighScoreDAOException(String msg) {
            super(msg);
        }
    }

}