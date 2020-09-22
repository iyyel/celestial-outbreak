package io.iyyel.celestialoutbreak.ui.entity;

import java.awt.*;

public final class Block extends AbstractMobileEntity {

    private int hitPoints;

    public Block(Point pos, Dimension dim, Shape shape, Color col, int speed, int hitPoints) {
        super(pos, dim, shape, col, speed);
        this.hitPoints = hitPoints;
    }

    @Override
    public void update() {

    }

    public void hit() {
        if (hitPoints > 0) {
            hitPoints--;
        }
    }

    public boolean isAlive() {
        return hitPoints > 0;
    }

    public int getHitPoints() {
        return hitPoints;
    }

}