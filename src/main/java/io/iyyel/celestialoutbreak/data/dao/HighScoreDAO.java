package io.iyyel.celestialoutbreak.data.dao;

import io.iyyel.celestialoutbreak.data.dao.contract.IHighScoreDAO;
import io.iyyel.celestialoutbreak.data.dto.HighScoreDTO;
import io.iyyel.celestialoutbreak.handler.LogHandler;
import io.iyyel.celestialoutbreak.handler.TextHandler;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class HighScoreDAO implements IHighScoreDAO {

    private final LogHandler logHandler = LogHandler.getInstance();
    private final TextHandler textHandler = TextHandler.getInstance();

    private List<HighScoreDTO> highScoreDTOList = null;

    private static IHighScoreDAO instance;

    private HighScoreDAO() {

    }

    static {
        try {
            instance = new HighScoreDAO();
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate Singleton ScoreDAO!");
        }
    }

    public static synchronized IHighScoreDAO getInstance() {
        return instance;
    }

    @Override
    public void loadHighScoreList() throws HighScoreDAOException {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(textHandler.SCORE_BIN_FILE_CLIENT_PATH));
            highScoreDTOList = (List<HighScoreDTO>) ois.readObject();
            ois.close();
            logHandler.log("Successfully read binary score file '" + textHandler.SCORE_BIN_FILE_NAME + "'", LogHandler.LogLevel.INFO, true);
        } catch (FileNotFoundException e) {
            logHandler.log("Failed to read binary score file '" + textHandler.SCORE_BIN_FILE_NAME + "'", LogHandler.LogLevel.FAIL, true);
            createNewScoreBinFile();
        } catch (IOException | ClassNotFoundException e) {
            logHandler.log("Exception: " + e.getMessage(), LogHandler.LogLevel.ERROR, false);
            throw new HighScoreDAOException("Failed to load ScoreDTO list: " + e.getMessage());
        }
    }

    @Override
    public void saveHighScoreList() throws HighScoreDAOException {
        if (highScoreDTOList == null)
            throw new HighScoreDAOException("Please load first.");
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(textHandler.SCORE_BIN_FILE_CLIENT_PATH));
            oos.writeObject(highScoreDTOList);
            oos.close();
            logHandler.log("Successfully saved binary score file '" + textHandler.SCORE_BIN_FILE_NAME + "' at '" + textHandler.SCORE_BIN_FILE_CLIENT_PATH + "'", LogHandler.LogLevel.INFO, true);
        } catch (IOException e) {
            logHandler.log("Exception: " + e.getMessage(), LogHandler.LogLevel.ERROR, false);
            throw new HighScoreDAOException("Failed to save ScoreDTO list: " + e.getMessage());
        }
    }

    @Override
    public boolean isHighScore(HighScoreDTO dto) throws HighScoreDAOException {
        if (highScoreDTOList == null)
            throw new HighScoreDAOException("Please load first.");

        HighScoreDTO currentHighScore = getHighScore(dto.getLevelName());

        if (currentHighScore == null)
            return true;
        else
            return dto.getScore() > currentHighScore.getScore();
    }

    @Override
    public HighScoreDTO getHighScore(String player, String levelName) throws HighScoreDAOException {
        if (highScoreDTOList == null)
            throw new HighScoreDAOException("Please load first.");
        Optional<HighScoreDTO> match = highScoreDTOList.stream().filter(highScoreDTO -> highScoreDTO.getPlayer()
                .equals(player) && highScoreDTO.getLevelName().equals(levelName)).findFirst();
        return match.orElse(null);
    }

    @Override
    public HighScoreDTO getHighScore(String levelName) throws HighScoreDAOException {
        if (highScoreDTOList == null)
            throw new HighScoreDAOException("Please load first.");
        Optional<HighScoreDTO> match = highScoreDTOList.stream().filter(highScoreDTO ->
                highScoreDTO.getLevelName().equals(levelName)).findFirst();
        return match.orElse(null);
    }

    @Override
    public void addHighScore(HighScoreDTO dto) throws HighScoreDAOException {
        if (highScoreDTOList == null)
            throw new HighScoreDAOException("Please load first.");
        highScoreDTOList.add(dto);
    }

    @Override
    public void removeScore(HighScoreDTO dto) throws HighScoreDAOException {
        if (highScoreDTOList == null)
            throw new HighScoreDAOException("Please load first.");
        highScoreDTOList.remove(dto);
    }

    @Override
    public void removeScore(int index) throws HighScoreDAOException {
        if (highScoreDTOList == null)
            throw new HighScoreDAOException("Please load first.");
        highScoreDTOList.remove(index);
    }

    @Override
    public List<HighScoreDTO> getScores() throws HighScoreDAOException {
        if (highScoreDTOList == null)
            throw new HighScoreDAOException("Please load first.");
        return highScoreDTOList;
    }

    private void createNewScoreBinFile() throws HighScoreDAOException {
        logHandler.log("Creating empty binary score file '" + textHandler.SCORE_BIN_FILE_NAME + "'", LogHandler.LogLevel.INFO, true);
        highScoreDTOList = new ArrayList<>();
        saveHighScoreList();
    }

}