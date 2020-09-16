package io.iyyel.celestialoutbreak.ui.entity;

import io.iyyel.celestialoutbreak.ui.interfaces.IEntityMovable;

import java.awt.*;

public abstract class AbstractMovableEntity extends AbstractEntity implements IEntityMovable {

    protected int speed;

    public AbstractMovableEntity(Point pos, Dimension dim, Shape shape, Color col, int speed) {
        super(pos, dim, shape, col);
        this.speed = speed;
    }

    @Override
    public void move(Direction dir) {
        switch (dir) {
            case LEFT:
                pos.x -= speed;
                break;
            case RIGHT:
                pos.x += speed;
                break;
            case UP:
                pos.y -= speed;
                break;
            case DOWN:
                pos.y += speed;
                break;
            default:
                break;
        }
    }

}