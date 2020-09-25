package io.iyyel.celestialoutbreak.ui.entity;

import io.iyyel.celestialoutbreak.handler.*;
import io.iyyel.celestialoutbreak.level.Level;
import io.iyyel.celestialoutbreak.ui.entity.effects.BallEffect;
import io.iyyel.celestialoutbreak.util.Util;

import java.awt.*;
import java.util.Random;

public final class Ball extends AbstractMobileEntity {

    private final Util util = Util.getInstance();
    private final OptionsHandler optionsHandler = OptionsHandler.getInstance();
    private final TextHandler textHandler = TextHandler.getInstance();
    private final SoundHandler soundHandler = SoundHandler.getInstance();
    private final LogHandler logHandler = LogHandler.getInstance();
    private final LevelHandler levelHandler = LevelHandler.getInstance();
    private final InputHandler inputHandler = InputHandler.getInstance();
    private final PowerUpHandler powerUpHandler = PowerUpHandler.getInstance();

    private boolean isStuck = true;

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
    }

    @Override
    public void update() {
        if (isStuck) {
            stuck();
        } else {
            pos.x += velocity.x;
            checkPaddleCollisionXAxis();
            pos.y += velocity.y;
            checkPaddleCollisionYAxis();

            checkLeftCollision();
            checkRightCollision();
            checkTopCollision();
            checkBottomCollision();
            checkBlockCollision();
        }
        updateEffect();
    }

    @Override
    public void render(Graphics2D g) {
        super.render(g);
    }

    private void stuck() {
        pos = new Point(paddle.pos.x + (paddle.dim.width / 2) - (dim.width / 2), paddle.pos.y - (dim.height));

        if (inputHandler.isAuxPressed()) {
            isStuck = false;
            velocity.y = -speed;
        }
    }

    private void checkLeftCollision() {
        /* Ball hit left x-axis. */
        if (pos.x < 0) {
            logHandler.log(textHandler.vBallTouchedXAxisLeftMsg, "checkLeftCollision", LogHandler.LogLevel.INFO, true);
            velocity.x = speed;
            ballHitClip.play(false);
        }
    }

    private void checkRightCollision() {
        /* Ball hit right x-axis. */
        if (pos.x > (screenWidth - dim.width)) {
            logHandler.log(textHandler.vBallTouchedXAxisRightMsg, "checkRightCollision", LogHandler.LogLevel.INFO, true);
            velocity.x = -speed;
            ballHitClip.play(false);
        }
    }

    private void checkTopCollision() {
        /* Ball hit top y-axis. */
        if (pos.y < 0) {

            if (velocity.x == 0) {
                randomXVelocity();
            }

            logHandler.log(textHandler.vBallTouchedYAxisTopMsg, "checkTopCollision", LogHandler.LogLevel.INFO, true);
            velocity.y = speed;
            ballHitClip.play(false);
        }
    }

    private void checkBottomCollision() {
        /* Ball hit bottom y-axis. */
        /* -35 because of Game Panel */
        if (pos.y > (screenHeight - 35)) {
            isStuck = true;

            // place the ball near the paddle immediately
            pos = new Point(paddle.pos.x + (paddle.dim.width / 2) - (dim.width / 2), paddle.pos.y - (dim.height));

            // ball now has no velocity
            setVelocity(0);

            // play reset sound
            ballResetClip.play(false);

            // player lost a life if god mode is not enabled
            if (!optionsHandler.isGodModeEnabled()) {
                levelHandler.getActiveLevel().decPlayerLife();
                logHandler.log("Player lost a life. Life: " + levelHandler.getActiveLevel().getPlayerLife(), "checkBottomCollision", LogHandler.LogLevel.INFO, true);
            }

            logHandler.log(textHandler.vBallTouchedYAxisBottomMsg, "checkBottomCollision", LogHandler.LogLevel.INFO, true);
        }
    }

    private void checkPaddleCollisionXAxis() {
        if (paddle.intersects(this)) {
            velocity.x *= -1;
            fixCollisionXAxis(paddle);
            ballHitClip.play(false);
        }
    }

    private void checkPaddleCollisionYAxis() {
        if (paddle.intersects(this)) {
            velocity.y *= -1;
            fixCollisionYAxis(paddle);
            ballHitClip.play(false);
        }
    }

    private void checkBlockCollision() {
        int blockIndex = blockField.intersects(this);

        /* No collision was found */
        if (blockIndex == -1) {
            return;
        }

        Block block = blockField.getBlock(blockIndex);

        if (block == null) {
            return;
        }

        velocity.y *= -1;

        if (velocity.x == 0) {
            randomXVelocity();
        }

        // block was hit
        blockField.hit(blockIndex);

        if (blockField.isBlockAlive(blockIndex)) {
            soundHandler.getSoundClip(textHandler.SOUND_FILE_NAME_BALL_HIT).play(false);
            logHandler.log(textHandler.vBallBlockFieldCollisionMsg(blockIndex, block.getHitPoints()), "checkBlockCollision", LogHandler.LogLevel.INFO, true);
        } else {
            /* spawn powerup by chance */
            boolean spawn = new Random().nextInt(100) < levelHandler.getActiveLevel().getPowerUpChance();
            if (spawn) {
                spawnPowerUp(block, levelHandler.getActiveLevel());
                logHandler.log("Power up spawned!", "checkBlockCollision", LogHandler.LogLevel.INFO, true);
            }

            soundHandler.getSoundClip(textHandler.SOUND_FILE_NAME_BLOCK_DESTROYED).play(false);
            blockField.remove(blockIndex);
            logHandler.log("BlockField[" + blockIndex + "] has been destroyed.", "checkBlockCollision", LogHandler.LogLevel.INFO, true);
        }
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

    private void randomXVelocity() {
        Random random = new Random();
        int flag = random.nextInt(2);
        if (flag == 0) {
            velocity.x = speed;
        } else {
            velocity.x = -speed;
        }
    }

}