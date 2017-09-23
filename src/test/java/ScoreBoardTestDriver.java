import io.inabsentia.celestialoutbreak.data.dao.IScoreBoardDAO.DALException;
import io.inabsentia.celestialoutbreak.data.dao.ScoreBoardDAO;
import io.inabsentia.celestialoutbreak.data.dto.ScoreBoardDTO;

import java.util.List;

public class ScoreBoardTestDriver {

    public static void main(String[] args) {

        ScoreBoardDAO sbDAO = new ScoreBoardDAO();

        try {
            List<ScoreBoardDTO> sbList = sbDAO.getScoreBoardList();
            for (ScoreBoardDTO sbDTO : sbList) System.out.println(sbDTO);
            int length = sbDAO.getScoreBoardList().size();
            System.out.println("Length: " + length);
        } catch (DALException e) {
            e.printStackTrace();
        }

        System.out.println("###############################################");

        try {
            ScoreBoardDTO sbDTO23 = sbDAO.getScoreBoard(23);
            System.out.println("Got: " + sbDTO23);
        } catch (DALException e) {
            e.printStackTrace();
        }

        System.out.println("###############################################");

        ScoreBoardDTO newSbDTO = new ScoreBoardDTO(2342, 234, "mars");

        try {
            sbDAO.createScoreBoard(newSbDTO);
            System.out.println("Got: " + sbDAO.getScoreBoard(newSbDTO.getSbId()));


            ScoreBoardDTO newUpdSbDTO = new ScoreBoardDTO(newSbDTO.getSbId(), 999, "venus");
            sbDAO.updateScoreBoard(newUpdSbDTO);
            System.out.println("Got updated: " + sbDAO.getScoreBoard(newUpdSbDTO.getSbId()));

            sbDAO.deleteScoreBoard(newSbDTO.getSbId());
            System.out.println("Got deleted: " + sbDAO.getScoreBoard(newUpdSbDTO.getSbId()));

        } catch (DALException e) {
            e.printStackTrace();
        }

    }

}