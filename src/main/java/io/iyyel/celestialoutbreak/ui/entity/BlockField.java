package io.iyyel.celestialoutbreak.ui.entity;


import io.iyyel.celestialoutbreak.handler.OptionsHandler;
import io.iyyel.celestialoutbreak.ui.entity.AbstractEntity.Shape;
import io.iyyel.celestialoutbreak.ui.interfaces.IEntityRenderable;
import io.iyyel.celestialoutbreak.ui.interfaces.IEntityUpdatable;
import io.iyyel.celestialoutbreak.util.Util;

import java.awt.*;

public final class BlockField implements IEntityUpdatable, IEntityRenderable {

    private final Util util = Util.getInstance();
    private final OptionsHandler optionsHandler = OptionsHandler.getInstance();

    private final int amount;
    private final Point startPos;
    private final Point spacing;
    private final Dimension dim;
    private final int hitPoints;
    private final Shape shape;
    private final float lum;
    private final float sat;
    private final int screenWidth;

    private int totalBlocksLeft;
    private int totalHitPointsLeft;

    private final Block[] blockArr;

    private boolean updateStopped;
    private boolean renderStopped;

    public BlockField(int amount, Point startPos, Point spacing, Dimension dim, int hitPoints,
                      Shape shape, float lum, float sat, int screenWidth) {
        this.amount = amount;
        this.startPos = startPos;
        this.spacing = spacing;
        this.dim = dim;
        this.hitPoints = hitPoints;
        this.shape = shape;
        this.lum = lum;
        this.sat = sat;
        this.screenWidth = screenWidth;

        this.totalBlocksLeft = amount;
        this.totalHitPointsLeft = getTotalHitPoints();

        this.blockArr = initBlocks();
    }

    @Override
    public void render(Graphics2D g) {
        if (isRenderStopped()) {
            return;
        }

        /* Disable anti-aliasing for blocks due to performance reasons. */
        if (optionsHandler.isAntiAliasingEnabled()) {
            if (shape.equals(Shape.RECTANGLE)) {
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
                g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
            }
        }

        /* Render the blocks if they exist, i.e. if they aren't equal to null. */
        for (Block block : blockArr) {
            if (block != null) {
                block.render(g);
            }
        }

        /* Re-enables anti-aliasing when blocks have been rendered. */
        if (optionsHandler.isAntiAliasingEnabled()) {
            if (shape.equals(Shape.RECTANGLE)) {
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            }
        }
    }

    @Override
    public void stopRender() {
        renderStopped = true;
    }

    @Override
    public void resumeRender() {
        renderStopped = false;
    }

    @Override
    public boolean isRenderStopped() {
        return renderStopped;
    }

    @Override
    public void update() {
        if (isUpdateStopped()) {
            return;
        }

        for (Block block : blockArr) {
            if (block != null) {
                block.update();
            }
        }
    }

    @Override
    public void stopUpdate() {
        updateStopped = true;
    }

    @Override
    public void resumeUpdate() {
        updateStopped = false;
    }

    @Override
    public boolean isUpdateStopped() {
        return updateStopped;
    }

    private Block[] initBlocks() {
        Block[] arr = new Block[amount];
        int initX = startPos.x;
        for (int i = 0; i < arr.length; i++) {
            /* Instantiate Block object. */
            Point pos = new Point(startPos);
            Color col = util.generatePastelColor(lum, sat);
            /* speed is sat to 0 because we don't want the blocks to really move.. yet. */
            arr[i] = new Block(pos, dim, shape, col, 0, hitPoints);

            /*
             * Adds spacing and extra width for the new block
             * object to be created.
             */
            startPos.x += dim.width + spacing.x;

            /* Make sure to wrap around the screen. */
            if (startPos.x + dim.width >= screenWidth) {
                startPos.y += spacing.y;
                startPos.x = initX;
            }
        }
        return arr;
    }

    public void remove(int index) {
        if (index >= 0 && index < blockArr.length) {
            blockArr[index] = null;
            totalBlocksLeft--;
        }
    }

    public int getTotalBlocks() {
        return amount;
    }

    public int getTotalBlocksLeft() {
        return totalBlocksLeft;
    }

    public int getTotalHitPoints() {
        return hitPoints * amount;
    }

    public int getTotalHitPointsLeft() {
        return totalHitPointsLeft;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void hit(int index) {
        if (index >= 0 && index < blockArr.length &&
                blockArr[index] != null) {
            totalHitPointsLeft--;
            blockArr[index].hit();
        }
    }

    public boolean isBlockAlive(int index) {
        if (index >= 0 && index < blockArr.length &&
                blockArr[index] != null) {
            return blockArr[index].isAlive();
        }
        return false;
    }

}