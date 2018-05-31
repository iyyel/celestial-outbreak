import io.inabsentia.celestialoutbreak.data.dao.IPlayerDAO;
import io.inabsentia.celestialoutbreak.data.dao.PlayerDAO;

public class PlayerDAOTestDriver {

    private static final IPlayerDAO playerDAO = PlayerDAO.getInstance();

    public static void main(String[] args) {

        try {
            playerDAO.loadPlayerList();
            // Do stuff
            playerDAO.savePlayerList();
        } catch (IPlayerDAO.PlayerDAOException e) {
            e.printStackTrace();
        }

    }

}
