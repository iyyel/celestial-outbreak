package io.iyyel.celestialoutbreak.ui.entity;

import io.iyyel.celestialoutbreak.handler.SoundHandler;
import io.iyyel.celestialoutbreak.ui.entity.effects.BallEffect;
import io.iyyel.celestialoutbreak.ui.entity.effects.Effect;
import io.iyyel.celestialoutbreak.ui.entity.effects.PaddleEffect;

import java.awt.*;

public final class PowerUp extends AbstractMobileEntity {

    private final SoundHandler soundHandler = SoundHandler.getInstance();

    private final int screenHeight;
    private final Paddle paddle;
    private final Ball ball;
    private final Effect effect;

    private final SoundHandler.SoundClip spawnClip;
    private final SoundHandler.SoundClip collideClip;

    public PowerUp(Point pos, Dimension dim, Shape shape, Color col, int speed,
                   int screenHeight, Paddle paddle, Ball ball, Effect effect) {
        super(pos, dim, shape, col, speed);
        this.screenHeight = screenHeight;
        this.paddle = paddle;
        this.ball = ball;
        this.effect = effect;
        spawnClip = soundHandler.getSoundClip(effect.getSpawnSoundFileName());
        collideClip = soundHandler.getSoundClip(effect.getCollideSoundFileName());
    }

    @Override
    public void update() {
        if (isUpdateStopped()) {
            return;
        }

        pos.y += speed;
    }

    @Override
    public void render(Graphics2D g) {
        super.render(g);
    }

    public boolean hasReachedBottom() {
        /* PowerUp hit bottom y-axis. +35 because of bottom panel */
        return pos.y + 35 > (screenHeight - dim.height);
    }

    public boolean collidesWithPaddle() {
        return paddle.intersects(this);
    }

    public void applyEffect() {
        if (effect instanceof PaddleEffect) {
            paddle.applyEffect((PaddleEffect) effect);
        } else if (effect instanceof BallEffect) {
            ball.applyEffect((BallEffect) effect);
        }
    }

    public void playSpawnClip() {
        spawnClip.play(false);
    }

    public void playCollideClip() {
        collideClip.play(false);
    }

}