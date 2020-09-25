package io.iyyel.celestialoutbreak.ui.entity;

import io.iyyel.celestialoutbreak.handler.InputHandler;
import io.iyyel.celestialoutbreak.ui.entity.effects.PaddleEffect;
import io.iyyel.celestialoutbreak.util.Util;

import java.awt.*;

public final class Paddle extends AbstractMobileEntity {

    private final Util util = Util.getInstance();
    private final InputHandler inputHandler = InputHandler.getInstance();

    private final Dimension origDim;
    private final Shape origShape;
    private final Color origCol;
    private final int origSpeed;

    private final int screenWidth;
    private final int screenHeight;

    private PaddleEffect effect;

    private final BlockField blockField;

    public Paddle(Point pos, Dimension dim, Shape shape, Color col, int speed, int screenWidth, int screenHeight, BlockField blockField) {
        super(pos, dim, shape, col, speed);
        this.origDim = dim;
        this.origShape = shape;
        this.origCol = col;
        this.origSpeed = speed;

        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        setVelocity(new Point(speed, speed));

        this.blockField = blockField;
    }

    @Override
    public void update() {
        if (isUpdateStopped()) {
            return;
        }

        moveLeft();
        moveRight();
        checkBlockCollisionXAxis();
        moveUp();
        moveDown();
        checkBlockCollisionYAxis();

        updateEffect();
    }

    private void moveUp() {
        if (inputHandler.isUpPressed() && pos.y > 0) {
            pos.y -= velocity.y;
        }
    }

    private void moveDown() {
        /* Paddle can't go underneath the game panel, hence -35 pixels. */
        if (inputHandler.isDownPressed() && pos.y <= screenHeight - 35) {
            pos.y += velocity.y;
        }
    }

    private void moveLeft() {
        if (inputHandler.isLeftPressed() && pos.x > 0) {
            pos.x -= velocity.x;
        }
    }

    private void moveRight() {
        if (inputHandler.isRightPressed() && pos.x <= screenWidth - dim.width) {
            pos.x += velocity.x;
        }
    }

    @Override
    public void render(Graphics2D g) {
        super.render(g);
    }

    private void checkBlockCollisionXAxis() {
        int blockIndex = blockField.intersects(this);
        if (blockIndex != -1) {
            Block b = blockField.getBlock(blockIndex);
            if (b != null) {
                fixCollisionXAxis(b);
            }
        }
    }

    private void checkBlockCollisionYAxis() {
        int blockIndex = blockField.intersects(this);
        if (blockIndex != -1) {
            Block b = blockField.getBlock(blockIndex);
            if (b != null) {
                fixCollisionYAxis(b);
            }
        }
    }

    public void applyEffect(PaddleEffect effect) {
        if (effect != null && effect.isActive()) {
            return;
        }

        this.effect = effect;
        this.effect.activate();
        this.dim = effect.getDim();
        this.shape = effect.getShape();
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
                this.col = origCol;
                this.speed = origSpeed;
                effect = null;
            }
        }
    }

}