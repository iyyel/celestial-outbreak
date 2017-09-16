package io.inabsentia.celestialoutbreak.data.dao;

import io.inabsentia.celestialoutbreak.data.IConnector;
import io.inabsentia.celestialoutbreak.data.IConnector.DALException;
import io.inabsentia.celestialoutbreak.data.dto.ScoreBoardDTO;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class ScoreBoardDAO implements IScoreBoardDAO {

    private final IConnector dbConnector;

    public ScoreBoardDAO(IConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    @Override
    public ScoreBoardDTO getScoreBoard(int sbId) throws DALException {
        Document doc = dbConnector.getDocument(sbId);
        return new ScoreBoardDTO(doc.getInteger("_id"), doc.getInteger("levelScore"), doc.getString("levelType"));
    }

    @Override
    public List<ScoreBoardDTO> getScoreBoardList() throws DALException {
        List<ScoreBoardDTO> sbList = new ArrayList<>();
        List<Document> docList = dbConnector.getDocumentList();

        for (Document doc : docList) sbList.add(new ScoreBoardDTO(doc.getInteger("_id"), doc.getInteger("levelScore"), doc.getString("levelType")));

        return sbList;
    }

    @Override
    public void createScoreBoard(ScoreBoardDTO sbDTO) throws DALException {
        Document doc = fillDocument(sbDTO);
        dbConnector.createDocument(doc);
    }

    @Override
    public void updateScoreBoard(ScoreBoardDTO sbDTO) throws DALException {
        Document doc = fillDocument(sbDTO);
        dbConnector.updateDocument(doc);
    }

    @Override
    public void deleteScoreBoard(int sbId) throws DALException {
        dbConnector.deleteDocument(sbId);
    }

    private Document fillDocument(ScoreBoardDTO sbDTO) {
        Document doc = new Document();
        doc.put("_id", sbDTO.getSbId());
        doc.put("levelScore", sbDTO.getSbLevelScore());
        doc.put("levelType", sbDTO.getSbLevelType());
        return doc;
    }

}