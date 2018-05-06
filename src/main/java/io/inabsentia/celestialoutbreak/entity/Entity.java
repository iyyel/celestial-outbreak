package io.inabsentia.celestialoutbreak.entity;

import java.awt.*;

/**
 * An Entity is defined as an object that can be seen on the screen.
 * Therefore an entity has a position, a width and height and a colour
 * assigned to it.
 * <p>
 * An Entity is immobile.
 * See @MobileEntity for more information on movable entities.
 */
public abstract class Entity {

    protected Point pos;
    protected Color color;

    protected int width;
    protected int height;

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
     * that this Entity forms onto the screen. This is used
     * for collision detection.
     *
     * @return Entity bounds.
     */
    public abstract Rectangle getBounds();

}