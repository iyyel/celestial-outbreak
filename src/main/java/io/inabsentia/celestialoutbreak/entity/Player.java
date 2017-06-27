package io.inabsentia.celestialoutbreak.entity;

public class Player {

    private static Player instance;

    private String playerName;
    private int playerLives;
    private int playerScore;
    private int playerCurrentLevelIndex;

    private Player() {

    }

    static {
        try {
            instance = new Player();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static synchronized Player getInstance() {
        return instance;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getPlayerLives() {
        return playerLives;
    }

    public void setPlayerLives(int playerLives) {
        this.playerLives = playerLives;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public void setPlayerScore(int playerScore) {
        this.playerScore = playerScore;
    }

    public int getPlayerCurrentLevelIndex() {
        return playerCurrentLevelIndex;
    }

    public void setPlayerCurrentLevelIndex(int playerCurrentLevelIndex) {
        this.playerCurrentLevelIndex = playerCurrentLevelIndex;
    }

}