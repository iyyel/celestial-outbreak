package io.inabsentia.celestialoutbreak.data.dao;

import io.inabsentia.celestialoutbreak.data.IConnector;
import io.inabsentia.celestialoutbreak.data.dto.ScoreBoardDTO;

import java.io.*;
import java.util.List;

public class LocalScoreBoardDAO implements IScoreBoardDAO {

    private List<ScoreBoardDTO> scoreBoardList;

    public LocalScoreBoardDAO() {
        try {
            scoreBoardList = loadScoreBoardBinary();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        for (ScoreBoardDTO dto : scoreBoardList) {
            System.out.println(dto);
        }

    }

    @Override
    public ScoreBoardDTO getScoreBoard(int sbId) throws IConnector.DALException {
        return null;
    }

    @Override
    public List<ScoreBoardDTO> getScoreBoardList() throws IConnector.DALException {
        return null;
    }

    @Override
    public void createScoreBoard(ScoreBoardDTO sbDTO) throws IConnector.DALException {

    }

    @Override
    public void updateScoreBoard(ScoreBoardDTO sbDTO) throws IConnector.DALException {

    }

    @Override
    public void deleteScoreBoard(int sbId) throws IConnector.DALException {

    }

    private List<ScoreBoardDTO> loadScoreBoardBinary() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("scoreboard.bin"));
        return (List<ScoreBoardDTO>) ois.readObject();
    }

    private void writeScoreBoardBinary() throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("scoreboard.bin"));
        oos.writeObject(scoreBoardList);
        oos.close();
    }

}
