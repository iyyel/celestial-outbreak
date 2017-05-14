package io.inabsentia.celestialoutbreak.game.entity;

/**
 * Singleton class, since there is only one player
 * at a time of the running game instance.
 */
public class Player {

    private static final Player instance = new Player();

    private String name;
    private int lives;
    private int score;

    private Player() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public synchronized static Player getInstance() {
        return instance;
    }

}