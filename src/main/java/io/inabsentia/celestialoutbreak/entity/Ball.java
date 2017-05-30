package io.inabsentia.celestialoutbreak.entity;

import io.inabsentia.celestialoutbreak.controller.Game;
import io.inabsentia.celestialoutbreak.handler.SoundHandler;
import io.inabsentia.celestialoutbreak.utils.Utils;

import java.awt.*;
import java.util.Random;

public class Ball extends MobileEntity {

    private Point velocity;
    private SoundHandler soundHandler;
    private final Random random;

    private int paddleCollisionTimer = 0;
    private int paddleCollisionTimerValue = 20;
    private int ballStaleTimerValue = 40;
    private int ballStaleTimer = ballStaleTimerValue;

    private int ballPosXOffset;
    private int ballPosYOffset;

    private final Utils utils = Utils.getInstance();

    public Ball(Point pos, int ballPosXOffset, int ballPosYOffset, int width, int height, int speed, Color color, Game game) {
        super(pos, width, height, speed, color, game);
        this.ballPosXOffset = ballPosXOffset;
        this.ballPosYOffset = ballPosYOffset;

        velocity = new Point(speed, speed);
        soundHandler = SoundHandler.getInstance();
        random = new Random();
    }

    public void update(Paddle paddle, BlockList blockList) {
        if (ballStaleTimer == 0) {
            pos.x += velocity.x;
            pos.y += velocity.y;
        } else {
            ballStaleTimer--;
        }

        checkCollision(paddle);
        checkCollision(blockList);

        if (pos.x < 0) {
            if (utils.VERBOSE_ENABLED) utils.logMessage("Ball touched left y-axis.");
            velocity.x = speed;
            soundHandler.beep01.play(false);
        }

        if (pos.x > (game.getWidth() - width)) {
            if (utils.VERBOSE_ENABLED) utils.logMessage("Ball touched right y-axis.");
            velocity.x = -speed;
            soundHandler.beep01.play(false);
        }

        if (pos.y < 0) {
            if (utils.VERBOSE_ENABLED) utils.logMessage("Ball touched top x-axis.");
            velocity.y = speed;
            soundHandler.beep01.play(false);
        }

        if (pos.y > (game.getHeight() - height)) {
            if (utils.VERBOSE_ENABLED) utils.logMessage("Ball touched bottom x-axis.");

            pos = new Point((game.getWidth() / 2) - ballPosXOffset, (game.getHeight() / 2) - ballPosYOffset);

            boolean isPositiveValue = random.nextBoolean();
            int ballSpeedDecrement = random.nextInt(speed);

            velocity.x = (isPositiveValue ? 1 : -1) * speed + (isPositiveValue ? -ballSpeedDecrement : ballSpeedDecrement);
            velocity.y = speed;

            soundHandler.beep03.play(false);
            ballStaleTimer = ballStaleTimerValue;
        }
    }

    private <T> void checkCollision(T t) {
        if (t.getClass().equals(Paddle.class) && ((Paddle) t).getBounds().intersects(getBounds())) {
            if (paddleCollisionTimer == 0) {
                velocity.y *= -1;

                if (velocity.x < 0)
                    velocity.x = -speed;
                else
                    velocity.x = speed;

                paddleCollisionTimer = paddleCollisionTimerValue;

                soundHandler.beep01.play(false);

                if (utils.VERBOSE_ENABLED) utils.logMessage("Ball collision with Paddle. paddleCollisionTimer changed: " + paddleCollisionTimer);
            }
        } else if (t.getClass().equals(BlockList.class)) {
            for (int i = 0, n = ((BlockList) t).getLength(); i < n; i++) {
                if (((BlockList) t).getBlock(i) != null && ((BlockList) t).getBlock(i).getBounds().intersects(getBounds())) {
                    velocity.y *= -1;
                    ((BlockList) t).destroyBlock(i);

                    soundHandler.beep01.play(false);

                    if (utils.VERBOSE_ENABLED) utils.logMessage("Ball collision with BlockList[" + i + "].");
                }
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(color);
        g.fillOval(pos.x, pos.y, width, height);
        if (paddleCollisionTimer > 0) paddleCollisionTimer--;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(pos.x, pos.y, width, height);
    }

}