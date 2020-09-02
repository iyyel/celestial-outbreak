package io.iyyel.celestialoutbreak.data.dto;

import java.io.Serializable;

public final class HighScoreDTO implements Serializable {

    private final String player;
    private final String levelName;
    private final long score;
    private final long time;
    private boolean powerUpEnabled;

    public HighScoreDTO(String player, String levelName, long score, long time, boolean powerUpEnabled) {
        this.player = player;
        this.levelName = levelName;
        this.score = score;
        this.time = time;
        this.powerUpEnabled = powerUpEnabled;
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

    public boolean getPowerUpEnabled() {
        return powerUpEnabled;
    }

}