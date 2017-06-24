package io.inabsentia.celestialoutbreak.data.dao;

import io.inabsentia.celestialoutbreak.data.IDBConnector.DALException;
import io.inabsentia.celestialoutbreak.data.dto.ScoreBoardDTO;

import java.util.List;

public interface IScoreBoardDAO {
    ScoreBoardDTO getScoreBoard(int sbId) throws DALException;
    List<ScoreBoardDTO> getScoreBoardList() throws DALException;
    void createScoreBoard(ScoreBoardDTO sbDTO) throws DALException;
    void updateScoreBoard(ScoreBoardDTO sbDTO) throws DALException;
    void deleteScoreBoard(int sbId) throws DALException;
}