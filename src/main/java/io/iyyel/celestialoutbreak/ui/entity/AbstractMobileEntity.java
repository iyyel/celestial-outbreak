package io.iyyel.celestialoutbreak.ui.entity;

import java.awt.*;

public abstract class AbstractMobileEntity extends AbstractEntity {

    protected int speed;
    protected Point velocity;

    public AbstractMobileEntity(Point pos, Dimension dim, Shape shape, Color col, int speed) {
        super(pos, dim, shape, col);
        this.speed = speed;
        this.velocity = new Point(0, 0);
    }

    protected void setVelocity(int speed) {
        velocity.x = speed;
        velocity.y = speed;
    }

    protected void setVelocity(Point vel) {
        velocity.x = vel.x;
        velocity.y = vel.y;
    }

    protected void updateVelocity(int speed) {
        if (velocity.x < 0) {
            velocity.x = -speed;
        } else {
            velocity.x = speed;
        }

        if (velocity.y < 0) {
            velocity.y = -speed;
        } else {
            velocity.y = speed;
        }
    }

}