package io.iyyel.celestialoutbreak.entity;

import java.awt.*;

public final class PowerUp extends MobileEntity {

    /**
     * Default constructor.
     *
     * @param pos    Current position of the Entity.
     * @param width  Width of the Entity.
     * @param height Height of the Entity.
     * @param color  Color of the Entity.
     * @param speed  Speed of the Entity.
     */
    public PowerUp(Point pos, int width, int height, Color color, int speed) {
        super(pos, width, height, color, speed);
    }

    public void update() {
        pos.y += 1;
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(color);
        g.fillRect(pos.x, pos.y, width, height);
    }

}