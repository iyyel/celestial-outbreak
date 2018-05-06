package io.inabsentia.celestialoutbreak.entity;

import java.awt.*;

/**
 *
 */
public abstract class Entity {

    protected Point pos;

    protected int width;
    protected int height;

    protected Color color;

    /**
     *
     * @param pos
     * @param width
     * @param height
     * @param color
     */
    public Entity(Point pos, int width, int height, Color color) {
        this.pos = pos;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    /**
     *
     * @param g
     */
    public abstract void render(Graphics2D g);

    /**
     *
     * @return
     */
    public abstract Rectangle getBounds();

}