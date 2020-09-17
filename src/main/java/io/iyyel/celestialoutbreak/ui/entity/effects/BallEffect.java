package io.iyyel.celestialoutbreak.ui.entity.effects;

import io.iyyel.celestialoutbreak.ui.entity.AbstractEntity.Shape;

import java.awt.*;

public final class BallEffect extends Effect {

    private final Dimension dim;
    private final Shape shape;
    private final Color col;
    private final int speed;

    public BallEffect(int duration, Dimension dim, Shape shape, Color color, int speed,
                      String spawnSoundFileName, String collideSoundFileName) {
        super(duration);
        this.dim = dim;
        this.shape = shape;
        this.col = color;
        this.speed = speed;
        this.spawnSoundFileName = spawnSoundFileName;
        this.collideSoundFileName = collideSoundFileName;
    }

    public Dimension getDim() {
        return dim;
    }

    public Shape getShape() {
        return shape;
    }

    public Color getColor() {
        return col;
    }

    public int getSpeed() {
        return speed;
    }

}