package io.inabsentia.celestialoutbreak.data.dao;

import io.inabsentia.celestialoutbreak.data.dto.ScoreBoardDTO;

import java.util.List;

public interface IScoreBoardDAO {
    ScoreBoardDTO getScoreBoard(int sbId) throws DALException;
    List<ScoreBoardDTO> getScoreBoardList() throws DALException;
    void createScoreBoard(ScoreBoardDTO sbDTO) throws DALException;
    void updateScoreBoard(ScoreBoardDTO sbDTO) throws DALException;
    void deleteScoreBoard(int sbId) throws DALException;

    class DALException extends Exception {

        public DALException(String msg, Throwable e) {
            super(msg, e);
        }

        public DALException(String msg) {
            super(msg);
        }

    }

}