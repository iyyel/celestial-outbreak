package io.inabsentia.celestialoutbreak.entity;

import io.inabsentia.celestialoutbreak.controller.Game;

import java.awt.*;

/*
 * Player controlled Paddle
 */
public class Paddle extends MobileEntity {

    public Paddle(Point pos, int width, int height, int speed, Color color, Game game) {
        super(pos, width, height, speed, color, game);
    }

    public void update(boolean left, boolean right) {
        if (left && pos.x > 0)
            pos.x -= speed;
        if (right && pos.x <= (game.getWidth() - width))
            pos.x += speed;
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(color);
        g.fillRect(pos.x, pos.y, width, height);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(pos.x, pos.y, width, height);
    }

}