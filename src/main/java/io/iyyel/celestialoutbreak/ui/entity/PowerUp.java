package io.iyyel.celestialoutbreak.ui.entity;

import io.iyyel.celestialoutbreak.handler.SoundHandler;
import io.iyyel.celestialoutbreak.ui.entity.effects.BallEffect;
import io.iyyel.celestialoutbreak.ui.entity.effects.Effect;
import io.iyyel.celestialoutbreak.ui.entity.effects.PaddleEffect;

import java.awt.*;

public final class PowerUp extends AbstractMobileEntity {

    private final SoundHandler soundHandler = SoundHandler.getInstance();

    private final PowerUp.Style style;
    private final int screenHeight;
    private final Paddle paddle;
    private final Ball ball;
    private final Effect effect;

    private final SoundHandler.SoundClip spawnClip;
    private final SoundHandler.SoundClip collideClip;

    public enum Style {
        CIRCLE,
        SQUARE
    }

    public PowerUp(Point pos, Dimension dim, Color color, int speed, PowerUp.Style style,
                   int screenHeight, Paddle paddle, Ball ball, Effect effect) {
        super(pos, dim, color, speed);
        this.style = style;
        this.screenHeight = screenHeight;
        this.paddle = paddle;
        this.ball = ball;
        this.effect = effect;
        spawnClip = soundHandler.getSoundClip(effect.getSpawnSoundFileName());
        collideClip = soundHandler.getSoundClip(effect.getCollideSoundFileName());
    }

    @Override
    public void update() {
        super.update();

        if (isUpdateStopped()) {
            return;
        }

        pos.y += speed;
    }

    @Override
    public void render(Graphics2D g) {
        if (isRenderStopped()) {
            return;
        }

        g.setColor(color);
        if (style.equals(PowerUp.Style.SQUARE)) {
            g.fillRect(pos.x, pos.y, dim.width, dim.height);
        } else if (style.equals(PowerUp.Style.CIRCLE)) {
            g.fillOval(pos.x, pos.y, dim.width, dim.height);
        }
    }

    public boolean hasReachedBottom() {
        /* PowerUp hit bottom y-axis. */
        return pos.y > (screenHeight - dim.height);
    }

    public boolean collidesWithPaddle() {
        return paddle.getBounds().intersects(getBounds());
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