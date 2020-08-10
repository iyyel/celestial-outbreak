package io.iyyel.celestialoutbreak.ui.entity;

import io.iyyel.celestialoutbreak.ui.interfaces.IRenderable;
import io.iyyel.celestialoutbreak.ui.interfaces.IUpdatable;

import java.awt.*;

public abstract class AbstractEntity implements IUpdatable, IRenderable {

    protected Point pos;
    protected Dimension dim;
    protected Color color;

    private int blockUpdateUpdates = 0;
    private int blockRenderUpdates = 0;

    public AbstractEntity(Point pos, Dimension dim, Color color) {
        this.pos = pos;
        this.dim = dim;
        this.color = color;
    }

    @Override
    public void update() {
        if (isUpdateStopped()) {
            blockUpdateUpdates--;
        }

        if (isRenderStopped()) {
            blockRenderUpdates--;
        }
    }

    @Override
    public void stopUpdate(int updates) {
        this.blockUpdateUpdates = updates;
    }

    @Override
    public void stopRender(int updates) {
        this.blockRenderUpdates = updates;
    }

    @Override
    public boolean isUpdateStopped() {
        return blockUpdateUpdates != 0;
    }

    @Override
    public boolean isRenderStopped() {
        return blockRenderUpdates != 0;
    }

    protected Rectangle getBounds() {
        return new Rectangle(pos.x, pos.y, dim.width, dim.height);
    }

}