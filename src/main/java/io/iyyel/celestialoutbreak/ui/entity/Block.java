package io.iyyel.celestialoutbreak.ui.entity;

import java.awt.*;

public final class Block extends AbstractEntity {

    private int health;

    public Block(Point pos, Dimension dim, Color color, int health) {
        super(pos, dim, color);
        this.health = health;
    }

    @Override
    public void update() {
        super.update();

        // if (isUpdateStopped()) {
        //     return;
        // }
    }

    @Override
    public void render(Graphics2D g) {
        if (isRenderStopped()) {
            return;
        }

        g.setColor(color);
        g.fillRect(pos.x, pos.y, dim.width, dim.height);
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