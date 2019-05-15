package io.iyyel.celestialoutbreak.entity;

import java.awt.*;

public final class Block extends Entity {

    private int hitpoints;

    public Block(Point pos, Dimension dim, int hitpoints, Color color) {
        super(pos, dim, color);
        this.hitpoints = hitpoints;
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(color);
        g.fillRect(pos.x, pos.y, dim.width, dim.height);
    }

    @Override
    public void update() {

    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(pos.x, pos.y, dim.width, dim.height);
    }

    public void decHitPoints() {
        if (hitpoints > 0) {
            hitpoints -= 1;
        }
    }

    public boolean isDead() {
        return hitpoints == 0;
    }

    public int getHitPoints() {
        return hitpoints;
    }

}