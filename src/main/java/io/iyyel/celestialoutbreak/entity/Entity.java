package io.iyyel.celestialoutbreak.entity;

import java.awt.*;

/**
 * An Entity is defined as an object that can be seen on the screen.
 * Therefore an entity has a position, a width and height and a colour
 * assigned to it. It can not move around the screen.
 */
public abstract class Entity {

    protected Point pos;

    protected int width;
    protected int height;

    protected Color color;

    /**
     * Default constructor.
     *
     * @param pos    Current position of the Entity.
     * @param width  Width of the Entity.
     * @param height Height of the Entity.
     * @param color  Color of the Entity.
     */
    public Entity(Point pos, int width, int height, Color color) {
        this.pos = pos;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    /**
     * This method renders the Entity to the screen.
     *
     * @param g Graphics object used to render this Entity.
     */
    public abstract void render(Graphics2D g);

    /**
     * This method returns the bounds, as in the Rectangle
     * that this Entity forms onto the screen. This is mainly
     * used for collision detection.
     *
     * @return Rectangle with Entity bounds.
     */
    protected Rectangle getBounds() {
        return new Rectangle(pos.x, pos.y, width, height);
    }

}