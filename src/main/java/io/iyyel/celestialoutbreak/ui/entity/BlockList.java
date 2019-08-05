package io.iyyel.celestialoutbreak.ui.entity;

import io.iyyel.celestialoutbreak.ui.contract.IRenderable;
import io.iyyel.celestialoutbreak.controller.GameController;
import io.iyyel.celestialoutbreak.handler.OptionsHandler;
import io.iyyel.celestialoutbreak.util.Util;

import java.awt.*;

public final class BlockList implements IRenderable {

    private final Util util = Util.getInstance();
    private final OptionsHandler optionsHandler = OptionsHandler.getInstance();
    private final GameController gameController;

    private final Dimension dim;
    private final Point blockPosSpacing;
    private final int hitPoints;
    private final int blockAmount;
    private final float lum;
    private final float sat;

    private int blocksLeft;
    private int totalHitPointsLeft;

    private Point pos;
    private Block[] blockList;

    public BlockList(int blockAmount, int hitPoints, Point pos, Dimension dim, Point blockPosSpacing,
                     float lum, float sat, GameController gameController) {
        this.pos = pos;
        this.dim = dim;
        this.blockPosSpacing = blockPosSpacing;
        this.blocksLeft = blockAmount;
        this.gameController = gameController;
        this.hitPoints = hitPoints;
        this.blockAmount = blockAmount;
        this.totalHitPointsLeft = getTotalHitPoints();
        this.lum = lum;
        this.sat = sat;

        /* Create the array and initialize the @Block objects. */
        blockList = new Block[blockAmount];
        createBlocks();
    }

    @Override
    public void render(Graphics2D g) {
        /* Disable antialiasing for blocks for performance concerns. */
        if (optionsHandler.isAntiAliasingEnabled()) {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        }

        /* Render the blocks if they exist, i.e. if they aren't equal to null. */
        for (Block block : blockList)
            if (block != null)
                block.render(g);

        /* Enables antialiasing when blocks have been rendered. */
        if (optionsHandler.isAntiAliasingEnabled()) {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        }
    }

    public void destroyBlock(int index) {
        blockList[index] = null;
        blocksLeft--;
    }

    public Block getBlock(int index) {
        return blockList[index];
    }

    public int getLength() {
        return blockList.length;
    }

    public int getBlockAmount() {
        return blockAmount;
    }

    public int getBlocksLeft() {
        return blocksLeft;
    }

    public int getTotalHitPoints() {
        System.out.println("totalHitPoints: " + hitPoints * blockAmount);
        return hitPoints * blockAmount;
    }

    public int getTotalHitPointsLeft() {
        System.out.println("totalHitPointsLeft: " + totalHitPointsLeft);
        return totalHitPointsLeft;
    }

    public void decTotalHitPoints() {
        totalHitPointsLeft--;
    }

    private void createBlocks() {
        int initialX = pos.x;

        for (int i = 0; i < blockList.length; i++) {
            /* Instantiate @Block object. */
            blockList[i] = new Block(new Point(pos), dim, hitPoints, util.generatePastelColor(lum, sat));

            /*
             * Adds spacing and extra width for the new block
             * object to be created.
             */
            pos.x += dim.width + blockPosSpacing.x;

            /* Make sure to wrap around the screen. */
            if (pos.x + dim.width >= gameController.getWidth()) {
                pos.y += blockPosSpacing.y;
                pos.x = initialX;
            }
        }
    }

}