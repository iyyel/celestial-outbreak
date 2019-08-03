package io.iyyel.celestialoutbreak.data.dto;

import java.io.Serializable;

public final class HighScoreDTO implements Serializable {

    private String player;
    private String levelName;
    private int score;
    private int time;

    public HighScoreDTO(String player, String levelName, int score, int time) {
        this.player = player;
        this.levelName = levelName;
        this.score = score;
        this.time = time;
    }

    public String getPlayer() {
        return player;
    }

    public String getLevelName() {
        return levelName;
    }

    public int getScore() {
        return score;
    }

    public int getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "ScoreDTO{" +
                "player='" + player + '\'' +
                ", levelName='" + levelName + '\'' +
                ", score=" + score +
                ", time=" + time +
                '}';
    }

}