package io.iyyel.celestialoutbreak.entity;

import java.awt.*;

/**
 * The MobileEntity is an Entity that is able to move
 * around the screen. It therefore inherits from the Entity
 * class, with an added speed variable.
 */
public abstract class MobileEntity extends Entity {

    protected int speed;

    /**
     * Default constructor.
     *
     * @param pos    Current position of the Entity.
     * @param width  Width of the Entity.
     * @param height Height of the Entity.
     * @param color  Color of the Entity.
     * @param speed  Speed of the Entity.
     */
    public MobileEntity(Point pos, int width, int height, Color color, int speed) {
        super(pos, width, height, color);
        this.speed = speed;
    }

}