package io.iyyel.celestialoutbreak.data.dto;

import java.io.Serializable;

public final class HighScoreDTO implements Serializable {

    private String player;
    private String levelName;
    private long score;
    private long time;

    public HighScoreDTO(String player, String levelName, long score, long time) {
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

    public long getScore() {
        return score;
    }

    public long getTime() {
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