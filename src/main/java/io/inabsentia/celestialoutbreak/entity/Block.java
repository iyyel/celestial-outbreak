package io.inabsentia.celestialoutbreak.entity;

import java.awt.*;

public class Block extends Entity {

    public Block(Point pos, int width, int height, Color color) {
        super(pos, width, height, color);
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(color);
        g.fillRect(pos.x, pos.y, width, height);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(pos.x, pos.y, width, height);
    }

}