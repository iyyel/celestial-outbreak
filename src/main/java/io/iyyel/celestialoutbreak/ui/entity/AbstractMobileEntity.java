package io.iyyel.celestialoutbreak.ui.entity;

import java.awt.*;

public abstract class AbstractMobileEntity extends AbstractEntity {

    protected int speed;

    public AbstractMobileEntity(Point pos, Dimension dim, Color color, int speed) {
        super(pos, dim, color);
        this.speed = speed;
    }
    
}