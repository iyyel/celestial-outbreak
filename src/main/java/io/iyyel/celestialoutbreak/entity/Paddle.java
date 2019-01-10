package io.iyyel.celestialoutbreak.entity;

import io.iyyel.celestialoutbreak.controller.GameController;

import java.awt.*;

/**
 * This is the @MobileEntity object, @Paddle that is controlled by the player.
 * It is used to collide with the @Ball, receive power ups, etc.
 * <p>
 * The @Paddle inherits from the @MobileEntity class because it has to move
 * left and right on the screen.
 * <p>
 * Features of the paddle can be adjusted with in the individual level options.
 */
public final class Paddle extends MobileEntity {

    private final int PADDLE_PAUSE_SCREEN_TIMER_INITIAL = 120;
    private int paddlePauseTimer = 0;

    /**
     * Default constructor.
     *
     * @param pos            Initial position of the @Paddle.
     * @param width          Width of the @Paddle.
     * @param height         Height of the @Paddle.
     * @param speed          Speed of the @Paddle.
     * @param color          Color of the @Paddle.
     * @param gameController Current game instance.
     */
    public Paddle(Point pos, int width, int height, int speed,
                  Color color, GameController gameController) {
        super(pos, width, height, color, speed, gameController);
    }

    /**
     * Updates the position of the @Paddle object.
     *
     * @param left  Move left, if true.
     * @param right Move right, if true.
     */
    public void update(boolean left, boolean right) {
        if (paddlePauseTimer == 0) {
            if (left && pos.x > 0) {
                pos.x -= speed;
            }

            if (right && pos.x <= gameController.getWidth() - width) {
                pos.x += speed;
            }
        } else {
            paddlePauseTimer--;
        }
    }

    /**
     * Renders the @Paddle object onto the screen.
     *
     * @param g Graphics object used to render this Entity.
     */
    @Override
    public void render(Graphics2D g) {
        g.setColor(color);
        g.fillRect(pos.x, pos.y, width, height);
    }

    /**
     * This is used to check if the @Paddle collides
     * with the @Ball.
     *
     * @return Rectangle using the the @Paddle objects bounds.
     */
    @Override
    public Rectangle getBounds() {
        return new Rectangle(pos.x, pos.y, width, height);
    }

    public void pause() {
        paddlePauseTimer = PADDLE_PAUSE_SCREEN_TIMER_INITIAL;
    }

}