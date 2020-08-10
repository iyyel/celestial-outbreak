package io.iyyel.celestialoutbreak.ui.entity;

import io.iyyel.celestialoutbreak.handler.OptionsHandler;
import io.iyyel.celestialoutbreak.util.Util;

import java.awt.*;

public final class BlockField {

    private final Util util = Util.getInstance();
    private final OptionsHandler optionsHandler = OptionsHandler.getInstance();

    private final int amount;
    private final int health;
    private final Point startPos;
    private final Dimension dim;
    private final Point spacing;
    private final float lum;
    private final float sat;
    private final int screenWidth;

    private int blocksLeft;
    private int totalHealthLeft;

    private final Block[] blockList;

    public BlockField(int amount, int health, Point startPos, Dimension dim, Point spacing,
                      float lum, float sat, int screenWidth) {
        this.amount = amount;
        this.health = health;
        this.startPos = startPos;
        this.dim = dim;
        this.spacing = spacing;
        this.lum = lum;
        this.sat = sat;
        this.screenWidth = screenWidth;

        this.blocksLeft = amount;
        this.totalHealthLeft = getTotalHealth();

        blockList = new Block[amount];
        createBlocks();
    }

    public void update() {
        for (Block block : blockList) {
            if (block != null) {
                block.update();
            }
        }
    }

    public void render(Graphics2D g) {
        /* Disable anti-aliasing for blocks due to performance reasons. */
        if (optionsHandler.isAntiAliasingEnabled()) {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        }

        /* Render the blocks if they exist, i.e. if they aren't equal to null. */
        for (Block block : blockList) {
            if (block != null) {
                block.render(g);
            }
        }

        /* Re-enables anti-aliasing when blocks have been rendered. */
        if (optionsHandler.isAntiAliasingEnabled()) {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        }
    }

    private void createBlocks() {
        int initialX = startPos.x;

        for (int i = 0; i < blockList.length; i++) {
            /* Instantiate Block object. */
            Point blockPos = new Point(startPos);
            Color blockColor = util.generatePastelColor(lum, sat);
            blockList[i] = new Block(blockPos, dim, blockColor, health);

            /*
             * Adds spacing and extra width for the new block
             * object to be created.
             */
            startPos.x += dim.width + spacing.x;

            /* Make sure to wrap around the screen. */
            if (startPos.x + dim.width >= screenWidth) {
                startPos.y += spacing.y;
                startPos.x = initialX;
            }
        }
    }

    public void remove(int index) {
        blockList[index] = null;
        blocksLeft--;
    }

    public Block get(int index) {
        return blockList[index];
    }

    public int getLength() {
        return blockList.length;
    }

    public int getBlocksLeft() {
        return blocksLeft;
    }

    public int getHealth() {
        return health;
    }

    public int getTotalHealth() {
        return health * amount;
    }

    public int getTotalHealthLeft() {
        return totalHealthLeft;
    }

    public void hit(int index) {
        if (blockList[index] != null) {
            totalHealthLeft--;
            blockList[index].hit();
        }
    }

}