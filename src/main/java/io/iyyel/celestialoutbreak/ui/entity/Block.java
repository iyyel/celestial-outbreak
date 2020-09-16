package io.iyyel.celestialoutbreak.ui.entity;

import io.iyyel.celestialoutbreak.ui.interfaces.IEntityMortal;

import java.awt.*;

public final class Block extends AbstractMovableEntity implements IEntityMortal {

    private int hitPoints;

    public Block(Point pos, Dimension dim, Shape shape, Color col, int speed, int hitPoints) {
        super(pos, dim, shape, col, speed);
        this.hitPoints = hitPoints;
    }

    @Override
    public void update() {

    }

    @Override
    public void hit() {
        if (hitPoints > 0) {
            hitPoints--;
        }
    }

    @Override
    public boolean isAlive() {
        return hitPoints > 0;
    }

    @Override
    public int getHitPoints() {
        return hitPoints;
    }

}