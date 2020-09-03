package io.iyyel.celestialoutbreak.ui.entity.effects;

import java.awt.*;

public final class PaddleEffect extends Effect {

    private final Dimension dim;
    private final Color color;
    private final int speed;

    public PaddleEffect(int duration, Dimension dim, Color color, int speed,
                        String spawnSoundFileName, String collideSoundFileName) {
        super(duration);
        this.dim = dim;
        this.color = color;
        this.speed = speed;
        this.spawnSoundFileName = spawnSoundFileName;
        this.collideSoundFileName = collideSoundFileName;
    }

    public Dimension getDim() {
        return dim;
    }

    public Color getColor() {
        return color;
    }

    public int getSpeed() {
        return speed;
    }

}