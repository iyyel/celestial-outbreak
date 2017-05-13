package io.inabsentia.celestialoutbreak.entity;

import io.inabsentia.celestialoutbreak.controller.Game;
import io.inabsentia.celestialoutbreak.utils.Utils;

import java.awt.*;

public class BlockList {

    private final int width;
    private final int height;
    private final int spacing;

    private Block[] blockList;
    private final Utils utils;
    private final Game game;

    private Point pos;

    public BlockList(int blockAmount, Point pos, int width, int height, int spacing, Game game) {
        this.pos = pos;
        this.width = width;
        this.height = height;
        this.spacing = spacing;
        this.game = game;

        blockList = new Block[blockAmount];
        utils = Utils.getInstance();
        initBlocks();
    }

    public void render(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        for (Block block : blockList)
            if (block != null)
                block.render(g);
    }

    private void initBlocks() {
        int initialX = pos.x;
        int initialY = pos.y;
        for (int i = 0, n = blockList.length; i < n; i++) {
            blockList[i] = new Block(new Point(pos.x, pos.y), width, height, utils.generatePastelColor(0.8F, 9000F));
            pos.x += width + spacing;
            if (pos.x + 50 >= game.getWidth()) {
                pos.y += initialY;
                pos.x = initialX;
            }
        }
    }

    public int getBlocksLeft() {
        int count = 0;
        for (Block block : blockList) {
            if (block != null) count++;
        }
        return count;
    }

    public int getLength() {
        return blockList.length;
    }

    public void destroyBlock(int index) {
        blockList[index] = null;
    }

    public Block getBlock(int index) {
        return blockList[index];
    }

}