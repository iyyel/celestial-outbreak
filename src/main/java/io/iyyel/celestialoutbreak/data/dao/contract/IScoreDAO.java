package io.iyyel.celestialoutbreak.data.dao.contract;

public interface IScoreDAO {

    class ScoreDAOException extends Exception {
        public ScoreDAOException(String msg) {
            super(msg);
        }
    }

}