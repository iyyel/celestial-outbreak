package io.iyyel.celestialoutbreak.entity;

import io.iyyel.celestialoutbreak.handler.FileHandler;
import io.iyyel.celestialoutbreak.handler.OptionsHandler;
import io.iyyel.celestialoutbreak.handler.SoundHandler;
import io.iyyel.celestialoutbreak.handler.TextHandler;

import java.awt.*;
import java.util.Random;

/**
 * The Ball is a MobileEntity that the Paddle is used
 * to collide with. The Ball inherits from MobileEntity
 * because the Ball is going to move around the screen.
 * <p>
 * Ball is the entity that handles collisions. Since
 * we are talking about rather simple collision logic here,
 * this is doable.
 * <p>
 * Velocity is used to update the speed of the Ball.
 */
public final class Ball extends MobileEntity {

    private final OptionsHandler optionsHandler = OptionsHandler.getInstance();
    private final TextHandler textHandler = TextHandler.getInstance();
    private final SoundHandler soundHandler = SoundHandler.getInstance();
    private final FileHandler fileHandler = FileHandler.getInstance();

    private final Random random = new Random();
    private final Point velocity;

    private final int PADDLE_COLLISION_TIMER_INITIAL = 20;
    private final int BALL_STALE_TIMER_INITIAL = 40;

    private int paddleCollisionTimer = 0;
    private int ballStaleTimer = BALL_STALE_TIMER_INITIAL;
    private final int posXOffset;
    private final int posYOffset;
    private final int screenWidth;
    private final int screenHeight;

    /**
     * Default constructor.
     *
     * @param pos        Current position of the Ball. (x, y)
     * @param width      Width of the Ball. (pixels)
     * @param height     Height of the Ball. (pixels)
     * @param color      Color of the Ball.
     * @param speed      Speed of the Ball.
     * @param posXOffset Ball x-axis offset.
     * @param posYOffset Ball y-axis offset.
     */
    public Ball(Point pos, int width, int height, Color color, int speed, int posXOffset, int posYOffset,
                int screenWidth, int screenHeight) {
        super(pos, width, height, color, speed);
        this.posXOffset = posXOffset;
        this.posYOffset = posYOffset;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        velocity = new Point(speed, speed);
    }

    /**
     * This is the update method of the Ball.
     * It should be run roughly every 30, 60, 120 times a second.
     * <p>
     * The method checks for collision between the Blocks inside the BlockList
     * and the Ball, as well as checking whether the Ball has gone out of the screen
     * or not.
     *
     * @param paddle    The current Paddle of the game.
     * @param blockList The current BlockList of the game.
     */
    public void update(Paddle paddle, BlockList blockList) {
        if (ballStaleTimer == 0) {
            pos.x += velocity.x;
            pos.y += velocity.y;
        } else {
            ballStaleTimer--;
        }

        /* Check for collision. */
        checkPaddleCollision(paddle);
        checkBlockCollision(blockList);

        /* Ball hit left x-axis. */
        if (pos.x < 0) {
            if (optionsHandler.isVerboseLogEnabled()) {
                fileHandler.writeLog(textHandler.vBallTouchedXAxisLeftMsg);
            }
            velocity.x = speed;
            soundHandler.getSoundClip(textHandler.SOUND_FILE_NAME_BALL_HIT).play(false);
        }

        /* Ball hit right x-axis. */
        if (pos.x > (screenWidth - width)) {
            if (optionsHandler.isVerboseLogEnabled()) {
                fileHandler.writeLog(textHandler.vBallTouchedXAxisRightMsg);
            }
            velocity.x = -speed;
            soundHandler.getSoundClip(textHandler.SOUND_FILE_NAME_BALL_HIT).play(false);
        }

        /* Ball hit top y-axis. */
        if (pos.y < 0) {
            if (optionsHandler.isVerboseLogEnabled()) {
                fileHandler.writeLog(textHandler.vBallTouchedYAxisTopMsg);
            }
            velocity.y = speed;
            soundHandler.getSoundClip(textHandler.SOUND_FILE_NAME_BALL_HIT).play(false);
        }

        /* Ball hit bottom y-axis. */
        if (pos.y > (screenHeight - height)) {
            if (optionsHandler.isVerboseLogEnabled()) {
                fileHandler.writeLog(textHandler.vBallTouchedYAxisBottomMsg);
            }

            pos = new Point((screenWidth / 2) - posXOffset, (screenHeight / 2) - posYOffset);

            boolean isPositiveValue = random.nextBoolean();
            int ballSpeedDecrement = random.nextInt(speed);

            velocity.x = (isPositiveValue ? 1 : -1) * speed + (isPositiveValue ? -ballSpeedDecrement : ballSpeedDecrement);
            velocity.y = speed;

            soundHandler.getSoundClip(textHandler.SOUND_FILE_NAME_BALL_RESET).play(false);
            ballStaleTimer = BALL_STALE_TIMER_INITIAL;
        }
    }

    /**
     * This method is used to check if the Ball has had
     * a collision with the paddle.
     * <p>
     *
     * @param paddle The paddle to check for collision with the ball.
     */
    private void checkPaddleCollision(Paddle paddle) {
        if (paddle.getBounds().intersects(getBounds())) {
            if (paddleCollisionTimer == 0) {
                velocity.y *= -1;

                if (velocity.x < 0) {
                    velocity.x = -speed;
                } else {
                    velocity.x = speed;
                }

                paddleCollisionTimer = PADDLE_COLLISION_TIMER_INITIAL;

                soundHandler.getSoundClip(textHandler.SOUND_FILE_NAME_BALL_HIT).play(false);

                if (optionsHandler.isVerboseLogEnabled()) {
                    fileHandler.writeLog(textHandler.vBallPaddleCollisionMsg(paddleCollisionTimer));
                }
            }
        }
    }

    /**
     * This method is used to check if the Ball has had
     * a collision with a block.
     * <p>
     *
     * @param blockList The list of blocks to check for collisions.
     */
    private void checkBlockCollision(BlockList blockList) {
        for (int i = 0; i < blockList.getLength(); i++) {
            if (blockList.getBlock(i) != null && blockList.getBlock(i).getBounds().intersects(getBounds())) {
                velocity.y *= -1;

                // block was hit
                blockList.getBlock(i).hit();

                if (blockList.getBlock(i).isDead()) {
                    blockList.removeBlock(i);
                }

                soundHandler.getSoundClip(textHandler.SOUND_FILE_NAME_BALL_HIT).play(false);

                if (optionsHandler.isVerboseLogEnabled()) {
                    fileHandler.writeLog(textHandler.vBallBlockListCollisionMsg(i));
                }
            }
        }
    }

    /**
     * Draws the Ball onto the screen.
     *
     * @param g Graphics object used to render this Entity.
     */
    @Override
    public void render(Graphics2D g) {
        g.setColor(color);
        g.fillOval(pos.x, pos.y, width, height);

        if (paddleCollisionTimer > 0) {
            paddleCollisionTimer--;
        }
    }

}