package io.iyyel.celestialoutbreak.ui.entity;

import java.awt.*;

public final class Block extends AbstractEntity {

    private int health;
    private final Style style;

    public enum Style {
        CIRCLE,
        SQUARE
    }

    public Block(Point pos, Dimension dim, Color color, int health, Style style) {
        super(pos, dim, color);
        this.health = health;
        this.style = style;
    }

    @Override
    public void render(Graphics2D g) {
        if (isRenderStopped()) {
            return;
        }

        g.setColor(color);
        if (style.equals(Style.SQUARE)) {
            g.fillRect(pos.x, pos.y, dim.width, dim.height);
        } else if (style.equals(Style.CIRCLE)) {
            g.fillOval(pos.x, pos.y, dim.width, dim.height);
        }
    }

    public void hit() {
        if (health > 0) {
            health -= 1;
        }
    }

    public boolean isDead() {
        return health == 0;
    }

    public int getHealth() {
        return health;
    }

}