package io.inabsentia.celestialoutbreak.entity;

import java.awt.*;

/**
 * The Block is a specific Entity that can be drawn onto the screen.
 * It's a going to be drawn as an Rectangle, of which the player can hit
 * with the ball for it to disappear and get points.
 * The Block should be an immobile object, so therefore only extends Entity.
 * <p>
 * The Block is not going to be directly used, but used in another object called a
 * BlockList, where a list of these block resides.
 */
public final class Block extends Entity {

    /**
     * Default constructor.
     *
     * @param pos    Current Position of the Block.
     * @param width  Width of the Block.
     * @param height Height of the Block.
     * @param color  Color of the Block.
     */
    public Block(Point pos, int width, int height, Color color) {
        super(pos, width, height, color);
    }

    /**
     * Draws the @Block onto the screen.
     *
     * @param g Graphics object used to render this Entity.
     */
    @Override
    public void render(Graphics2D g) {
        g.setColor(color);
        g.fillRect(pos.x, pos.y, width, height);
    }

    /**
     * This is used to check if the Ball collides
     * with the Block.
     *
     * @return Rectangle using the Block's bounds.
     */
    @Override
    public Rectangle getBounds() {
        return new Rectangle(pos.x, pos.y, width, height);
    }

}