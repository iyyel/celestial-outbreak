package io.iyyel.celestialoutbreak.ui.entity;

import java.awt.*;

public abstract class AbstractMovableEntity extends AbstractEntity {

    protected enum Direction {
        LEFT,
        RIGHT,
        UP,
        DOWN
    }

    protected int speed;

    public AbstractMovableEntity(Point pos, Dimension dim, Shape shape, Color col, int speed) {
        super(pos, dim, shape, col);
        this.speed = speed;
    }

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
        }
    }

    public void fixCollision(AbstractEntity entity) {
        // found out where this entity is located according to 'entity.' up, down, left or right
        Point ttlPos = (Point) pos.clone();
        Point ttrPos = new Point(pos.x + dim.width, pos.y);
        Point tblPos = new Point(pos.x, pos.y + dim.height);
        Point tbrPos = new Point(pos.x + dim.width, pos.y + dim.height);

        Point etlPos = (Point) entity.pos.clone();
        Point etrPos = new Point(entity.pos.x + entity.dim.width, entity.pos.y);
        Point eblPos = new Point(entity.pos.x, entity.pos.y + entity.dim.height);
        Point ebrPos = new Point(entity.pos.x + entity.dim.width, entity.pos.y + entity.dim.height);

        if (ttlPos.x < etlPos.x && tblPos.x < eblPos.x) { // this is left of entity
            int deltaX = Math.abs((ttlPos.x + dim.width) - etlPos.x);
            pos.x -= deltaX;
        } else if (ttrPos.x > etrPos.x && tbrPos.x > ebrPos.x) { // this is right of entity
            int deltaX = Math.abs((etlPos.x + entity.dim.width) - ttlPos.x);
            pos.x += deltaX;
        } else if (pos.y < entity.pos.y) {   // this is top of entity
            int deltaY = Math.abs((pos.y + dim.height) - entity.pos.y);
            pos.y -= deltaY;
        } else if (pos.y > entity.pos.y) { // this is bottom of entity
            int deltaY = Math.abs((entity.pos.y + entity.dim.height) - pos.y);
            pos.y += deltaY;
        }
    }

}