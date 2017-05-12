package io.inabsentia.celestialoutbreak.entity;

import io.inabsentia.celestialoutbreak.controller.Game;
import io.inabsentia.celestialoutbreak.handler.SoundHandler;
import io.inabsentia.celestialoutbreak.utils.Utils;

import java.awt.*;

public class Ball extends MobileEntity {

    private Point velocity;
    private SoundHandler soundHandler;
    private int paddleCollisionTimer = 0;


    private final boolean DEV_ENABLED = Utils.getInstance().DEV_ENABLED;

    public Ball(Point pos, int width, int height, int speed, Color color, Game game) {
        super(pos, width, height, speed, color, game);
        soundHandler = SoundHandler.getInstance();
        velocity = new Point(speed, speed);
    }

    public void update(Paddle paddle, BlockList blockList) {
        pos.x += velocity.x;
        pos.y += velocity.y;

        checkCollision(paddle);
        checkCollision(blockList);

        if (pos.x < 0) {
            velocity.x *= -1 + (int) (Math.cos(Math.random() * 10));
            soundHandler.beep01.play(false);
        }

        if (pos.x > (game.getWidth() - width)) {
            velocity.x *= -1 + (int) (Math.cos(Math.random() * 10));
            soundHandler.beep01.play(false);
        }

        if (pos.y < 0 || pos.y > (game.getHeight() - height)) {
            velocity.y *= -1 + (int) (Math.sin(Math.random() * 10));
            soundHandler.beep01.play(false);
        }
    }

    private <T> void checkCollision(T t) {
        if (t.getClass().equals(Paddle.class) && ((Paddle) t).getBounds().intersects(getBounds())) {
            if (paddleCollisionTimer == 0) {
                velocity.y *= -1;
                paddleCollisionTimer = 20;

                soundHandler.beep01.play(false);

                if (DEV_ENABLED) {
                    System.err.println("[DEV]: Ball collision with Paddle. paddleCollisionTimer changed: " + paddleCollisionTimer);
                }
            }
        } else if (t.getClass().equals(BlockList.class)) {
            for (int i = 0, n = ((BlockList) t).getLength(); i < n; i++) {
                if (((BlockList) t).getBlock(i) != null && ((BlockList) t).getBlock(i).getBounds().intersects(getBounds())) {
                    velocity.y *= -1;
                    ((BlockList) t).destroyBlock(i);

                    soundHandler.beep01.play(false);

                    if (DEV_ENABLED) {
                        System.err.println("[DEV]: Ball collision with BlockList[" + i + "].");
                    }
                }
            }
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(color);
        g.fillOval(pos.x, pos.y, width, height);

        if (paddleCollisionTimer > 0) {
            paddleCollisionTimer--;

            if (DEV_ENABLED) {
                System.err.println("[DEV]: paddleCollisionTimer decremented to " + paddleCollisionTimer + ".");
            }
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(pos.x, pos.y, width, height);
    }

}