package io.iyyel.celestialoutbreak.ui.entity;

import io.iyyel.celestialoutbreak.handler.InputHandler;
import io.iyyel.celestialoutbreak.ui.entity.effects.PaddleEffect;
import io.iyyel.celestialoutbreak.util.Util;

import java.awt.*;

public final class Paddle extends AbstractMobileEntity {

    private final Util util = Util.getInstance();
    private final InputHandler inputHandler = InputHandler.getInstance();

    private final Dimension origDim;
    private final Color origColor;
    private final int origSpeed;

    private final int screenWidth;

    private PaddleEffect effect;

    public Paddle(Point pos, Dimension dim, Color color, int speed, int screenWidth) {
        super(pos, dim, color, speed);
        this.origDim = super.dim;
        this.origColor = super.color;
        this.origSpeed = super.speed;
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

        updateEffect();
    }

    @Override
    public void render(Graphics2D g) {
        if (isRenderStopped()) {
            return;
        }

        g.setColor(color);
        g.fillRect(pos.x, pos.y, dim.width, dim.height);
    }

    public void applyEffect(PaddleEffect effect) {
        if (effect != null && effect.isActive()) {
            return;
        }

        this.effect = effect;
        this.effect.activate();
        this.dim = effect.getDim();
        this.color = effect.getColor();
        this.speed = effect.getSpeed();
    }

    private void updateEffect() {
        if (effect != null && effect.isActive()) {
            long delta = util.getTimeElapsed() - effect.getStartTime();
            if (delta > effect.getDuration()) {
                effect.deactivate();
                this.dim = origDim;
                this.color = origColor;
                this.speed = origSpeed;
                effect = null;
            }
        }
    }

}