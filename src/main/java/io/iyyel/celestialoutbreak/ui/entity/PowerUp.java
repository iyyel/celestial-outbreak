package io.iyyel.celestialoutbreak.ui.entity;

import io.iyyel.celestialoutbreak.ui.entity.effects.BallEffect;
import io.iyyel.celestialoutbreak.ui.entity.effects.Effect;
import io.iyyel.celestialoutbreak.ui.entity.effects.PaddleEffect;
import io.iyyel.celestialoutbreak.util.Util;

import java.awt.*;
import java.util.Random;

public final class PowerUp extends AbstractMobileEntity {

    private final Util util = Util.getInstance();

    private final int screenHeight;
    private final Paddle paddle;
    private final Ball ball;
    private final Effect effect;

    public PowerUp(Point pos, Dimension dim, Color color, int speed, int screenHeight, Paddle paddle, Ball ball) {
        super(pos, dim, color, speed);
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
        g.fillOval(pos.x, pos.y, dim.width, dim.height);
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
        int effectDuration = r.nextInt(10) + 5;
        boolean isPaddleEffect = r.nextInt(100) < 50;
        Effect e;

        /* make a Paddle effect */
        if (isPaddleEffect) {
            int paddleWidth = r.nextInt(480) + 20;
            int paddleHeight = r.nextInt(50);
            Dimension paddleDim = new Dimension(paddleWidth, paddleHeight);
            Color paddleColor = util.generatePastelColor((float) r.nextInt(6000) + 1000, (float) r.nextInt(6000) + 1000);
            int paddleSpeed = r.nextInt(10);
            e = new PaddleEffect(effectDuration, paddleDim, paddleColor, paddleSpeed);
        } else {
            /* make a Ball effect */
            int ballWidth = r.nextInt(180) + 20;
            int ballHeight = r.nextInt(180) + 20;
            Dimension ballDim = new Dimension(ballWidth, ballHeight);
            Color ballColor = util.generatePastelColor((float) r.nextInt(6000) + 1000, (float) r.nextInt(6000) + 1000);
            int ballSpeed = r.nextInt(10);
            e = new BallEffect(effectDuration, ballDim, ballColor, ballSpeed);
        }

        return e;
    }

}