package io.iyyel.celestialoutbreak.ui.screen.component;

import io.iyyel.celestialoutbreak.ui.interfaces.IRenderable;
import io.iyyel.celestialoutbreak.ui.interfaces.IUpdatable;

import java.awt.*;

public abstract class AbstractComponent implements IUpdatable, IRenderable {

    protected final Point pos;
    protected final Dimension dim;
    protected Color bgColor;

    protected final int screenWidth;
    protected final int screenHeight;

    private int blockUpdateUpdates = 0;
    private int blockRenderUpdates = 0;

    public AbstractComponent(Point pos, Dimension dim, Color bgColor, Point posOffset,
                             int screenWidth, int screenHeight) {
        this.pos = new Point(pos.x - posOffset.x, pos.y - posOffset.y);
        this.dim = dim;
        this.bgColor = bgColor;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
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

    public void setBgColor(Color bgColor) {
        this.bgColor = bgColor;
    }

}