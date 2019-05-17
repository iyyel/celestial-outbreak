package io.iyyel.celestialoutbreak.ui.entity;

import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.handler.*;

import java.awt.*;

public final class Ball extends AbstractMobileEntity {

    private final OptionsHandler optionsHandler = OptionsHandler.getInstance();
    private final TextHandler textHandler = TextHandler.getInstance();
    private final SoundHandler soundHandler = SoundHandler.getInstance();
    private final LogHandler logHandler = LogHandler.getInstance();
    private final LevelHandler levelHandler = LevelHandler.getInstance();
    private final InputHandler inputHandler = InputHandler.getInstance();
    private final GameController gameController;

    private Point velocity;
    private boolean isStuck = true;

    private final int PADDLE_COLLISION_TIMER_INITIAL = 30;
    private final int BALL_PAUSE_SCREEN_TIMER_INITIAL = 120;

    private int paddleCollisionTimer = 0;
    private int ballPauseTimer = 0;

    private SoundHandler.SoundClip ballHitClip = soundHandler.getSoundClip(textHandler.SOUND_FILE_NAME_BALL_HIT);
    private SoundHandler.SoundClip ballResetClip = soundHandler.getSoundClip(textHandler.SOUND_FILE_NAME_BALL_RESET);

    private final Paddle paddle;
    private final BlockList blockList;

    public Ball(Point pos, int size, Color color, int speed, GameController gameController, Paddle paddle, BlockList blockList) {
        super(pos, new Dimension(size, size), color, speed, gameController);
        this.gameController = gameController;
        this.paddle = paddle;
        this.blockList = blockList;
        velocity = new Point(0, 0);
    }

    public void update() {
        if (ballPauseTimer == 0 && !isStuck) {
            pos.x += velocity.x;
            pos.y += velocity.y;
        } else if (ballPauseTimer > 0 && !isStuck) {
            ballPauseTimer--;
        } else if (isStuck) {
            pos = new Point(paddle.pos.x + (paddle.dim.width / 2) - (dim.width / 2), paddle.pos.y - (dim.height));

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
            logHandler.log(textHandler.vBallTouchedXAxisLeftMsg, LogHandler.LogLevel.INFORMATION, true);
            velocity.x = speed;
            ballHitClip.play(false);
        }

        /* Ball hit right x-axis. */
        if (pos.x > (gameController.getWidth() - dim.width)) {
            logHandler.log(textHandler.vBallTouchedXAxisRightMsg, LogHandler.LogLevel.INFORMATION, true);
            velocity.x = -speed;
            ballHitClip.play(false);
        }

        /* Ball hit top y-axis. */
        if (pos.y < 0) {
            logHandler.log(textHandler.vBallTouchedYAxisTopMsg, LogHandler.LogLevel.INFORMATION, true);
            velocity.y = speed;
            ballHitClip.play(false);
        }

        /* Ball hit bottom y-axis. */
        if (pos.y > (gameController.getHeight() - dim.height)) {
            if (optionsHandler.isGodModeEnabled()) {
                velocity.y = -speed;
                ballHitClip.play(false);
            } else {
                isStuck = true;

                // player lost a life
                levelHandler.getActiveLevel().decPlayerLife();

                pos = new Point(paddle.pos.x + (paddle.dim.width / 2) - (dim.width / 2), paddle.pos.y - (dim.height));

                velocity.x = 0;
                velocity.y = 0;

                ballResetClip.play(false);

                logHandler.log(textHandler.vBallTouchedYAxisBottomMsg, LogHandler.LogLevel.INFORMATION, true);
                logHandler.log("Player lost a life. Life: " + levelHandler.getActiveLevel().getPlayerLife(), LogHandler.LogLevel.INFORMATION, true);
            }
        }

    }

    private <T> void checkCollision(T t) {
        if (t instanceof Paddle && ((Paddle) t).getBounds().intersects(getBounds())) {
            if (paddleCollisionTimer == 0) {
                velocity.y *= -1;

                if (velocity.x < 0) {
                    velocity.x = -speed;
                } else {
                    velocity.x = speed;
                }

                paddleCollisionTimer = PADDLE_COLLISION_TIMER_INITIAL;

                soundHandler.getSoundClip(textHandler.SOUND_FILE_NAME_BALL_HIT).play(false);
                logHandler.log(textHandler.vBallPaddleCollisionMsg(paddleCollisionTimer), LogHandler.LogLevel.INFORMATION, true);
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
                        logHandler.log("BlockList[" + i + "] has been destroyed.", LogHandler.LogLevel.INFORMATION, true);
                    } else {
                        soundHandler.getSoundClip(textHandler.SOUND_FILE_NAME_BALL_HIT).play(false);
                        logHandler.log(textHandler.vBallBlockListCollisionMsg(i, blockList.getBlock(i).getHitPoints()), LogHandler.LogLevel.INFORMATION, true);
                    }

                }
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(color);
        g.fillOval(pos.x, pos.y, dim.width, dim.height);

        if (paddleCollisionTimer > 0) {
            paddleCollisionTimer--;
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(pos.x, pos.y, dim.width, dim.height);
    }

    public void pause() {
        ballPauseTimer = BALL_PAUSE_SCREEN_TIMER_INITIAL;
    }

}