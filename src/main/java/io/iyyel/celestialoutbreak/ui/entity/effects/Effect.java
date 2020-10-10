package io.iyyel.celestialoutbreak.ui.entity.effects;

import io.iyyel.celestialoutbreak.handler.LevelHandler;

public abstract class Effect {

    private final LevelHandler levelHandler = LevelHandler.getInstance();

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
        startTime = levelHandler.getActiveLevel().getLevelTimer().getSecondsElapsed();
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