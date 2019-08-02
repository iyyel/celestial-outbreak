package io.iyyel.celestialoutbreak.data.dao;

import io.iyyel.celestialoutbreak.data.dao.contract.IPlayerDAO;
import io.iyyel.celestialoutbreak.data.dao.contract.IScoreDAO;
import io.iyyel.celestialoutbreak.data.dto.ScoreDTO;
import io.iyyel.celestialoutbreak.handler.LogHandler;
import io.iyyel.celestialoutbreak.handler.TextHandler;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class ScoreDAO implements IScoreDAO {

    private final LogHandler logHandler = LogHandler.getInstance();
    private final TextHandler textHandler = TextHandler.getInstance();

    private List<ScoreDTO> scoreDTOList = null;

    private static IScoreDAO instance;

    private ScoreDAO() {

    }

    static {
        try {
            instance = new ScoreDAO();
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate Singleton ScoreDAO!");
        }
    }

    public static synchronized IScoreDAO getInstance() {
        return instance;
    }

    @Override
    public void loadScoreDTOList() throws ScoreDAOException {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(textHandler.SCORE_BIN_FILE_CLIENT_PATH));
            scoreDTOList = (List<ScoreDTO>) ois.readObject();
            ois.close();
            logHandler.log("Successfully read binary score file '" + textHandler.SCORE_BIN_FILE_NAME + "'", LogHandler.LogLevel.INFO, true);
        } catch (FileNotFoundException e) {
            logHandler.log("Failed to read binary score file '" + textHandler.SCORE_BIN_FILE_NAME + "'", LogHandler.LogLevel.FAIL, true);
            createNewScoreBinFile();
        } catch (IOException | ClassNotFoundException e) {
            logHandler.log("Exception: " + e.getMessage(), LogHandler.LogLevel.ERROR, false);
            throw new ScoreDAOException("Failed to load ScoreDTO list: " + e.getMessage());
        }
    }

    @Override
    public void saveScoreDTOList() throws ScoreDAOException {
        if (scoreDTOList == null)
            throw new ScoreDAOException("Please load first.");
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(textHandler.SCORE_BIN_FILE_CLIENT_PATH));
            oos.writeObject(scoreDTOList);
            oos.close();
            logHandler.log("Successfully saved binary score file '" + textHandler.SCORE_BIN_FILE_NAME + "' at '" + textHandler.SCORE_BIN_FILE_CLIENT_PATH + "'", LogHandler.LogLevel.INFO, true);
        } catch (IOException e) {
            logHandler.log("Exception: " + e.getMessage(), LogHandler.LogLevel.ERROR, false);
            throw new ScoreDAOException("Failed to save ScoreDTO list: " + e.getMessage());
        }
    }

    @Override
    public ScoreDTO getScore(String player, String levelName) throws ScoreDAOException {
        if (scoreDTOList == null)
            throw new ScoreDAOException("Please load first.");
        Optional<ScoreDTO> match = scoreDTOList.stream().filter(scoreDTO -> scoreDTO.getPlayer()
                .equals(player) && scoreDTO.getLevelName().equals(levelName)).findFirst();
        return match.orElse(null);
    }

    @Override
    public void addScore(ScoreDTO dto) throws ScoreDAOException {
        if (scoreDTOList == null)
            throw new ScoreDAOException("Please load first.");
        scoreDTOList.add(dto);
    }

    @Override
    public void removeScore(ScoreDTO dto) throws ScoreDAOException {
        if (scoreDTOList == null)
            throw new ScoreDAOException("Please load first.");
        scoreDTOList.remove(dto);
    }

    @Override
    public void removeScore(int index) throws ScoreDAOException {
        if (scoreDTOList == null)
            throw new ScoreDAOException("Please load first.");
        scoreDTOList.remove(index);
    }

    @Override
    public List<ScoreDTO> getScores() throws ScoreDAOException {
        if (scoreDTOList == null)
            throw new ScoreDAOException("Please load first.");
        return scoreDTOList;
    }

    private void createNewScoreBinFile() throws ScoreDAOException {
        logHandler.log("Creating empty binary score file '" + textHandler.SCORE_BIN_FILE_NAME + "'", LogHandler.LogLevel.INFO, true);
        scoreDTOList = new ArrayList<>();
        saveScoreDTOList();
    }

}