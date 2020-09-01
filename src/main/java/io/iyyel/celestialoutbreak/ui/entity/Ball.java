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

    private final Point velocity;
    private boolean isStuck = true;

    private final int PADDLE_COLLISION_TIMER_INITIAL = 30;

    private int paddleCollisionTimer = 0;

    private final SoundHandler.SoundClip ballHitClip = soundHandler.getSoundClip(textHandler.SOUND_FILE_NAME_BALL_HIT);
    private final SoundHandler.SoundClip ballResetClip = soundHandler.getSoundClip(textHandler.SOUND_FILE_NAME_BALL_RESET);

    private final Ball.Style style;

    private final Paddle paddle;
    private final BlockField blockField;

    private final int screenWidth;
    private final int screenHeight;

    private BallEffect effect;

    private final Dimension origDim;
    private final Color origColor;
    private final int origSpeed;

    public enum Style {
        CIRCLE,
        SQUARE
    }

    public Ball(Point pos, Dimension dim, Color color, int speed, Style style, Paddle paddle,
                BlockField blockField, int screenWidth, int screenHeight) {
        super(pos, dim, color, speed);
        this.style = style;
        this.paddle = paddle;
        this.blockField = blockField;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.origDim = super.dim;
        this.origColor = super.color;
        this.origSpeed = super.speed;
        velocity = new Point(0, 0);
    }

    @Override
    public void update() {
        super.update();
        checkForStuck();

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
        if (isRenderStopped()) {
            return;
        }

        g.setColor(color);
        if (style.equals(Ball.Style.SQUARE)) {
            g.fillRect(pos.x, pos.y, dim.width, dim.height);
        } else if (style.equals(Ball.Style.CIRCLE)) {
            g.fillOval(pos.x, pos.y, dim.width, dim.height);
        }

        if (paddleCollisionTimer > 0) {
            paddleCollisionTimer--;
        }
    }

    private void checkForStuck() {
        if (!isUpdateStopped() && !isStuck) {
            pos.x += velocity.x;
            pos.y += velocity.y;
        } else if (isStuck) {
            pos = new Point(paddle.pos.x + (paddle.dim.width / 2) - (dim.width / 2), paddle.pos.y - (dim.height));
            if (inputHandler.isUsePressed()) {
                isStuck = false;
                velocity.y = -speed;
            }
        }
    }

    private void checkLeftCollision() {
        /* Ball hit left x-axis. */
        if (pos.x < 0) {
            logHandler.log(textHandler.vBallTouchedXAxisLeftMsg, LogHandler.LogLevel.INFO, true);
            velocity.x = speed;
            ballHitClip.play(false);
        }
    }

    private void checkRightCollision() {
        /* Ball hit right x-axis. */
        if (pos.x > (screenWidth - dim.width)) {
            logHandler.log(textHandler.vBallTouchedXAxisRightMsg, LogHandler.LogLevel.INFO, true);
            velocity.x = -speed;
            ballHitClip.play(false);
        }
    }

    private void checkTopCollision() {
        /* Ball hit top y-axis. */
        if (pos.y < 0) {
            logHandler.log(textHandler.vBallTouchedYAxisTopMsg, LogHandler.LogLevel.INFO, true);
            velocity.y = speed;
            ballHitClip.play(false);
        }
    }

    private void checkBottomCollision() {
        /* Ball hit bottom y-axis. */
        if (pos.y > (screenHeight - dim.height)) {
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

                logHandler.log(textHandler.vBallTouchedYAxisBottomMsg, LogHandler.LogLevel.INFO, true);
                logHandler.log("Player lost a life. Life: " + levelHandler.getActiveLevel().getPlayerLife(), LogHandler.LogLevel.INFO, true);
            }
        }
    }

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
                    logHandler.log(textHandler.vBallPaddleCollisionMsg(paddleCollisionTimer), LogHandler.LogLevel.INFO, true);
                }
            }
        }
    }

    private void checkBlockCollision(BlockField blockField) {
        for (int i = 0; i < blockField.getLength(); i++) {
            if (blockField.get(i) != null && blockField.get(i).getBounds().intersects(getBounds())) {
                velocity.y *= -1;

                // block was hit
                blockField.hit(i);

                if (blockField.get(i).isDead()) {
                    /* spawn powerup by chance if enabled */
                    if (optionsHandler.isPowerUpEnabled()) {
                        boolean spawn = new Random().nextInt(100) < levelHandler.getActiveLevel().getPowerUpChance();
                        if (spawn) {
                            spawnPowerUp(blockField.get(i), levelHandler.getActiveLevel());
                            logHandler.log("Power up spawned!", "checkBlockCollision", LogHandler.LogLevel.INFO, true);
                        }
                    }

                    soundHandler.getSoundClip(textHandler.SOUND_FILE_NAME_BLOCK_DESTROYED).play(false);
                    blockField.remove(i);
                    logHandler.log("BlockField[" + i + "] has been destroyed.", LogHandler.LogLevel.INFO, true);
                } else {
                    soundHandler.getSoundClip(textHandler.SOUND_FILE_NAME_BALL_HIT).play(false);
                    logHandler.log(textHandler.vBallBlockFieldCollisionMsg(i, blockField.get(i).getHealth()), LogHandler.LogLevel.INFO, true);
                }
            }
        }
    }

    private void spawnPowerUp(Block block, Level level) {
        Point powerUpPos = new Point(block.pos.x + (block.dim.width / 2) - (level.getPowerUpDim().width / 2), block.pos.y);

        PowerUp powerUp = new PowerUp(powerUpPos,
                level.getPowerUpDim(),
                level.getBlockField().getLatestBlockColor(),
                level.getPowerUpSpeed(),
                level.getPowerUpStyle(), screenHeight, paddle, this, level.getRandomEffect());

        powerUpHandler.spawnPowerUp(powerUp);
    }

    public void applyEffect(BallEffect effect) {
        if (effect != null && effect.isActive()) {
            return;
        }

        this.effect = effect;
        this.effect.activate();
        this.dim = effect.getDim();
        this.color = effect.getColor();
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
                this.color = origColor;
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