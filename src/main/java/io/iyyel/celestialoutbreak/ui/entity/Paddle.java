package io.iyyel.celestialoutbreak.ui.entity;

import io.iyyel.celestialoutbreak.handler.InputHandler;

import java.awt.*;

public final class Paddle extends AbstractMobileEntity {

    private final InputHandler inputHandler = InputHandler.getInstance();

    private final int screenWidth;

    public Paddle(Point pos, Dimension dim, Color color, int speed, int screenWidth) {
        super(pos, dim, color, speed);
        this.screenWidth = screenWidth;
    }

    @Override
    public void update() {
        super.update();

        if (isUpdateStopped()) {
            return;
        }

        if (inputHandler.isLeftPressed() && pos.x > 0) {
            pos.x -= speed;
        }

        if (inputHandler.isRightPressed() && pos.x <= screenWidth - dim.width) {
            pos.x += speed;
        }
    }

    @Override
    public void render(Graphics2D g) {
        if (isRenderStopped()) {
            return;
        }

        g.setColor(color);
        g.fillRect(pos.x, pos.y, dim.width, dim.height);
    }

    public void applyPowerUp(PowerUp powerUp) {
        System.out.println("PowerUp applied!");
    }

}