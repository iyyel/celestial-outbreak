package io.iyyel.celestialoutbreak.entity;

import io.iyyel.celestialoutbreak.handler.OptionsHandler;
import io.iyyel.celestialoutbreak.utils.Utils;

import java.awt.*;

/**
 * The BlockList is a class of it's own, containing a list of a Block entities.
 * This class was created since a Block entity would never be used alone, but rather
 * in a group.
 * <p>
 * It acts as a place to control all of the Block entities in the game.
 * This class keeps track of how many Blocks are currently alive, etc.
 */
public final class BlockList {

    private final Utils utils = Utils.getInstance();
    private final OptionsHandler optionsHandler = OptionsHandler.getInstance();

    private Point pos;

    private final int width;
    private final int height;
    private final int spacing;
    private final int health;

    private int blocksLeft;

    private Block[] blockList;

    private final int screenWidth;

    /**
     * Default constructor.
     *
     * @param blockAmount Amount of @Block objects contained in this @BlockList.
     * @param pos         Initial @Block object position.
     * @param width       Width of each @Block object.
     * @param height      Height of each @Block object.
     * @param spacing     Spacing between @Block objects.
     */
    public BlockList(int blockAmount, Point pos, int width, int height, int spacing, int health, int screenWidth) {
        this.pos = pos;
        this.width = width;
        this.height = height;
        this.spacing = spacing;
        this.blocksLeft = blockAmount;
        this.health = health;
        this.screenWidth = screenWidth;

        /* Create the array and initialize the Block objects. */
        blockList = new Block[blockAmount];
        initBlocks();
    }

    /**
     * Draws every Block object in the list onto the screen.
     * <p>
     * Disables antialiasing when rendering Block objects,
     * due to performance(FPS), and enables it afterwards.
     *
     * @param g Graphics object used to render every block.
     */
    public void render(Graphics2D g) {
        /* Disable antialiasing for blocks for performance reasons. */
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

        /* Enables antialiasing when blocks have been rendered. */
        if (optionsHandler.isAntiAliasingEnabled()) {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        }
    }

    /**
     * Removes a Block object from this BlockList
     * by setting it equal to null in the list,
     * and decrements the total amount of live Block objects.
     *
     * @param index Index of the Block object to be destroyed.
     */
    public void removeBlock(int index) {
        blockList[index] = null;
        blocksLeft--;
    }

    /**
     * Returns the Block object at the given index in this BlockList.
     *
     * @param index Index of the Block object to be returned.
     * @return Block object from the BlockList.
     */
    public Block getBlock(int index) {
        return blockList[index];
    }

    /**
     * Returns the length of the BlockList, aka how many blocks
     * were alive at creation.
     * <p>
     * IMPORTANT: The size of the array will never change, since
     * blocks are just put to null if destroyed.
     *
     * @return The amount of Block objects in the list at
     * creation of the BlockList.
     */
    public int getLength() {
        return blockList.length;
    }

    /**
     * Returns the amount of @lock objects that are alive
     * in this BlockList instance.
     *
     * @return Amount of alive Block objects.
     */
    public int getBlocksLeft() {
        return blocksLeft;
    }

    /**
     * Initialize the Block objects in this BlockList instance.
     * <p>
     * Instantiates every Block object, adds new spacing, etc.
     */
    private void initBlocks() {
        int initialX = pos.x;
        int initialY = pos.y;

        for (int i = 0; i < blockList.length; i++) {
            /* Instantiate Block object. */
            Point blockPos = new Point(pos.x, pos.y);
            Color blockColor = utils.generatePastelColor(0.8F, 9000F);
            blockList[i] = new Block(blockPos, width, height, blockColor, health);

            /*
             * Adds spacing and extra width for the new block
             * object to be created.
             */
            pos.x += width + spacing;

            /* Make sure to wrap around the screen. */
            if (pos.x + width >= screenWidth) {
                pos.y += initialY;
                pos.x = initialX;
            }
        }
    }

}