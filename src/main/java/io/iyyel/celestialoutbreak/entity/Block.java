package io.iyyel.celestialoutbreak.entity;

import java.awt.*;

/**
 * The Block is a specific Entity that can be drawn onto the screen.
 * It's going to be drawn as an Rectangle, of which the player can hit
 * with the Ball for it to disappear and get points and maybe a powerup.
 * The Block should be an immobile object, so therefore only extends Entity.
 * <p>
 * The Block is not going to be directly used, but used in another object called a
 * BlockList, where a list of these block resides.
 */
public final class Block extends Entity {

    private int health;

    /**
     * Default constructor.
     *
     * @param pos    Current Position of the Block.
     * @param width  Width of the Block.
     * @param height Height of the Block.
     * @param color  Color of the Block.
     */
    public Block(Point pos, int width, int height, Color color, int health) {
        super(pos, width, height, color);
        this.health = health;
    }

    @Override
    public void update(Object... arguments) {

    }

    /**
     * Draws the Block onto the screen.
     *
     * @param g Graphics object used to render this Entity.
     */
    @Override
    public void render(Graphics2D g) {
        g.setColor(color);
        g.fillRect(pos.x, pos.y, width, height);
    }

    /**
     * This method decreases the health of the Block
     * by one. For example, it if was hit by the ball.
     */
    public void hit() {
        if (health > 0) {
            health -= 1;
        }
    }

    /**
     * This method is used to check whether the Block
     * is "dead", i.e. when its health is at 0.
     */
    public boolean isDead() {
        return health == 0;
    }

}