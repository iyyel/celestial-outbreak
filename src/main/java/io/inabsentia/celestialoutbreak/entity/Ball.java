package io.inabsentia.celestialoutbreak.entity;

import io.inabsentia.celestialoutbreak.controller.Game;
import io.inabsentia.celestialoutbreak.handler.FileHandler;
import io.inabsentia.celestialoutbreak.handler.SoundHandler;
import io.inabsentia.celestialoutbreak.handler.TextHandler;
import io.inabsentia.celestialoutbreak.utils.GameUtils;

import java.awt.*;
import java.util.Random;

public class Ball extends MobileEntity {

    private final GameUtils gameUtils = GameUtils.getInstance();
    private final TextHandler textHandler = TextHandler.getInstance();
    private final SoundHandler soundHandler = SoundHandler.getInstance();
    private final FileHandler fileHandler = FileHandler.getInstance();

    private final Random random = new Random();

    private Point velocity;

    private int paddleCollisionTimer = 0;
    private int paddleCollisionTimerValue = 20;
    private int ballStaleTimerValue = 40;
    private int ballStaleTimer = ballStaleTimerValue;

    private int ballPosXOffset;private int ballPosYOffset;

    public Ball(Point pos, int ballPosXOffset, int ballPosYOffset, int width, int height, int speed, Color color, Game game) {
        super(pos, width, height, speed, color, game);
        this.ballPosXOffset = ballPosXOffset;
        this.ballPosYOffset = ballPosYOffset;

        velocity = new Point(speed, speed);
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
            if (gameUtils.isVerboseEnabled()) fileHandler.writeLogMsg(textHandler.vBallTouchedXAxisLeftMsg);
            velocity.x = speed;
            soundHandler.ballBounce.play(false);
        }

        if (pos.x > (game.getWidth() - width)) {
            if (gameUtils.isVerboseEnabled()) fileHandler.writeLogMsg(textHandler.vBallTouchedXAxisRightMsg);
            velocity.x = -speed;
            soundHandler.ballBounce.play(false);
        }

        if (pos.y < 0) {
            if (gameUtils.isVerboseEnabled()) fileHandler.writeLogMsg(textHandler.vBallTouchedYAxisTopMsg);
            velocity.y = speed;
            soundHandler.ballBounce.play(false);
        }

        if (pos.y > (game.getHeight() - height)) {
            if (gameUtils.isVerboseEnabled()) fileHandler.writeLogMsg(textHandler.vBallTouchedYAxisBottomMsg);

            pos = new Point((game.getWidth() / 2) - ballPosXOffset, (game.getHeight() / 2) - ballPosYOffset);

            boolean isPositiveValue = random.nextBoolean();
            int ballSpeedDecrement = random.nextInt(speed);

            velocity.x = (isPositiveValue ? 1 : -1) * speed + (isPositiveValue ? -ballSpeedDecrement : ballSpeedDecrement);
            velocity.y = speed;

            soundHandler.ballReset.play(false);
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

                soundHandler.ballBounce.play(false);

                if (gameUtils.isVerboseEnabled()) fileHandler.writeLogMsg(textHandler.vBallPaddleCollisionMsg(paddleCollisionTimer));
            }
        } else if (t.getClass().equals(BlockList.class)) {
            for (int i = 0, n = ((BlockList) t).getLength(); i < n; i++) {
                if (((BlockList) t).getBlock(i) != null && ((BlockList) t).getBlock(i).getBounds().intersects(getBounds())) {
                    velocity.y *= -1;
                    ((BlockList) t).destroyBlock(i);

                    soundHandler.ballBounce.play(false);

                    if (gameUtils.isVerboseEnabled()) fileHandler.writeLogMsg(textHandler.vBallBlockListCollisionMsg(i));
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