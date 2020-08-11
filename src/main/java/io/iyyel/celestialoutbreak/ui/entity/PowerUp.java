package io.iyyel.celestialoutbreak.ui.entity;

import java.awt.*;

public final class PowerUp extends AbstractMobileEntity {

    private final int screenHeight;
    private final Paddle paddle;

    public PowerUp(Point pos, Dimension dim, Color color, int speed, int screenHeight, Paddle paddle) {
        super(pos, dim, color, speed);
        this.screenHeight = screenHeight;
        this.paddle = paddle;
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

}