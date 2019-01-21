package io.iyyel.celestialoutbreak.entity;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.handler.*;

import java.awt.*;

/**
 * The Ball is a MobileEntity that the @Paddle is used
 * to collide with. The Ball inherits from MobileEntity
 * because the Ball is going to move around the screen.
 * <p>
 * Ball is the entity that handles collisions. Since
 * we are talking about rather simple collision logic here,
 * this is doable. A better design could probably consist of a
 * collision controller or something like that.
 * <p>
 * Velocity is used to update the speed of the Ball, attempting
 * to give it a natural physical feel.
 */
public final class Ball extends MobileEntity {

    private final OptionsHandler optionsHandler = OptionsHandler.getInstance();
    private final TextHandler textHandler = TextHandler.getInstance();
    private final SoundHandler soundHandler = SoundHandler.getInstance();
    private final FileHandler fileHandler = FileHandler.getInstance();
    private final LevelHandler levelHandler = LevelHandler.getInstance();
    private final InputHandler inputHandler = InputHandler.getInstance();
    private final GameController gameController;

    private Point velocity;
    private boolean isStuck = false;

    private final int PADDLE_COLLISION_TIMER_INITIAL = 30;
    private final int BALL_PAUSE_SCREEN_TIMER_INITIAL = 120;
    private final int BALL_PAUSE_TIMER_INITIAL = 50;

    private int paddleCollisionTimer = 0;
    private int ballPauseTimer = BALL_PAUSE_TIMER_INITIAL;
    private int ballPosXOffset;
    private int ballPosYOffset;

    private SoundHandler.SoundClip ballHitClip = soundHandler.getSoundClip(textHandler.SOUND_FILE_NAME_BALL_HIT);
    private SoundHandler.SoundClip ballResetClip = soundHandler.getSoundClip(textHandler.SOUND_FILE_NAME_BALL_RESET);

    /**
     * Default constructor.
     *
     * @param pos            Current position of the Ball. (x, y)
     * @param width          Width of the Ball. (pixels)
     * @param height         Height of the Ball. (pixels)
     * @param color          Color of the Ball.
     * @param speed          Speed of the Ball.
     * @param ballPosXOffset Ball x-axis offset.
     * @param ballPosYOffset Ball y-axis offset.
     * @param gameController Current game instance.
     */
    public Ball(Point pos, int width, int height, Color color, int speed,
                int ballPosXOffset, int ballPosYOffset, GameController gameController) {
        super(pos, width, height, color, speed, gameController);
        this.ballPosXOffset = ballPosXOffset;
        this.ballPosYOffset = ballPosYOffset;
        this.gameController = gameController;

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
        if (ballPauseTimer == 0 && !isStuck) {
            pos.x += velocity.x;
            pos.y += velocity.y;
        } else if (ballPauseTimer > 0 && !isStuck) {
            ballPauseTimer--;
        } else if (isStuck) {
            pos = new Point(paddle.pos.x + (paddle.width / 2) - (width / 2), paddle.pos.y - (height));


            if (inputHandler.isUsePressed()) {
                isStuck = false;
                velocity.y = -speed;
            }

        }

        /* Check for collision. */
        checkCollision(paddle);
        checkCollision(blockList);

        /* Ball hit left x-axis. */
        if (pos.x < 0) {
            if (optionsHandler.isVerboseLogEnabled()) {
                fileHandler.writeLog(textHandler.vBallTouchedXAxisLeftMsg);
            }
            velocity.x = speed;
            ballHitClip.play(false);
        }

        /* Ball hit right x-axis. */
        if (pos.x > (gameController.getWidth() - width)) {
            if (optionsHandler.isVerboseLogEnabled()) {
                fileHandler.writeLog(textHandler.vBallTouchedXAxisRightMsg);
            }
            velocity.x = -speed;
            ballHitClip.play(false);
        }

        /* Ball hit top y-axis. */
        if (pos.y < 0) {
            if (optionsHandler.isVerboseLogEnabled()) {
                fileHandler.writeLog(textHandler.vBallTouchedYAxisTopMsg);
            }
            velocity.y = speed;
            ballHitClip.play(false);
        }

        /* Ball hit bottom y-axis. */
        if (pos.y > (gameController.getHeight() - height)) {
            if (optionsHandler.isGodModeEnabled()) {
                velocity.y = -speed;
                ballHitClip.play(false);
            } else {
                isStuck = true;

                // player lost a life
                levelHandler.getActiveLevel().decPlayerLife();

                pos = new Point(paddle.pos.x + (paddle.width / 2) - (width / 2), paddle.pos.y - (height));

                velocity.x = 0;
                velocity.y = 0;

                ballResetClip.play(false);

                if (optionsHandler.isVerboseLogEnabled()) {
                    fileHandler.writeLog(textHandler.vBallTouchedYAxisBottomMsg);
                    fileHandler.writeLog("Player lost a life. Life: " + levelHandler.getActiveLevel().getPlayerLife());
                }

            }
        }

    }

    /**
     * This is a generic method used to check if the ball collides with t.
     * t can either be a Paddle or a BlockList.
     * <p>
     * TODO: This can be cleaned up. Instead of using the actual BlockList, make it use
     * TODO: each individual block. That would make more logical sense, perhaps.
     * TODO: Perhaps find a better way to do this.
     *
     * @param t   Object to check collision with.
     * @param <T> Generic type.
     */
    private <T> void checkCollision(T t) {
        if (t instanceof Paddle && ((Paddle) t).getBounds().intersects(getBounds())) {
            if (paddleCollisionTimer == 0) {
                // var relativeIntersectY = (paddle1Y+(PADDLEHEIGHT/2)) - intersectY;
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
        } else if (t instanceof BlockList) {

            /* Cast t to a BlockList. */
            BlockList blockList = ((BlockList) t);

            for (int i = 0; i < blockList.getLength(); i++) {
                if (blockList.getBlock(i) != null && blockList.getBlock(i).getBounds().intersects(getBounds())) {
                    velocity.y *= -1;

                    // block was hit
                    blockList.getBlock(i).decHitPoints();

                    if (blockList.getBlock(i).isDead()) {
                        soundHandler.getSoundClip(textHandler.SOUND_FILE_NAME_BLOCK_DESTROYED).play(false);
                        blockList.destroyBlock(i);

                        if (optionsHandler.isVerboseLogEnabled()) {
                            fileHandler.writeLog("BlockList[" + i + "] has been destroyed.");
                        }
                    } else {
                        soundHandler.getSoundClip(textHandler.SOUND_FILE_NAME_BALL_HIT).play(false);

                        if (optionsHandler.isVerboseLogEnabled()) {
                            fileHandler.writeLog(textHandler.vBallBlockListCollisionMsg(i, blockList.getBlock(i).getHitPoints()));
                        }
                    }

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

    /**
     * This is used to check if the Ball collides
     * with the Block.
     *
     * @return Rectangle using the Ball's bounds.
     */
    @Override
    public Rectangle getBounds() {
        return new Rectangle(pos.x, pos.y, width, height);
    }

    public void pause() {
        ballPauseTimer = BALL_PAUSE_SCREEN_TIMER_INITIAL;
    }

}