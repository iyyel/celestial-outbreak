import io.inabsentia.celestialoutbreak.data.Connector;
import io.inabsentia.celestialoutbreak.data.IConnector.DALException;
import io.inabsentia.celestialoutbreak.data.dao.IScoreBoardDAO;
import io.inabsentia.celestialoutbreak.data.dao.ScoreBoardDAO;
import io.inabsentia.celestialoutbreak.data.dto.ScoreBoardDTO;
import org.bson.Document;

import java.util.List;

public class DBConnectorTestDriver {

    private final static Connector dbConnector = Connector.getInstance();

    public static void main(String[] args) {

        ScoreBoardDTO sbDTO0 = new ScoreBoardDTO(1, 23, "Red");

        ScoreBoardDTO sbDTO1 = new ScoreBoardDTO(2, 254, "Green");

        ScoreBoardDTO sbDTO2 = new ScoreBoardDTO(3, 2343, "Blue");

        IScoreBoardDAO sbDAO = new ScoreBoardDAO(dbConnector);


        try {
            // Only use these 3 statements if you need to create the initial data.
            //sbDAO.createScoreBoard(sbDTO0);
            //sbDAO.createScoreBoard(sbDTO1);
            //sbDAO.createScoreBoard(sbDTO2);

            List<Document> docList = dbConnector.getDocumentList();
            for (Document doc : docList) System.out.println(doc);


            // update

            Document updatedDoc = new Document();
            updatedDoc.put("_id", 1);
            updatedDoc.put("levelScore", 20);
            updatedDoc.put("levelType", "blue");
            dbConnector.updateDocument(updatedDoc);

            dbConnector.deleteDocument(2);

            List<ScoreBoardDTO> scoreBoardList = sbDAO.getScoreBoardList();

            for (ScoreBoardDTO sbDTO : scoreBoardList) System.out.println(sbDTO);

        } catch (DALException e) {
            e.printStackTrace();
        }

    }

}