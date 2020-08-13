package io.iyyel.celestialoutbreak.ui.entity;

import io.iyyel.celestialoutbreak.handler.LevelHandler;
import io.iyyel.celestialoutbreak.ui.entity.effects.BallEffect;
import io.iyyel.celestialoutbreak.ui.entity.effects.Effect;
import io.iyyel.celestialoutbreak.ui.entity.effects.PaddleEffect;

import java.awt.*;
import java.util.Random;

public final class PowerUp extends AbstractMobileEntity {

    private final LevelHandler levelHandler = LevelHandler.getInstance();

    private PowerUp.Style style;
    private final int screenHeight;
    private final Paddle paddle;
    private final Ball ball;
    private final Effect effect;

    public enum Style {
        CIRCLE,
        SQUARE
    }

    public PowerUp(Point pos, Dimension dim, Color color, int speed, PowerUp.Style style,
                   int screenHeight, Paddle paddle, Ball ball) {
        super(pos, dim, color, speed);
        this.style = style;
        this.screenHeight = screenHeight;
        this.paddle = paddle;
        this.ball = ball;
        this.effect = genEffect();
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

    private Effect genEffect() {
        Random r = new Random();
        int effectDuration = r.nextInt((int) levelHandler.getActiveLevel().getPowerUpMaxDuration()) +
                (int) levelHandler.getActiveLevel().getPowerUpMinDuration();
        boolean isPaddleEffect = r.nextInt(100) < 50;
        Effect e;

        /* make a Paddle effect */
        if (isPaddleEffect) {
            int paddleWidth = r.nextInt(480) + 20;
            int paddleHeight = r.nextInt(20);
            Dimension paddleDim = new Dimension(paddleWidth, paddleHeight);
            Color paddleColor = this.color;

            int paddleSpeed;
            if (r.nextInt(100) < 5) {
                paddleSpeed = r.nextInt(29) + 1;
            } else {
                paddleSpeed = r.nextInt(9) + 1;
            }

            e = new PaddleEffect(effectDuration, paddleDim, paddleColor, paddleSpeed);
        } else {
            /* make a Ball effect */
            int ballWidth = r.nextInt(180) + 20;
            int ballHeight = r.nextInt(180) + 20;
            Dimension ballDim = new Dimension(ballWidth, ballHeight);
            Color ballColor = this.color;

            int ballSpeed;
            if (r.nextInt(100) < 5) {
                ballSpeed = r.nextInt(29) + 1;
            } else {
                ballSpeed = r.nextInt(9) + 1;
            }

            e = new BallEffect(effectDuration, ballDim, ballColor, ballSpeed);
        }

        return e;
    }

}