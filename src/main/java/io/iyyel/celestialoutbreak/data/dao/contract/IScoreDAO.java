package io.iyyel.celestialoutbreak.data.dao.contract;

import io.iyyel.celestialoutbreak.data.dao.ScoreDAO;
import io.iyyel.celestialoutbreak.data.dto.ScoreDTO;

import java.util.List;

public interface IScoreDAO {

    void loadScoreDTOList() throws ScoreDAOException;
    void saveScoreDTOList() throws ScoreDAOException;

    ScoreDTO getScore(String player, String levelName) throws ScoreDAOException;
    void addScore(ScoreDTO dto) throws ScoreDAOException;
    void removeScore(ScoreDTO dto) throws ScoreDAOException;
    void removeScore(int index) throws ScoreDAOException;
    List<ScoreDTO> getScores()  throws ScoreDAOException;

    class ScoreDAOException extends Exception {
        public ScoreDAOException(String msg) {
            super(msg);
        }
    }

}