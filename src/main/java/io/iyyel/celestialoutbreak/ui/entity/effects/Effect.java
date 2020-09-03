package io.iyyel.celestialoutbreak.ui.entity.effects;

import io.iyyel.celestialoutbreak.util.Util;

public abstract class Effect {

    private final Util util = Util.getInstance();

    // effect duration in seconds
    protected final int duration;
    protected boolean active = false;
    protected long startTime = 0;
    protected String spawnSoundFileName;
    protected String collideSoundFileName;

    public Effect(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public boolean isActive() {
        return active;
    }

    public void activate() {
        startTime = util.getTimeElapsed();
        active = true;
    }

    public void deactivate() {
        startTime = 0;
        active = false;
    }

    public long getStartTime() {
        return startTime;
    }

    public String getSpawnSoundFileName() {
        return spawnSoundFileName;
    }

    public String getCollideSoundFileName() {
        return collideSoundFileName;
    }

}