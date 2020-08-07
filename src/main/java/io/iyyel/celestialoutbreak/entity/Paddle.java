package io.iyyel.celestialoutbreak.entity;

import java.awt.*;

/**
 * This is the MobileEntity object, Paddle that is controlled by the player.
 * It is used to collide with the Ball entity, receive power ups, etc.
 * <p>
 * The Paddle inherits from the MobileEntity class because it will move
 * left and right on the screen.
 */
public final class Paddle extends MobileEntity {

    private final int screenWidth;

    /**
     * Default constructor.
     *
     * @param pos    Initial position of the Paddle.
     * @param width  Width of the Paddle.
     * @param height Height of the Paddle.
     * @param speed  Speed of the Paddle.
     * @param color  Color of the Paddle.
     */
    public Paddle(Point pos, int width, int height, Color color, int speed, int screenWidth) {
        super(pos, width, height, color, speed);
        this.screenWidth = screenWidth;
    }

    /**
     * Updates the position of the Paddle entity.
     *
     * @param left  Move left, if true.
     * @param right Move right, if true.
     */
    public void update(boolean left, boolean right) {
        if (left && pos.x > 0) {
            pos.x -= speed;
        }

        if (right && pos.x <= screenWidth - width) {
            pos.x += speed;
        }
    }

    /**
     * Renders the Paddle entity onto the screen.
     *
     * @param g Graphics object used to render this Entity.
     */
    @Override
    public void render(Graphics2D g) {
        g.setColor(color);
        g.fillRect(pos.x, pos.y, width, height);
    }

}