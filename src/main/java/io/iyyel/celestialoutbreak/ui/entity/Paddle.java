package io.iyyel.celestialoutbreak.ui.entity;

import io.iyyel.celestialoutbreak.handler.InputHandler;
import io.iyyel.celestialoutbreak.ui.entity.effects.PaddleEffect;
import io.iyyel.celestialoutbreak.util.Util;

import java.awt.*;

public final class Paddle extends AbstractMovableEntity {

    private final Util util = Util.getInstance();
    private final InputHandler inputHandler = InputHandler.getInstance();

    private final Dimension origDim;
    private final Color origColor;
    private final int origSpeed;

    private final BlockField blockField;
    private final int screenWidth;
    private final int screenHeight;

    private PaddleEffect effect;

    public Paddle(Point pos, Dimension dim, Color color, int speed,
                  BlockField blockField, int screenWidth, int screenHeight) {
        super(pos, dim, color, speed);
        this.blockField = blockField;
        this.origDim = super.dim;
        this.origColor = super.col;
        this.origSpeed = super.speed;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    @Override
    public void update() {
        if (inputHandler.isLeftPressed() && pos.x > 0) {
            move(Direction.LEFT);
        }

        if (inputHandler.isRightPressed() && pos.x <= screenWidth - dim.width) {
            move(Direction.RIGHT);
        }

        if (inputHandler.isUpPressed() && pos.y > 0) {
            move(Direction.UP);
        }

        /* Paddle can't go underneath the game panel, hence -35 pixels. */
        if (inputHandler.isDownPressed() && pos.y <= screenHeight - 35) {
            move(Direction.DOWN);
        }

        int blockIndex = blockField.checkForEntityIntersection(this);

        if (blockIndex != -1) {
            Block block = blockField.get(blockIndex);
            handleIntersection(block);
        }

        updateEffect();
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(col);
        g.fillRect(pos.x, pos.y, dim.width, dim.height);
        g.setColor(col);
    }

    public void applyEffect(PaddleEffect effect) {
        if (effect != null && effect.isActive()) {
            return;
        }

        this.effect = effect;
        this.effect.activate();
        this.dim = effect.getDim();
        this.col = effect.getColor();
        this.speed = effect.getSpeed();
    }

    private void updateEffect() {
        if (effect != null && effect.isActive()) {
            long delta = util.getTimeElapsed() - effect.getStartTime();
            if (delta > effect.getDuration()) {
                effect.deactivate();
                this.pos = new Point(pos.x + (dim.width / 2), pos.y);
                this.dim = origDim;
                this.col = origColor;
                this.speed = origSpeed;
                effect = null;
            }
        }
    }

}