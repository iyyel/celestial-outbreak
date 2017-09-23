package io.inabsentia.celestialoutbreak.data.dao;

import io.inabsentia.celestialoutbreak.data.dto.ScoreBoardDTO;

import java.io.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ScoreBoardDAO implements IScoreBoardDAO {

    private List<ScoreBoardDTO> scoreBoardList;
    private Comparator<ScoreBoardDTO> scoreBoardComparator;

    public ScoreBoardDAO() {
        try {
            scoreBoardList = loadScoreBoardBinary();
        } catch (DALException e) {
            e.printStackTrace();
        }

        scoreBoardComparator = new Comparator<ScoreBoardDTO>() {
            @Override
            public int compare(ScoreBoardDTO sbDTO1, ScoreBoardDTO sbDTO2) {
                return Integer.compare(sbDTO1.getSbId(), sbDTO2.getSbId());
            }
        };
    }

    @Override
    public ScoreBoardDTO getScoreBoard(int sbId) throws DALException {
        int index = Collections.binarySearch(scoreBoardList, new ScoreBoardDTO(sbId, 0, null), scoreBoardComparator);
        if (index < 1) throw new DALException("Id [" + sbId + "] not found!");
        return scoreBoardList.get(index);
    }

    @Override
    public List<ScoreBoardDTO> getScoreBoardList() throws DALException {
        return scoreBoardList;
    }

    @Override
    public void createScoreBoard(ScoreBoardDTO sbDTO) throws DALException {
        int index = Collections.binarySearch(scoreBoardList, sbDTO, scoreBoardComparator);
        if (index > 0) throw new DALException("Id [" + sbDTO.getSbId() + "] already exists!");

        scoreBoardList.add(sbDTO);
        sortScoreBoardList(scoreBoardList);
    }

    @Override
    public void updateScoreBoard(ScoreBoardDTO sbDTO) throws DALException {
        int index = Collections.binarySearch(scoreBoardList, sbDTO, scoreBoardComparator);
        if (index < 1) throw new DALException("Id [" + sbDTO.getSbId() + "] not found!");

        ScoreBoardDTO existingSb = getScoreBoard(sbDTO.getSbId());
        scoreBoardList.remove(existingSb);

        scoreBoardList.add(sbDTO);
        sortScoreBoardList(scoreBoardList);
    }

    @Override
    public void deleteScoreBoard(int sbId) throws DALException {
        ScoreBoardDTO existingSb = getScoreBoard(sbId);
        scoreBoardList.remove(existingSb);
        sortScoreBoardList(scoreBoardList);
    }

    private List<ScoreBoardDTO> loadScoreBoardBinary() throws DALException {
        ObjectInputStream ois = null;

        try {
            ois = new ObjectInputStream(new FileInputStream("scoreboard.bin"));
            return (List<ScoreBoardDTO>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new DALException(e.getMessage(), e);
        }
    }

    public void saveScoreBoardBinary() throws DALException {
        sortScoreBoardList(scoreBoardList);
        ObjectOutputStream oos = null;

        try {
            oos = new ObjectOutputStream(new FileOutputStream("scoreboard.bin"));
            oos.writeObject(scoreBoardList);
            oos.close();
        } catch (IOException e) {
            throw new DALException(e.getMessage(), e);
        }
    }

    private void sortScoreBoardList(List<ScoreBoardDTO> scoreBoardList) {
        Collections.sort(scoreBoardList, scoreBoardComparator);
    }

}
