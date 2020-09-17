package io.iyyel.celestialoutbreak.ui.entity;

import io.iyyel.celestialoutbreak.handler.*;
import io.iyyel.celestialoutbreak.level.Level;
import io.iyyel.celestialoutbreak.ui.entity.effects.BallEffect;
import io.iyyel.celestialoutbreak.util.Util;

import java.awt.*;

public final class Ball extends AbstractMovableEntity {

    private final Util util = Util.getInstance();
    private final OptionsHandler optionsHandler = OptionsHandler.getInstance();
    private final TextHandler textHandler = TextHandler.getInstance();
    private final SoundHandler soundHandler = SoundHandler.getInstance();
    private final LogHandler logHandler = LogHandler.getInstance();
    private final LevelHandler levelHandler = LevelHandler.getInstance();
    private final InputHandler inputHandler = InputHandler.getInstance();
    private final PowerUpHandler powerUpHandler = PowerUpHandler.getInstance();

    private final Point velocity;
    private boolean isStuck = true;

    private final int PADDLE_COLLISION_TIMER_INITIAL = 30;

    private int paddleCollisionTimer = 0;

    private final SoundHandler.SoundClip ballHitClip = soundHandler.getSoundClip(textHandler.SOUND_FILE_NAME_BALL_HIT);
    private final SoundHandler.SoundClip ballResetClip = soundHandler.getSoundClip(textHandler.SOUND_FILE_NAME_BALL_RESET);

    private final Paddle paddle;
    private final BlockField blockField;

    private final int screenWidth;
    private final int screenHeight;

    private BallEffect effect;

    private final Dimension origDim;
    private final Shape origShape;
    private final Color origCol;
    private final int origSpeed;

    public Ball(Point pos, Dimension dim, Shape shape, Color col, int speed, int screenWidth,
                int screenHeight, Paddle paddle, BlockField blockField) {
        super(pos, dim, shape, col, speed);
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.paddle = paddle;
        this.blockField = blockField;

        this.origDim = dim;
        this.origShape = shape;
        this.origCol = col;
        this.origSpeed = speed;

        velocity = new Point(0, 0);
    }

    @Override
    public void update() {
        move();

        /* Check for collision. */
        checkPaddleCollision(paddle);
        checkBlockCollision(blockField);

        checkLeftCollision();
        checkRightCollision();
        checkTopCollision();
        checkBottomCollision();

        updateEffect();
    }

    @Override
    public void render(Graphics2D g) {
        super.render(g);
        if (paddleCollisionTimer > 0) {
            paddleCollisionTimer--;
        }
    }

    private void move() {
        if (!isStuck) {
            pos.x += velocity.x;
            pos.y += velocity.y;
        } else {
            pos = new Point(paddle.pos.x + (paddle.dim.width / 2) - (dim.width / 2), paddle.pos.y - (dim.height));

            if (inputHandler.isAuxPressed()) {
                isStuck = false;
                velocity.y = -speed;
            }
        }
    }

    private void checkLeftCollision() {
        /* Ball hit left x-axis. */
        if (pos.x < 0) {
            logHandler.log(textHandler.vBallTouchedXAxisLeftMsg, "checkLeftCollision", LogHandler.LogLevel.INFO, true);
            velocity.x = speed;
            if (!isStuck) {
                ballHitClip.play(false);
            }
        }
    }

    private void checkRightCollision() {
        /* Ball hit right x-axis. */
        if (pos.x > (screenWidth - dim.width)) {
            logHandler.log(textHandler.vBallTouchedXAxisRightMsg, "checkRightCollision", LogHandler.LogLevel.INFO, true);
            velocity.x = -speed;
            if (!isStuck) {
                ballHitClip.play(false);
            }
        }
    }

    private void checkTopCollision() {
        /* Ball hit top y-axis. */
        if (pos.y < 0) {
            logHandler.log(textHandler.vBallTouchedYAxisTopMsg, "checkTopCollision", LogHandler.LogLevel.INFO, true);
            velocity.y = speed;
            if (!isStuck) {
                ballHitClip.play(false);
            }
        }
    }

    private void checkBottomCollision() {
        /* Ball hit bottom y-axis. */
        /* -35 because of Game Panel */
        if (pos.y > (screenHeight - 35)) {
            isStuck = true;

            // player lost a life
            if (!optionsHandler.isGodModeEnabled()) {
                levelHandler.getActiveLevel().decPlayerLife();
            }

            pos = new Point(paddle.pos.x + (paddle.dim.width / 2) - (dim.width / 2), paddle.pos.y - (dim.height));

            velocity.x = 0;
            velocity.y = 0;

            ballResetClip.play(false);

            logHandler.log(textHandler.vBallTouchedYAxisBottomMsg, "checkBottomCollision", LogHandler.LogLevel.INFO, true);
            logHandler.log("Player lost a life. Life: " + levelHandler.getActiveLevel().getPlayerLife(), "checkBottomCollision", LogHandler.LogLevel.INFO, true);
        }
    }

    private void checkPaddleCollision(Paddle paddle) {
        if (!paddle.isColliding(this)) {
            return;
        }

        if (paddleCollisionTimer != 0) {
            return;
        }

        velocity.y *= -1;

        if (velocity.x < 0) {
            velocity.x = -speed;
        } else {
            velocity.x = speed;
        }

        paddleCollisionTimer = PADDLE_COLLISION_TIMER_INITIAL;

        ballHitClip.play(false);

        if (optionsHandler.isVerboseLogEnabled()) {
            logHandler.log(textHandler.vBallPaddleCollisionMsg(paddleCollisionTimer), "checkPaddleCollision", LogHandler.LogLevel.INFO, true);
        }

    }

    private void checkBlockCollision(BlockField blockField) {
        /*
        int blockIndex = blockField.checkForEntityIntersection(this);

        /* No collision was found */
        /*
        if (blockIndex == -1) {
            return;
        }
        */

        /* If the ball is stuck and is colliding with a block */
        /*
        if (isStuck) {
            return;
        }

        velocity.y *= -1;

        // block was hit
        blockField.hit(blockIndex);

        if (blockField.get(blockIndex).isDead()) {
           */
        /* spawn powerup by chance */
            /*
            boolean spawn = new Random().nextInt(100) < levelHandler.getActiveLevel().getPowerUpChance();
            if (spawn) {
                spawnPowerUp(blockField.get(blockIndex), levelHandler.getActiveLevel());
                logHandler.log("Power up spawned!", "checkBlockCollision", LogHandler.LogLevel.INFO, true);
            }

            soundHandler.getSoundClip(textHandler.SOUND_FILE_NAME_BLOCK_DESTROYED).play(false);
            blockField.remove(blockIndex);
            logHandler.log("BlockField[" + blockIndex + "] has been destroyed.", "checkBlockCollision", LogHandler.LogLevel.INFO, true);
        } else {
            soundHandler.getSoundClip(textHandler.SOUND_FILE_NAME_BALL_HIT).play(false);
            logHandler.log(textHandler.vBallBlockFieldCollisionMsg(blockIndex, blockField.get(blockIndex).getHitPoints()), "checkBlockCollision", LogHandler.LogLevel.INFO, true);
        }
        */
    }

    private void spawnPowerUp(Block block, Level level) {
        Point powerUpPos = new Point(block.pos.x + (block.dim.width / 2) - (level.getPowerUpDim().width / 2), block.pos.y);

        PowerUp powerUp = new PowerUp(powerUpPos,
                level.getPowerUpDim(),
                level.getPowerUpShape(),
                block.col,
                level.getPowerUpSpeed(), screenHeight, paddle, this, level.getRandomEffect());

        powerUp.playSpawnClip();
        powerUpHandler.spawnPowerUp(powerUp);
    }

    public void applyEffect(BallEffect effect) {
        if (effect != null && effect.isActive()) {
            return;
        }

        this.effect = effect;
        this.effect.activate();
        this.dim = effect.getDim();
        this.col = effect.getColor();
        this.speed = effect.getSpeed();
        updateVelocity(speed);
    }

    private void updateEffect() {
        if (effect != null && effect.isActive()) {
            long delta = util.getTimeElapsed() - effect.getStartTime();
            if (delta > effect.getDuration()) {
                effect.deactivate();
                this.pos = new Point(pos.x + (dim.width / 2), pos.y + (dim.height / 2));
                this.dim = origDim;
                this.col = origCol;
                this.speed = origSpeed;
                updateVelocity(speed);
                effect = null;
            }
        }
    }

    private void updateVelocity(int speed) {
        if (velocity.x < 0) {
            velocity.x = -speed;
        } else {
            velocity.x = speed;
        }
        if (velocity.y < 0) {
            velocity.y = -speed;
        } else {
            velocity.y = speed;
        }
    }

}