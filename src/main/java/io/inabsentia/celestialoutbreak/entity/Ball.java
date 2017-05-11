package io.inabsentia.celestialoutbreak.entity;

import io.inabsentia.celestialoutbreak.controller.Game;
import io.inabsentia.celestialoutbreak.handler.SoundHandler;

import java.awt.*;

public class Ball extends MobileEntity {

    private Point velocity;
    private Point prevPos;
    private SoundHandler soundHandler;

    public Ball(Point pos, int width, int height, int speed, Color color, Game game, SoundHandler soundHandler) {
        super(pos, width, height, speed, color, game);
        this.soundHandler = soundHandler;
        velocity = new Point(speed, speed);
        prevPos = new Point(0, 0);
    }

    public void update(Paddle paddle, BlockList blockList) {
        pos.x += velocity.x;
        pos.y += velocity.y;

        checkCollision(paddle);
        checkCollision(blockList);

        if (pos.x < 0) {
            velocity.x *= -1 + (int) (Math.cos(Math.random() * 10));
            // Play beep
            soundHandler.beep01.play(false);
        }

        if (pos.x > (game.getWidth() - width)) {
            velocity.x *= -1 + (int) (Math.cos(Math.random() * 10));
            // Play beep
            soundHandler.beep01.play(false);
        }

        if (pos.y < 0 || pos.y > (game.getHeight() - height)) {
            velocity.y *= -1 + (int) (Math.sin(Math.random() * 10));
            // Play beep
            soundHandler.beep01.play(false);
        }
        prevPos = pos;
    }

    private void checkCollision(Object obj) {
        if (obj.getClass().equals(Paddle.class) && ((Paddle) obj).getBounds().intersects(getBounds())) {
            pos = prevPos;
            velocity.y *= -1;

            // Play beep
            soundHandler.beep01.play(false);

        } else if (obj.getClass().equals(BlockList.class)) {

            for (int i = 0, n = ((BlockList) obj).getLength(); i < n; i++) {

                if (((BlockList) obj).getBlock(i) != null && ((BlockList) obj).getBlock(i).getBounds().intersects(getBounds())) {
                    velocity.y *= -1;
                    ((BlockList) obj).destroyBlock(i);

                    // Play beep
                    soundHandler.beep01.play(false);
                }

            }
        }

    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(color);
        g.fillOval(pos.x, pos.y, width, height);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(pos.x, pos.y, width, height);
    }

}